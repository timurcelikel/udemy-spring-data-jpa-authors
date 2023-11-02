package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import guru.springframework.sdjpaintro.service.author.AuthorSpringDataServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Import(AuthorSpringDataServiceImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorSpringDataServiceTest {

	@Autowired
	private AuthorSpringDataServiceImpl authorSpringDataServiceImpl;

	@Test
	void testFindAuthorByFirstAndLastNameWithNativeQuery() {

		Author author = authorSpringDataServiceImpl.findAuthorByFirstAndLastNameNativeQuery("John", "Steinbeck");
		assertNotNull(author);
	}

	@Test
	void testFindAuthorByLastNameWithNamedQueryAnnotation() {

		Author author = authorSpringDataServiceImpl.findAuthorByLastNameWithNamedQuery("Steinbeck");
		assertNotNull(author);
	}

	@Test
	void testFindAuthorByFirstNameWithQueryAnnotation() {

		Author author = authorSpringDataServiceImpl.findAuthorByFirstNameWithQuery("Robert");
		assertNotNull(author);
	}

	@Test
	void testQueryByFirstName() throws ExecutionException, InterruptedException {

		Future<Author> futureAuthor = authorSpringDataServiceImpl.queryByFirstName("Robert");
		Author author = futureAuthor.get();
		assertNotNull(author);
	}

	@Test
	void testFindAuthorByName() {

		Author foundAuthor = authorSpringDataServiceImpl.findAuthorByName("John", "Steinbeck");
		assertThat(foundAuthor.getLastName()).isEqualTo("Steinbeck");
	}

	@Test
	void testFindAuthorByNameNotFound() {

		assertThrows(EntityNotFoundException.class, () -> {
			authorSpringDataServiceImpl.findAuthorByName("John", "Helmbeck");
		});
	}

	@Test
	void testFindAllAuthorsByFirstName() {

		List<Author> authors = authorSpringDataServiceImpl.findAllAuthorsByFirstName("John");
		assertThat(authors.size()).isEqualTo(1);
	}

	@Test
	void testFindAllAuthorsByFirstNameCount() {

		AtomicInteger count = new AtomicInteger();
		authorSpringDataServiceImpl.findAllAuthorsByFirstName("John").forEach(row -> count.getAndIncrement());
		assertThat(count.get()).isEqualTo(1);
	}

	@Test
	void testSaveAuthor() {

		Author author = new Author();
		author.setFirstName("Hugh");
		author.setLastName("Howey");
		authorSpringDataServiceImpl.saveAuthor(author);
		List<Author> authors = authorSpringDataServiceImpl.findAllAuthors();
		assertThat(authors.size()).isEqualTo(3);
	}

	@Test
	@Transactional
	void testUpdateAuthor() {

		Author foundAuthor = authorSpringDataServiceImpl.getById(1L);
		foundAuthor.setLastName("Heinbeck");
		authorSpringDataServiceImpl.saveAuthor(foundAuthor);
		Author updatedAuthor = authorSpringDataServiceImpl.getById(1L);
		assertThat(updatedAuthor.getLastName()).isEqualTo("Heinbeck");
	}
}
