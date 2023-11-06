package guru.springframework.sdjpaintro.service.book;

import guru.springframework.sdjpaintro.entity.Book;
import guru.springframework.sdjpaintro.service.book.impl.BookSpringDataJpaServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class BookSpringDataJpaServiceTest {

	@Autowired
	BookSpringDataJpaServiceImpl bookSpringDataJpaServiceImpl;

	@Test
	void testFindAllBooksSortByTitle() {
		List<Book> books = bookSpringDataJpaServiceImpl.findAllBooksSortByTitle(PageRequest.of(0, 1,
			Sort.by(Sort.Order.desc("title"))));
		assertThat(books).isNotNull().hasSize(1);
		assertThat(books.get(0).getTitle()).isEqualTo("Spring In Action");
	}

	@Test
	void testFindAllBookPagesWithPageable() {
		List<Book> books1 = bookSpringDataJpaServiceImpl.findAllBooks(PageRequest.of(0, 1));
		assertThat(books1).isNotNull().hasSize(1);
		assertThat(books1.get(0).getTitle()).isEqualTo("Domain Driven Design");

		List<Book> books2 = bookSpringDataJpaServiceImpl.findAllBooks(PageRequest.of(1, 1));
		assertThat(books2).isNotNull().hasSize(1);
		assertThat(books2.get(0).getTitle()).isEqualTo("Spring In Action");
	}

	@Test
	void testFindAllBookPages() {
		List<Book> books1 = bookSpringDataJpaServiceImpl.findAllBooks(1, 0);
		assertThat(books1).isNotNull().hasSize(1);
		assertThat(books1.get(0).getTitle()).isEqualTo("Domain Driven Design");

		List<Book> books2 = bookSpringDataJpaServiceImpl.findAllBooks(1, 1);
		assertThat(books2).isNotNull().hasSize(1);
		assertThat(books2.get(0).getTitle()).isEqualTo("Spring In Action");
	}

	@Test
	void testFindAllBooks() {
		List<Book> books = bookSpringDataJpaServiceImpl.findAllBooks();
		assertThat(books).isNotNull().hasSize(2);
	}

	@Test
	void getById() {
		Book book = bookSpringDataJpaServiceImpl.getById(2L);
		assertThat(book.getId()).isNotNull();
	}

	@Test
	void findBookByTitle() {
		Book book = bookSpringDataJpaServiceImpl.findBookByTitle("Domain Driven Design");
		assertThat(book).isNotNull();
	}

	@Test
	void saveNewBook() {
		Book book = new Book();
		book.setIsbn("1234");
		book.setPublisher("Self");
		book.setTitle("my book");
		book.setAuthorId(1L);

		Book saved = bookSpringDataJpaServiceImpl.saveNewBook(book);

		assertThat(saved).isNotNull();
	}

	@Test
	void updateBook() {
		Book book = new Book();
		book.setIsbn("1234");
		book.setPublisher("Self");
		book.setTitle("my book");
		book.setAuthorId(1L);
		Book saved = bookSpringDataJpaServiceImpl.saveNewBook(book);

		saved.setTitle("New Book");
		bookSpringDataJpaServiceImpl.updateBook(saved);

		Book fetched = bookSpringDataJpaServiceImpl.getById(saved.getId());

		assertThat(fetched.getTitle()).isEqualTo("New Book");
	}

	@Test
	void deleteBookById() {

		Book book = new Book();
		book.setIsbn("1234");
		book.setPublisher("Self");
		book.setTitle("my book");
		Book saved = bookSpringDataJpaServiceImpl.saveNewBook(book);

		bookSpringDataJpaServiceImpl.deleteBookById(saved.getId());

		assertThrows(JpaObjectRetrievalFailureException.class, () -> {
			bookSpringDataJpaServiceImpl.getById(saved.getId());
		});
	}
}
