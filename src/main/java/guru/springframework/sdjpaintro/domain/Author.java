package guru.springframework.sdjpaintro.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;

	private String lastName;

	// @Transient tells Hibernate ignore this field when doing a DDL validate
	@Transient
	private List<Book> books;

	public Long getId() {

		return id;
	}

	public void setId(final Long id) {

		this.id = id;
	}

	public String getFirstName() {

		return firstName;
	}

	public void setFirstName(final String firstName) {

		this.firstName = firstName;
	}

	public String getLastName() {

		return lastName;
	}

	public void setLastName(final String lastName) {

		this.lastName = lastName;
	}

	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(final List<Book> books) {
		this.books = books;
	}
	@Override
	public boolean equals(final Object o) {

		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Author author = (Author) o;

		if (!Objects.equals(id, author.id))
			return false;
		if (!Objects.equals(firstName, author.firstName))
			return false;
		return Objects.equals(lastName, author.lastName);
	}

	@Override
	public int hashCode() {

		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {

		return "Author{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}
}
