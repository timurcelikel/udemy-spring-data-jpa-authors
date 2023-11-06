package guru.springframework.sdjpaintro.service.book.impl;

import guru.springframework.sdjpaintro.entity.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import guru.springframework.sdjpaintro.service.book.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSpringDataJpaServiceImpl implements BookService {

	private final BookRepository bookRepository;

	public BookSpringDataJpaServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public List<Book> findAllBooksSortByTitle(final Pageable pageable) {
		Page<Book> bookPage = bookRepository.findAll(pageable);
		return bookPage.getContent();
	}

	@Override
	public List<Book> findAllBooks(final Pageable pageable) {
		return bookRepository.findAll(pageable).getContent();
	}

	@Override
	public List<Book> findAllBooks(final int pageSize, final int offset) {
		Pageable pageable = PageRequest.ofSize(pageSize);
		if (offset > 0) {
			pageable = pageable.withPage(offset / pageSize);
		} else {
			pageable = pageable.withPage(0);
		}
		return this.findAllBooks(pageable);
	}

	@Override
	public List<Book> findAllBooks() {
		return bookRepository.findAll();
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
