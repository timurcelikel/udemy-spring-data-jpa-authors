package guru.springframework.sdjpaintro.service;

import guru.springframework.sdjpaintro.entity.Author;
import guru.springframework.sdjpaintro.service.author.AuthorService;
import guru.springframework.sdjpaintro.service.author.impl.AuthorJdbcServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Import(AuthorJdbcServiceImpl.class)
class AuthorJdbcServiceTest {

	@Autowired
	AuthorService authorJdbcDao;

	@Test
	void testFindAuthorsByLastNameLike() {

		List<Author> authors = authorJdbcDao.listAuthorByLastNameLike("Stein");
		assertThat(authors).isNotNull();
		assertThat(authors.size()).isGreaterThan(0);
	}

	@Test
	void testGetAuthorById() {

		Author author = authorJdbcDao.getById(1L);
		assertThat(author).isNotNull();
	}

	@Test
	void testFindAuthorByName() {

		Author author = authorJdbcDao.findAuthorByName("John", "Steinbeck");
		assertThat(author).isNotNull();
	}

	@Test
	@Rollback(value = true)
	void testSaveAuthor() {

		Author author = new Author();
		author.setFirstName("Pee-wee");
		author.setLastName("Herman");
		Author savedAuthor = authorJdbcDao.saveAuthor(author);

		assertThat(savedAuthor.getFirstName()).isEqualTo("Pee-wee");
		assertThat(savedAuthor.getLastName()).isEqualTo("Herman");

		// Janky Rollback - above annotation didn't work
		authorJdbcDao.deleteAuthorById(savedAuthor.getId());
	}

	@Test
	@Rollback(value = true)
	void testUpdateAuthor() {

		Author author = new Author();
		author.setFirstName("Pee-wee");
		author.setLastName("Herman");
		Author savedAuthor = authorJdbcDao.saveAuthor(author);

		assertThat(savedAuthor.getFirstName()).isEqualTo("Pee-wee");
		assertThat(savedAuthor.getLastName()).isEqualTo("Herman");

		savedAuthor.setLastName("Sherman");
		Author updatedAuthor = authorJdbcDao.updateAuthor(savedAuthor);

		assertThat(updatedAuthor.getLastName()).isEqualTo("Sherman");

		// Janky Rollback - above annotation didn't work
		authorJdbcDao.deleteAuthorById(updatedAuthor.getId());
	}

	@Test
	void testDeleteAuthor() {

		Author author = new Author();
		author.setFirstName("Pee-wee");
		author.setLastName("Herman");
		Author savedAuthor = authorJdbcDao.saveAuthor(author);

		assertThat(savedAuthor.getFirstName()).isEqualTo("Pee-wee");
		assertThat(savedAuthor.getLastName()).isEqualTo("Herman");

		authorJdbcDao.deleteAuthorById(savedAuthor.getId());

		Author deletedAuthor = authorJdbcDao.getById(savedAuthor.getId());

		assertThat(deletedAuthor).isNull();
	}

}
