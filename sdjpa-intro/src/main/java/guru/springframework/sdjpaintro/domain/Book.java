package guru.springframework.sdjpaintro.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	private String isbn;

	private String publisher;

	public Book() {

	}

	public Book(final String title, final String isbn, final String publisher) {

		this.title = title;
		this.isbn = isbn;
		this.publisher = publisher;
	}

	public Long getId() {

		return id;
	}

	public void setId(final Long id) {

		this.id = id;
	}

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

	@Override
	public boolean equals(final Object o) {

		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Book book = (Book) o;

		return Objects.equals(id, book.id);
	}

	@Override
	public int hashCode() {

		return id != null ? id.hashCode() : 0;
	}
}
