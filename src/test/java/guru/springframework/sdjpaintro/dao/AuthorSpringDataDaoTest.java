package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.dao.author.AuthorSpringDataDaoImpl;
import guru.springframework.sdjpaintro.domain.Author;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@DataJpaTest
@Import(AuthorSpringDataDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorSpringDataDaoTest {

	@Autowired
	private AuthorSpringDataDaoImpl authorSpringDataDaoImpl;

	@Test
	void testFindAuthorById() {

		Author foundAuthor = authorSpringDataDaoImpl.getById(1L);
		assertThat(foundAuthor.getLastName()).isEqualTo("Steinbeck");
	}

	@Test
	void testFindAuthorByName() {

		Author foundAuthor = authorSpringDataDaoImpl.findAuthorByName("John", "Steinbeck");
		assertThat(foundAuthor.getLastName()).isEqualTo("Steinbeck");
	}

	@Test
	void testFindAuthorByNameNotFound() {

		assertThrows(EntityNotFoundException.class, () -> {
			authorSpringDataDaoImpl.findAuthorByName("John", "Helmbeck");
		});
	}

	@Test
	void testSaveAuthor() {

		Author author = new Author();
		author.setFirstName("Hugh");
		author.setLastName("Howey");
		authorSpringDataDaoImpl.saveAuthor(author);
		List<Author> authors = authorSpringDataDaoImpl.findAllAuthors();
		assertThat(authors.size()).isEqualTo(4);
	}

	@Test
	void testUpdateAuthor() {

		Author foundAuthor = authorSpringDataDaoImpl.getById(1L);
		foundAuthor.setLastName("Heinbeck");
		authorSpringDataDaoImpl.saveAuthor(foundAuthor);
		Author updatedAuthor = authorSpringDataDaoImpl.getById(1L);
		assertThat(updatedAuthor.getLastName()).isEqualTo("Heinbeck");
	}
}
