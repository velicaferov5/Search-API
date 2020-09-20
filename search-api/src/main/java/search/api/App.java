package search.api;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import search.api.service.SearchService;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		SearchService ser = new SearchService();
		try {
			ser.getRelatedBooks("Jack London");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
