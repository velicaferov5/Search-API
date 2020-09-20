package search.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import search.api.service.SearchService;

public class ControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@MockBean
	private SearchService searchService;

	@BeforeEach
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}

	@Test
	public void testHome() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/")
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetRelatedItems() throws Exception {
		String param = "Jack London";
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/search/".concat(param))
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}

}
