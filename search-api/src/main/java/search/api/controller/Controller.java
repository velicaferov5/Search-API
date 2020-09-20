package search.api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import search.api.model.ItemDTO;
import search.api.service.SearchService;

@RestController
@RequestMapping(value = "/api")
public class Controller {

	private SearchService searchService;

	public Controller(SearchService searchService) {
		this.searchService = searchService;
	}

	@RequestMapping("/")
	public String home() {
		return "Search API is working!";
	}

	/**
	 * Gets up to 5 books and 5 albums related to input {@param term} and returns as
	 * list.
	 * 
	 * @param term
	 * @return list related of books and albums
	 * @throws IOException
	 */
	@GetMapping("/search/{term}")
	public List<ItemDTO> getRelatedItems(@PathVariable String term) throws IOException {
		List<ItemDTO> items = searchService.getRelatedBooks(term);
		items.addAll(searchService.getRelatedAlbums(term));
		return items;
	}

}