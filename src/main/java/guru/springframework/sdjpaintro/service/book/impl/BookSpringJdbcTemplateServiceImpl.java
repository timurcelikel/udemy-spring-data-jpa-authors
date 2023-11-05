package guru.springframework.sdjpaintro.service.book.impl;

import guru.springframework.sdjpaintro.entity.Book;
import guru.springframework.sdjpaintro.service.book.BookMapper;
import guru.springframework.sdjpaintro.service.book.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSpringJdbcTemplateServiceImpl implements BookService {

	private final JdbcTemplate jdbcTemplate;

	public BookSpringJdbcTemplateServiceImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Book> findAllBooksSortByTitle(final Pageable pageable) {
		String sql =
				"SELECT * from book order by title " + pageable.getSort().getOrderFor("title").getDirection().name()
						+ " limit ? offset ?";
		return jdbcTemplate.query(sql, getBookMapper(), pageable.getPageSize(), pageable.getOffset());
	}

	@Override
	public List<Book> findAllBooks(final Pageable pageable) {
		return jdbcTemplate.query("SELECT * from book limit ? offset ?", getBookMapper(), pageable.getPageSize(),
				pageable.getOffset());
	}

	@Override
	public List<Book> findAllBooks(final int pageSize, final int offset) {
		return jdbcTemplate.query("SELECT * from book limit ? offset ?", getBookMapper(), pageSize, offset);
	}

	@Override
	public List<Book> findAllBooks() {
		return jdbcTemplate.query("SELECT * FROM book", getBookMapper());
	}

	@Override
	public Book getById(Long id) {
		return jdbcTemplate.queryForObject("SELECT * FROM book where id = ?", getBookMapper(), id);
	}

	@Override
	public Book findBookByTitle(String title) {
		return jdbcTemplate.queryForObject("SELECT * FROM book where title = ?", getBookMapper(), title);
	}

	@Override
	public Book saveNewBook(Book book) {
		jdbcTemplate.update("INSERT INTO book (isbn, publisher, title, author_id) VALUES (?, ?, ?, ?)",
				book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId());

		Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

		return this.getById(createdId);
	}

	@Override
	public Book updateBook(Book book) {
		jdbcTemplate.update("UPDATE book set isbn = ?, publisher = ?, title = ?, author_id = ? where id = ?",
				book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId(), book.getId());

		return this.getById(book.getId());
	}

	@Override
	public void deleteBookById(Long id) {
		jdbcTemplate.update("DELETE from book where id = ?", id);
	}

	private BookMapper getBookMapper() {
		return new BookMapper();
	}
}
