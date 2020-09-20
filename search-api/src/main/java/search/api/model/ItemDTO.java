package search.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDTO {

	@JsonAlias({ "volumeInfo/title", "trackName" })
	private String title;

	@JsonProperty("volumeInfo/authors")
	private List<String> authors;

	@JsonProperty
	private String artistName;

	@JsonProperty
	private String kind;

	public String getTitle() {
		return title;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public String getArtistName() {
		return artistName;
	}

	public String getKind() {
		return kind.endsWith("s#volumes") ? kind.substring(0, kind.indexOf("s#volumes")) : kind;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		return "ItemDTO [title=" + title + ", authors=" + authors + ", artistName=" + artistName + ", kind=" + kind
				+ "]";
	}
}
