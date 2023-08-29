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

	private Long authorId;

	public Book() {

	}

	public Book(final String title, final String isbn, final String publisher, final Long authorId) {

		this.title = title;
		this.isbn = isbn;
		this.publisher = publisher;
		this.authorId = authorId;
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

	public Long getAuthorId() {

		return authorId;
	}

	public void setAuthorId(final Long authorId) {

		this.authorId = authorId;
	}

	@Override
	public boolean equals(final Object o) {

		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Book book = (Book) o;

		if (!Objects.equals(id, book.id))
			return false;
		if (!Objects.equals(title, book.title))
			return false;
		if (!Objects.equals(isbn, book.isbn))
			return false;
		if (!Objects.equals(publisher, book.publisher))
			return false;
		return Objects.equals(authorId, book.authorId);
	}

	@Override
	public int hashCode() {

		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
		result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
		result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {

		return "Book{" +
				"id=" + id +
				", title='" + title + '\'' +
				", isbn='" + isbn + '\'' +
				", publisher='" + publisher + '\'' +
				", authorId=" + authorId +
				'}';
	}
}
