package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.dao.author.AuthorDao;
import guru.springframework.sdjpaintro.dao.author.AuthorHibernateDaoImpl;
import guru.springframework.sdjpaintro.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test") // See note in sublime notes around why this annotation and conditional annotations could be bad
@DataJpaTest
@Import(AuthorHibernateDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorHibernateDaoTest {

	@Autowired
	AuthorDao authorHibernateDao;

	@Test
	void testListAuthorByLastNameLike() {

		List<Author> authors = authorHibernateDao.listAuthorByLastNameLike("Stein");
		assertThat(authors).isNotNull();
		assertThat(authors.size()).isGreaterThan(0);
	}

	@Test
	void testGetAuthorById() {

		Author author = authorHibernateDao.getById(2L);
		assertThat(author).isNotNull();
	}

	@Test
	void testFindAuthorByName() {

		Author author = authorHibernateDao.findAuthorByName("John", "Steinbeck");
		assertThat(author).isNotNull();
	}

	@Test
	void testSaveAuthor() {

		Author author = new Author();
		author.setFirstName("Pee-wee");
		author.setLastName("Herman");
		Author savedAuthor = authorHibernateDao.saveAuthor(author);

		assertThat(savedAuthor.getFirstName()).isEqualTo("Pee-wee");
		assertThat(savedAuthor.getLastName()).isEqualTo("Herman");
		assertThat(savedAuthor.getId()).isNotNull();

		// Janky rollback
		authorHibernateDao.deleteAuthorById(savedAuthor.getId());
	}

	@Test
	void testUpdateAuthor() {

		Author author = new Author();
		author.setFirstName("Pee-wee");
		author.setLastName("Herman");
		Author savedAuthor = authorHibernateDao.saveAuthor(author);

		assertThat(savedAuthor.getFirstName()).isEqualTo("Pee-wee");
		assertThat(savedAuthor.getLastName()).isEqualTo("Herman");

		savedAuthor.setLastName("Sherman");
		Author updatedAuthor = authorHibernateDao.updateAuthor(savedAuthor);

		assertThat(updatedAuthor.getLastName()).isEqualTo("Sherman");

		// Janky rollback
		authorHibernateDao.deleteAuthorById(updatedAuthor.getId());
	}

	@Test
	void testDeleteAuthor() {

		Author author = new Author();
		author.setFirstName("Pee-wee");
		author.setLastName("Herman");
		Author savedAuthor = authorHibernateDao.saveAuthor(author);

		assertThat(savedAuthor.getFirstName()).isEqualTo("Pee-wee");
		assertThat(savedAuthor.getLastName()).isEqualTo("Herman");

		authorHibernateDao.deleteAuthorById(savedAuthor.getId());
	}
}
