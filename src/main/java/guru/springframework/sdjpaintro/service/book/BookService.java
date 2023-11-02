package guru.springframework.sdjpaintro.service.book;

import guru.springframework.sdjpaintro.domain.Book;

public interface BookService {

	Book getById(Long id);

	Book findBookByTitle(String title);

	Book saveNewBook(Book book);

	Book updateBook(Book book);

	void deleteBookById(Long id);
}
