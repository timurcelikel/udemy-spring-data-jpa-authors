package guru.springframework.sdjpaintro.service;

import guru.springframework.sdjpaintro.entity.Author;
import guru.springframework.sdjpaintro.service.author.AuthorQueryService;
import guru.springframework.sdjpaintro.service.author.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
		//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorHibernateServiceTest {

	@Autowired
	AuthorService authorHibernateDao;

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
