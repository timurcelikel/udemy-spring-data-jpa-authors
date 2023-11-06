package guru.springframework.sdjpaintro.service.author;

import guru.springframework.sdjpaintro.entity.Author;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class AuthorHibernateServiceTest {

	@Autowired
	AuthorService authorHibernateDao;

	@Autowired
	AuthorQueryService authorHibernateQueryDao;

	@Test
	void testFindAuthorsByLastNameOrderByFirstNameAscending() {

		Author author = Author.builder().firstName("Jorge").lastName("Steinbeck").build();
		authorHibernateQueryDao.saveAuthor(author);

		List<Author> authors = authorHibernateQueryDao.findAllAuthorsByLastName("Steinbeck",
			PageRequest.of(0, 10, Sort.by(Sort.Order.asc("firstName"))));
		assertThat(authors).isNotNull().hasSize(2);
		assertThat(authors.get(0).getFirstName()).isEqualTo("John");
		assertThat(authors.get(1).getFirstName()).isEqualTo("Jorge");
		authorHibernateQueryDao.deleteAuthorById(author.getId());
	}

	@Test
	void testFindAuthorsByLastNameOrderByFirstNameDescending() {

		Author author = Author.builder().firstName("Jorge").lastName("Steinbeck").build();
		authorHibernateQueryDao.saveAuthor(author);

		List<Author> authors = authorHibernateQueryDao.findAllAuthorsByLastName("Steinbeck",
			PageRequest.of(0, 10, Sort.by(Sort.Order.desc("firstName"))));
		assertThat(authors).isNotNull().hasSize(2);
		assertThat(authors.get(0).getFirstName()).isEqualTo("Jorge");
		assertThat(authors.get(1).getFirstName()).isEqualTo("John");
		authorHibernateQueryDao.deleteAuthorById(author.getId());
	}

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

		List<Author> authors = authorHibernateDao.findAuthorsByLastNameLike("Stein");
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
