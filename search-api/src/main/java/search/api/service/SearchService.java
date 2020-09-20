package search.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.internal.util.privilegedactions.GetInstancesFromServiceLoader;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import search.api.model.ItemDTO;
import search.api.model.SearchResult;
import search.api.model.SearchResultDeserializer;

@Service
public class SearchService {

	/**
	 * Gets up to 5 books related to input {@param term} and returns as list.
	 * 
	 * @param term
	 * @return list related of books and albums
	 * @throws IOException
	 */
	public List<ItemDTO> getRelatedBooks(String term) throws IOException {
		System.out.println("Called Books");
		Map<String, String> params = new HashMap<>();
		params.put("q", term);
		params.put("maxResults", "5");
		URL url = new URL("https://www.googleapis.com/books/v1/volumes?".concat(getParamsString(params)));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setConnectTimeout(6000);
		if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return readResponse(con.getInputStream());
		} else {
			System.out.println("GET NOT WORKED: " + con.getResponseCode());
			return Collections.emptyList();
		}
	}

	/**
	 * Gets up to 5 albums related to input {@param term} and returns as list.
	 * 
	 * @param term
	 * @return list related of books and albums
	 * @throws IOException
	 */
	public List<ItemDTO> getRelatedAlbums(String term) throws IOException {
		System.out.println("Called Albums");
		Map<String, String> params = new HashMap<>();
		params.put("term", term);
		params.put("country", "NL");
		params.put("media", "music");
		params.put("entity", "music");
		URL url = new URL("https://itunes.apple.com/search?".concat(getParamsString(params)));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setConnectTimeout(6000);
		if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return readResponse(con.getInputStream());
		} else {
			System.out.println("GET NOT WORKED: " + con.getResponseCode());
			return Collections.emptyList();
		}
	}

	/**
	 * Converts {@param params} to proper string for URL.
	 * 
	 * @param params
	 * @return parameters in string form
	 * @throws UnsupportedEncodingException
	 */
	public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();

		for (Map.Entry<String, String> entry : params.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			result.append("&");
		}

		String resultString = result.toString();
		return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
	}

	/**
	 * Reads HTTP response input, converts and returns {@link ItemDTO} list.
	 * 
	 * @param input
	 */
	public List<ItemDTO> readResponse(InputStream input) {
		SearchResult result = new SearchResult();
		ObjectMapper mapper = new ObjectMapper(); // .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
													// true)

		SimpleModule module = new SimpleModule("searchResultDeserializer", new Version(1, 0, 0, null, null, null));
		module.addDeserializer(SearchResult.class, new SearchResultDeserializer());
		mapper.registerModule(module);

		BufferedReader in;
		StringBuffer content = new StringBuffer();
		try {
			in = new BufferedReader(new InputStreamReader(input));
			content = new StringBuffer();
			String readLine = null;
			while ((readLine = in.readLine()) != null) {
				content.append(readLine);
			}
			in.close();
			result = mapper.readValue(content.toString(), SearchResult.class); // new TypeReference<List<ItemDTO>>() {}
		} catch (IOException e) {
			e.printStackTrace();
		}
		result.getItems().forEach(item -> {
			System.out.println(item.getAuthors() + " " + item.getTitle() + " " + item.getKind());
		});
		return result.getItems();
	}
}
