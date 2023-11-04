package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import guru.springframework.sdjpaintro.service.author.AuthorDao;
import guru.springframework.sdjpaintro.service.author.AuthorHibernateServiceImpl;
import guru.springframework.sdjpaintro.service.author.AuthorQueryService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test") // See note in sublime notes around why this annotation and conditional annotations could be bad
@SpringBootTest
@Import(AuthorHibernateServiceImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorHibernateServiceTest {

	@Autowired
	AuthorDao authorHibernateDao;

	@Autowired
	AuthorQueryService authorHibernateQueryDao;

	@Test
	void testFindAuthorByNameNative() {

		Author author = authorHibernateQueryDao.findAuthorByNameNative("John", "Steinbeck");
		assertThat(author).isNotNull();
	}

	@Test
	void testFindAuthorByNameCriteria() {

		Author author = authorHibernateQueryDao.findAuthorByNameCriteria("John", "Steinbeck");
		assertThat(author).isNotNull();
	}

	@Test
	void testFindAllNamedQuery() {

		List<Author> authors = authorHibernateQueryDao.findAll();
		assertThat(authors).isNotNull();
		assertThat(authors.size()).isGreaterThan(0);
	}

	@Test
	void testQueryAuthorById() {

		Author author = authorHibernateQueryDao.getByIdQuery(1L);
		assertThat(author).isNotNull();
		assertThat(author.getLastName()).isEqualTo("Steinbeck");
	}

	@Test
	void testTypedQueryAuthorById() {

		Author author = authorHibernateQueryDao.getByIdTypedQuery(1L);
		assertThat(author).isNotNull();
		assertThat(author.getLastName()).isEqualTo("Steinbeck");
	}

	@Test
	void testListAuthorByLastNameLike() {

		List<Author> authors = authorHibernateDao.listAuthorByLastNameLike("Stein");
		assertThat(authors).isNotNull();
		assertThat(authors.size()).isGreaterThan(0);
	}

	@Test
	@Disabled
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
		authorHibernateDao.deleteAuthorById(savedAuthor.getId());
		//authorHibernateDao.deleteAuthorById(updatedAuthor.getId());
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
