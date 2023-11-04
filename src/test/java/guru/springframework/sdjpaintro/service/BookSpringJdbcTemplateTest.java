package guru.springframework.sdjpaintro.service;

import guru.springframework.sdjpaintro.entity.Book;
import guru.springframework.sdjpaintro.service.book.impl.BookSpringJdbcTemplateServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookSpringJdbcTemplateTest {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	BookSpringJdbcTemplateServiceImpl bookSpringJdbcTemplateServiceImpl;

	@Test
	void testFindAllBooks() {
		List<Book> books = bookSpringJdbcTemplateServiceImpl.findAllBooks();

		assertThat(books).isNotNull().hasSize(3);
	}

	@Test
	void getById() {
		Book book = bookSpringJdbcTemplateServiceImpl.getById(3L);

		assertThat(book.getId()).isNotNull();
	}

	@Test
	void findBookByTitle() {
		Book book = bookSpringJdbcTemplateServiceImpl.findBookByTitle("Domain Driven Design");

		assertThat(book).isNotNull();
	}

	@Test
	void saveNewBook() {
		Book book = new Book();
		book.setIsbn("1234");
		book.setPublisher("Self");
		book.setTitle("my book");
		book.setAuthorId(1L);

		Book saved = bookSpringJdbcTemplateServiceImpl.saveNewBook(book);

		assertThat(saved).isNotNull();
	}

	@Test
	void updateBook() {
		Book book = new Book();
		book.setIsbn("1234");
		book.setPublisher("Self");
		book.setTitle("my book");
		book.setAuthorId(1L);
		Book saved = bookSpringJdbcTemplateServiceImpl.saveNewBook(book);

		saved.setTitle("New Book");
		bookSpringJdbcTemplateServiceImpl.updateBook(saved);

		Book fetched = bookSpringJdbcTemplateServiceImpl.getById(saved.getId());

		assertThat(fetched.getTitle()).isEqualTo("New Book");
	}

	@Test
	void deleteBookById() {

		Book book = new Book();
		book.setIsbn("1234");
		book.setPublisher("Self");
		book.setTitle("my book");
		Book saved = bookSpringJdbcTemplateServiceImpl.saveNewBook(book);

		bookSpringJdbcTemplateServiceImpl.deleteBookById(saved.getId());

		assertThrows(EmptyResultDataAccessException.class, () -> {
			bookSpringJdbcTemplateServiceImpl.getById(saved.getId());
		});
	}
}
