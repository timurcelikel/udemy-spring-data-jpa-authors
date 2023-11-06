package guru.springframework.sdjpaintro.service.author;

import guru.springframework.sdjpaintro.entity.Author;
import guru.springframework.sdjpaintro.repositories.AuthorRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class AuthorSpringJdbcTemplateServiceTest {

	@Autowired
	AuthorService authorSpringJdbcTemplateDao;

	@Autowired
	AuthorRepository authorRepository;

	@Test
	void testFindAuthorsByLastNameOrderByFirstNameAscending() {

		Author author = Author.builder().firstName("Jorge").lastName("Steinbeck").build();
		authorRepository.save(author);

		List<Author> authors = authorSpringJdbcTemplateDao.findAllAuthorsByLastName("Steinbeck",
			PageRequest.of(0, 10, Sort.by(Sort.Order.asc("firstName"))));
		assertThat(authors).isNotNull().hasSize(2);
		assertThat(authors.get(0).getFirstName()).isEqualTo("John");
		assertThat(authors.get(1).getFirstName()).isEqualTo("Jorge");
		authorRepository.deleteById(author.getId());
	}

	@Test
	void testFindAuthorsByLastNameOrderByFirstNameDescending() {

		Author author = Author.builder().firstName("Jorge").lastName("Steinbeck").build();
		authorRepository.save(author);

		List<Author> authors = authorSpringJdbcTemplateDao.findAllAuthorsByLastName("Steinbeck",
			PageRequest.of(0, 10, Sort.by(Sort.Order.desc("firstName"))));
		assertThat(authors).isNotNull().hasSize(2);
		assertThat(authors.get(0).getFirstName()).isEqualTo("Jorge");
		assertThat(authors.get(1).getFirstName()).isEqualTo("John");
		authorRepository.deleteById(author.getId());
	}

	@Test
	void testFindAuthorsByLastNameLike() {

		List<Author> authors = authorSpringJdbcTemplateDao.findAuthorsByLastNameLike("Stein");
		assertThat(authors).isNotNull().hasSize(1);
	}

	@Test
	void testGetAuthorById() {

		Author author = authorSpringJdbcTemplateDao.getById(2L);
		assertThat(author).isNotNull();
	}

	@Test
	void testFindAuthorByName() {

		Author author = authorSpringJdbcTemplateDao.findAuthorByName("John", "Steinbeck");
		assertThat(author).isNotNull();
	}

	@Test
	void testSaveAuthor() {

		Author author = new Author();
		author.setFirstName("Pee-wee");
		author.setLastName("Herman");
		Author savedAuthor = authorSpringJdbcTemplateDao.saveAuthor(author);

		assertThat(savedAuthor.getFirstName()).isEqualTo("Pee-wee");
		assertThat(savedAuthor.getLastName()).isEqualTo("Herman");
	}

	@Test
	void testUpdateAuthor() {

		Author author = new Author();
		author.setFirstName("Pee-wee");
		author.setLastName("Herman");
		Author savedAuthor = authorSpringJdbcTemplateDao.saveAuthor(author);

		assertThat(savedAuthor.getFirstName()).isEqualTo("Pee-wee");
		assertThat(savedAuthor.getLastName()).isEqualTo("Herman");

		savedAuthor.setLastName("Sherman");
		Author updatedAuthor = authorSpringJdbcTemplateDao.updateAuthor(savedAuthor);

		assertThat(updatedAuthor.getLastName()).isEqualTo("Sherman");
	}

	@Test
	void testDeleteAuthor() {

		Author author = new Author();
		author.setFirstName("Pee-wee");
		author.setLastName("Herman");
		Author savedAuthor = authorSpringJdbcTemplateDao.saveAuthor(author);

		assertThat(savedAuthor.getFirstName()).isEqualTo("Pee-wee");
		assertThat(savedAuthor.getLastName()).isEqualTo("Herman");

		authorSpringJdbcTemplateDao.deleteAuthorById(savedAuthor.getId());

		assertThrows(DataIntegrityViolationException.class, () -> {
			authorSpringJdbcTemplateDao.getById(savedAuthor.getId());
		});
	}
}
