package search.api.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class SearchResultDeserializer extends StdDeserializer<SearchResult> {

	public SearchResultDeserializer() {
		this(null);
	}

	public SearchResultDeserializer(Class<?> vc) {
		super(vc);
	}

	/*
	 * @Override public SearchResult deserialize(JsonParser parser,
	 * DeserializationContext deserializer) throws IOException,
	 * JsonProcessingException { SearchResult result = new SearchResult(); JsonNode
	 * node = parser.getCodec().readTree(parser); for (final JsonNode itemNode :
	 * node.get("items")) { ItemDTO item = new ItemDTO();
	 * item.setKind(itemNode.get("kind").asText());
	 * item.setTitle(itemNode.get("title").asText());
	 * 
	 * for (final JsonNode authorNode : node.get("authors")) {
	 * item.getAuthors().add(authorNode.asText()); } result.getItems().add(item); }
	 * 
	 * return result; }
	 */

	/**
	 * Deserializes response from Search APIs to {@link SearchResult}.
	 * 
	 * @param parser
	 * @param deserializer
	 * @return deserialized SearchResult
	 */
	@Override
	public SearchResult deserialize(JsonParser parser, DeserializationContext deserializer) {
		SearchResult result = new SearchResult();
		List<ItemDTO> items = new ArrayList<>();
		ObjectCodec codec = parser.getCodec();
		try {
			JsonNode node = codec.readTree(parser);
			for (final JsonNode itemNode : node.get("items")) {
				ItemDTO item = new ItemDTO();
				item.setKind(itemNode.get("kind").asText());
				JsonNode infoNode = itemNode.get("volumeInfo");
				item.setTitle(infoNode.get("title").asText());
				JsonNode authorNodes = infoNode.get("authors");
				if (authorNodes.size() > 0) {
					List<String> authors = new ArrayList<>();
					for (final JsonNode authorNode : authorNodes) {
						authors.add(authorNode.asText());
					}
					item.setAuthors(authors);
				}
				items.add(item);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		result.setItems(items);
		return result;
	}

}
