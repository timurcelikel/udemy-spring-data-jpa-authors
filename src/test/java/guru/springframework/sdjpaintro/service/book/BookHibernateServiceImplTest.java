package guru.springframework.sdjpaintro.service.book;

import guru.springframework.sdjpaintro.entity.Author;
import guru.springframework.sdjpaintro.entity.Book;
import guru.springframework.sdjpaintro.service.book.impl.BookHibernateServiceImpl;
import jakarta.persistence.EntityManagerFactory;
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
class BookHibernateServiceImplTest {

	@Autowired
	EntityManagerFactory emf;

	@Autowired
	BookHibernateServiceImpl bookService;

	@Test
	void findAllBooksSortByTitle() {
		List<Book> books = bookService.findAllBooksSortByTitle(PageRequest.of(0, 1,
			Sort.by(Sort.Order.desc("title"))));
		assertThat(books).isNotNull().hasSize(1);
		assertThat(books.get(0).getTitle()).isEqualTo("Spring In Action");
	}

	@Test
	void findAllBooks() {
		List<Book> books = bookService.findAllBooks(PageRequest.of(0, 10));
		assertThat(books).isNotNull().hasSize(2);
	}

	@Test
	void getById() {
		Book book = bookService.getById(1L);
		assertThat(book.getId()).isNotNull();
	}

	@Test
	void findBookByTitle() {
		Book book = bookService.findBookByTitle("Domain Driven Design");
		assertThat(book).isNotNull();
	}

	@Test
	void saveNewBook() {
		Book book = new Book();
		book.setIsbn("1234");
		book.setPublisher("Self");
		book.setTitle("my book");

		Author author = new Author();
		author.setId(3L);

		book.setAuthorId(1L);
		Book saved = bookService.saveNewBook(book);

		assertThat(saved).isNotNull();
		bookService.deleteBookById(saved.getId());
	}

	@Test
	void updateBook() {
		Book book = new Book();
		book.setIsbn("1234");
		book.setPublisher("Self");
		book.setTitle("my book");

		Author author = new Author();
		author.setId(3L);

		book.setAuthorId(1L);
		Book saved = bookService.saveNewBook(book);

		saved.setTitle("New Book");
		bookService.updateBook(saved);

		Book fetched = bookService.getById(saved.getId());

		assertThat(fetched.getTitle()).isEqualTo("New Book");

		// Hack: Because we are using our own Entity Manager that isn't managed by Spring we don't get the same
		// rollback features between each test.
		bookService.deleteBookById(saved.getId());
	}

	@Test
	void deleteBookById() {
		Book book = new Book();
		book.setIsbn("1234");
		book.setPublisher("Self");
		book.setTitle("my book");
		Book saved = bookService.saveNewBook(book);

		bookService.deleteBookById(saved.getId());

		Book deleted = bookService.getById(saved.getId());

		assertThat(deleted).isNull();
	}
}
