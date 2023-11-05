package guru.springframework.sdjpaintro.service.book;

import guru.springframework.sdjpaintro.entity.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

	List<Book> findAllBooksSortByTitle(Pageable pageable);

	List<Book> findAllBooks(Pageable pageable);

	List<Book> findAllBooks(int pageSize, int offset);

	List<Book> findAllBooks();

	Book getById(Long id);

	Book findBookByTitle(String title);

	Book saveNewBook(Book book);

	Book updateBook(Book book);

	void deleteBookById(Long id);
}
