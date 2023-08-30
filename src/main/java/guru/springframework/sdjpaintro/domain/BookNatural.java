package guru.springframework.sdjpaintro.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BookNatural {

	@Id
	private String title;

	private String isbn;

	private String publisher;

	public String getTitle() {

		return title;
	}

	public void setTitle(final String title) {

		this.title = title;
	}

	public String getIsbn() {

		return isbn;
	}

	public void setIsbn(final String isbn) {

		this.isbn = isbn;
	}

	public String getPublisher() {

		return publisher;
	}

	public void setPublisher(final String publisher) {

		this.publisher = publisher;
	}
}
