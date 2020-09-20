package search.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import search.api.model.ItemDTO;

@RunWith(SpringRunner.class)
public class SearchServiceTest {

	@Autowired
	@MockBean
	private SearchService searchService;

	@Test
	public void testGetRelatedBooks() throws Exception {
		System.out.println("Test");
		List<String> authors = Arrays.asList("Nicholas Deakin", "Catherine Jones Finer", "Bob Matthews");
		List<ItemDTO> expectedItems = Arrays.asList(newItemDTO(
				"Welfare and the State: The zenith of Western welfare state systems", authors, null, "book"));

		List<ItemDTO> actualItems = searchService.getRelatedBooks("Jack London");

		assertItems(expectedItems, actualItems);
		/*
		 * searchService.getRelatedBooks(term).then().assertThat().statusCode(HttpStatus
		 * .OK.value()) .body("id", equalTo(testMovie.getId())).body("name",
		 * equalTo(testMovie.getName())) .body("synopsis", notNullValue());
		 * 
		 * String jsonString = new ObjectMapper().writeValueAsString(changeRequest);
		 * 
		 * MockHttpServletRequestBuilder builder =
		 * MockMvcRequestBuilders.post("/api/expand/flights/fdps")
		 * .contentType(MediaType.APPLICATION_JSON).content(jsonString);
		 * 
		 * mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).
		 * andDo(MockMvcResultHandlers.print())
		 * .andExpect(MockMvcResultMatchers.content().json(
		 * "[{\"flightInformation\":{\"flight\":{\"flightId\":1,\"callsign\":\"AAAA\",\"adep\":\"adep\",\"ades\":\"ades\",\"requestedfl\":350,\"cruisingSpeed\":350,\"flightType\":\"GENERAL\",\"flightRule\":\"IFR\",\"transformation\":\"FPL\",\"numberOfAircraft\":1,\"transmissionTrigger\":\"RELATIVE_ACTIVATION_FLIGHT\",\"transmissionTime\":0,\"activationtime\":0,\"startRouteAt\":1,\"initialFL\":300,\"clearedFL\":300,\"aircraft\":{\"typename\":\"A320\",\"wakeTurbulence\":\"LIGHT\",\"performanceData\":{\"climbRates\":[]}}},\"originalRoute\":[{\"sequence\":1,\"routeType\":\"ORIGINAL\",\"routeComponentType\":\"AERODROME_COMPONENT\",\"sectorEntry\":false,\"madapEntryPoint\":false,\"madapExitPoint\":false,\"ncop\":false},{\"sequence\":2,\"routeType\":\"ORIGINAL\",\"routeComponentType\":\"AERODROME_COMPONENT\",\"sid\":{\"aerodromeId\":1,\"identity\":\"SID 1\"},\"star\":{\"aerodromeId\":1,\"identity\":\"STAR 1\"},\"reportingpoint\":\"RP1\",\"aerodrome\":\"EBRU\",\"tasrfl\":\"TASRFL\",\"bearing\":0,\"distance\":0,\"route\":\"Route 1\",\"artificialname\":\"ARTIFICIALNAME\",\"sectorEntry\":false,\"madapEntryPoint\":false,\"madapExitPoint\":false,\"ncop\":false}],\"expandedRouteFdps\":[{\"sequence\":1,\"routeType\":\"EXPANDED\",\"routeComponentType\":\"REPORTINGPOINT_COMPONENT\",\"x\":1,\"y\":1,\"sectorEntry\":false,\"madapEntryPoint\":false,\"madapExitPoint\":false,\"ncop\":false},{\"sequence\":2,\"routeType\":\"EXPANDED\",\"routeComponentType\":\"REPORTINGPOINT_COMPONENT\",\"x\":2,\"y\":2,\"sectorEntry\":false,\"madapEntryPoint\":false,\"madapExitPoint\":false,\"ncop\":false}],\"expandedRouteModified\":[],\"validationErrors\":[],\"fieldErrors\":[],\"fieldWarnings\":[],\"routeErrors\":[],\"routeWarnings\":[]},\"trackList\":[{\"time\":5,\"altitude\":1,\"tas\":\"1\",\"x\":1,\"y\":1},{\"time\":10,\"altitude\":2,\"tas\":\"2\",\"x\":2,\"y\":2}]}]"
		 * )); // .andExpect(MockMvcResultMatchers.content().
		 * json("[{\"trainingSession\":1,\"flightId\":1,\"errors\":[{\"key\":null,\"value\":[\"aicraft name\",\"adep\",\"initialfix aerodrome\",\"alternate 2\",\"rc1 in route\",\"route in route\",\"alternate 1\",\"initial sector\",\"initialfix rp\",\"ades\",\"rc2 in route\",\"aerodrome in route\",\"star in route\"]}]}]"
		 * ));
		 */
	}

	private ItemDTO newItemDTO(String title, List<String> authors, String artistName, String kind) {
		ItemDTO item = new ItemDTO();
		item.setTitle(title);
		item.setAuthors(authors);
		item.setArtistName(artistName);
		item.setKind(kind);

		return item;
	}

	private void assertItems(List<ItemDTO> expected, List<ItemDTO> actual) {
		assertEquals(expected.size(), actual.size());
		for (int index1 = 0; index1 < expected.size(); index1++) {
			assertEquals(expected.get(index1).getTitle(), actual.get(index1).getTitle());
			if (expected.get(index1).getAuthors() != null) {
				assertEquals(expected.get(index1).getAuthors().size(), actual.get(index1).getAuthors().size());
				for (int index2 = 0; index2 < expected.get(index1).getAuthors().size(); index2++) {
					assertEquals(expected.get(index1).getAuthors().get(index2),
							actual.get(index1).getAuthors().get(index2));
				}
			}
			assertEquals(expected.get(index1).getArtistName(), actual.get(index1).getArtistName());
			assertEquals(expected.get(index1).getKind(), actual.get(index1).getKind());
		}
	}

	private static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
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
}
