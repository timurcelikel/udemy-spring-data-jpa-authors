package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.dao.author.AuthorDao;
import guru.springframework.sdjpaintro.dao.author.AuthorSpringJdbcTemplateDaoImpl;
import guru.springframework.sdjpaintro.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@DataJpaTest
@Import(AuthorSpringJdbcTemplateDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorSpringJdbcTemplateDaoTest {

	@Autowired
	AuthorDao authorSpringJdbcTemplateDao;

	@Test
	void testFindAuthorsByLastNameLike() {

		List<Author> authors = authorSpringJdbcTemplateDao.listAuthorByLastNameLike("Stein");
		assertThat(authors).isNotNull();
		assertThat(authors.size()).isGreaterThan(0);
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

		assertThrows(TransientDataAccessResourceException.class, () -> {
			authorSpringJdbcTemplateDao.getById(savedAuthor.getId());
		});
	}
}
