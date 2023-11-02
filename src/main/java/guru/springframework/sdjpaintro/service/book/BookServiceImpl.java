package guru.springframework.sdjpaintro.service.book;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Book getById(Long id) {
		return bookRepository.getById(id);
	}

	@Override
	public Book findBookByTitle(String title) {
		return bookRepository.findBookByTitle(title).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public Book saveNewBook(Book book) {
		return bookRepository.save(book);
	}

	@Transactional
	@Override
	public Book updateBook(Book book) {
		Book foundBook = bookRepository.getById(book.getId());
		foundBook.setIsbn(book.getIsbn());
		foundBook.setPublisher(book.getPublisher());
		foundBook.setAuthorId(book.getAuthorId());
		foundBook.setTitle(book.getTitle());
		return bookRepository.save(foundBook);
	}

	@Override
	public void deleteBookById(Long id) {
		bookRepository.deleteById(id);
	}
}
