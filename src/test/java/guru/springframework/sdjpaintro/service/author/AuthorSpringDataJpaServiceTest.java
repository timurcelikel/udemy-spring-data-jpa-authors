package guru.springframework.sdjpaintro.service.author;

import guru.springframework.sdjpaintro.entity.Author;
import guru.springframework.sdjpaintro.service.author.impl.AuthorSpringDataJpaServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@Transactional
class AuthorSpringDataJpaServiceTest {

	@Autowired
	private AuthorSpringDataJpaServiceImpl authorSpringDataJpaServiceImpl;

	@Test
	void testFindAuthorsByLastNameOrderByFirstNameAscending() {

		Author author = Author.builder().firstName("Jorge").lastName("Steinbeck").build();
		authorSpringDataJpaServiceImpl.saveAuthor(author);

		List<Author> authors = authorSpringDataJpaServiceImpl.findAllAuthorsByLastName("Steinbeck",
			PageRequest.of(0, 10, Sort.by(Sort.Order.asc("firstName"))));
		assertThat(authors).isNotNull().hasSize(2);
		assertThat(authors.get(0).getFirstName()).isEqualTo("John");
		assertThat(authors.get(1).getFirstName()).isEqualTo("Jorge");
	}

	@Test
	void testFindAuthorsByLastNameOrderByFirstNameDescending() {

		Author author = Author.builder().firstName("Jorge").lastName("Steinbeck").build();
		authorSpringDataJpaServiceImpl.saveAuthor(author);

		List<Author> authors = authorSpringDataJpaServiceImpl.findAllAuthorsByLastName("Steinbeck",
			PageRequest.of(0, 10, Sort.by(Sort.Order.desc("firstName"))));
		assertThat(authors).isNotNull().hasSize(2);
		assertThat(authors.get(0).getFirstName()).isEqualTo("Jorge");
		assertThat(authors.get(1).getFirstName()).isEqualTo("John");
	}

	@Test
	void testFindAuthorsByLastNameLike() {

		List<Author> authors = authorSpringDataJpaServiceImpl.findAuthorsByLastNameLike("Stein");
		assertThat(authors).hasSize(1);
	}

	@Test
	void testFindAuthorsByLastNameStartingWith() {

		List<Author> authors = authorSpringDataJpaServiceImpl.findAuthorsByLastNameStartingWith("Stein");
		assertThat(authors).hasSize(1);
	}

	@Test
	void testFindAuthorByFirstAndLastNameWithNativeQuery() {

		Author author = authorSpringDataJpaServiceImpl.findAuthorByFirstAndLastNameNativeQuery("John", "Steinbeck");
		assertNotNull(author);
	}

	@Test
	void testFindAuthorByLastNameWithNamedQueryAnnotation() {

		Author author = authorSpringDataJpaServiceImpl.findAuthorByLastNameWithNamedQuery("Steinbeck");
		assertNotNull(author);
	}

	@Test
	void testFindAuthorByFirstNameWithQueryAnnotation() {

		Author author = authorSpringDataJpaServiceImpl.findAuthorByFirstNameWithQuery("Robert");
		assertNotNull(author);
	}

	@Test
	void testQueryByFirstName() throws ExecutionException, InterruptedException {

		Future<Author> futureAuthor = authorSpringDataJpaServiceImpl.queryByFirstName("Robert");
		Author author = futureAuthor.get();
		assertNotNull(author);
	}

	@Test
	void testFindAuthorByName() {

		Author foundAuthor = authorSpringDataJpaServiceImpl.findAuthorByName("John", "Steinbeck");
		assertThat(foundAuthor.getLastName()).isEqualTo("Steinbeck");
	}

	@Test
	void testFindAllByFirstName() {

		List<Author> authors = authorSpringDataJpaServiceImpl.findAllByFirstName("John");
		assertThat(authors).hasSize(1);
	}

	@Test
	void testFindAuthorByNameNotFound() {

		assertThrows(EntityNotFoundException.class, () -> {
			authorSpringDataJpaServiceImpl.findAuthorByName("John", "Helmbeck");
		});
	}

	@Test
	void testFindAllAuthorsByFirstName() {

		List<Author> authors = authorSpringDataJpaServiceImpl.findAllAuthorsByFirstName("John");
		assertThat(authors.size()).isEqualTo(1);
	}

	@Test
	void testFindAllAuthorsByFirstNameCount() {

		AtomicInteger count = new AtomicInteger();
		authorSpringDataJpaServiceImpl.findAllAuthorsByFirstName("John").forEach(row -> count.getAndIncrement());
		assertThat(count.get()).isEqualTo(1);
	}

	@Test
	void testSaveAuthor() {

		Author author = new Author();
		author.setFirstName("Hugh");
		author.setLastName("Howey");
		authorSpringDataJpaServiceImpl.saveAuthor(author);
		List<Author> authors = authorSpringDataJpaServiceImpl.findAllAuthors();
		assertThat(authors).hasSize(3);
	}

	@Test
	void testUpdateAuthor() {

		Author foundAuthor = authorSpringDataJpaServiceImpl.getById(1L);
		foundAuthor.setLastName("Heinbeck");
		authorSpringDataJpaServiceImpl.saveAuthor(foundAuthor);
		Author updatedAuthor = authorSpringDataJpaServiceImpl.getById(1L);
		assertThat(updatedAuthor.getLastName()).isEqualTo("Heinbeck");
	}
}
