package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("dao")
@DataJpaTest
@Import(AuthorSpringJdbcTemplateDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorSpringJdbcTemplateDaoTest {

	@Autowired
	AuthorSpringJdbcTemplateDao authorSpringJdbcTemplateDao;

	@Test
	void testGetAuthorById() {

		Author author = authorSpringJdbcTemplateDao.getById(1L);
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

		Author deletedAuthor = authorSpringJdbcTemplateDao.getById(savedAuthor.getId());

		assertThat(deletedAuthor).isNull();
	}

}
