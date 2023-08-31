package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.dao.author.AuthorDao;
import guru.springframework.sdjpaintro.dao.author.AuthorJdbcDaoImpl;
import guru.springframework.sdjpaintro.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@Import(AuthorJdbcDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorJdbcDaoTest {

	@Autowired
	AuthorDao authorJdbcDao;

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
