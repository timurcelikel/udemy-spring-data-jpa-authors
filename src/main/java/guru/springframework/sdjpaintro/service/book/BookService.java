package guru.springframework.sdjpaintro.service.book;

import guru.springframework.sdjpaintro.entity.Book;

import java.util.List;

public interface BookService {

	List<Book> findAllBooks();

	Book getById(Long id);

	Book findBookByTitle(String title);

	Book saveNewBook(Book book);

	Book updateBook(Book book);

	void deleteBookById(Long id);
}
