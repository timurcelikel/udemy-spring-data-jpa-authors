package guru.springframework.sdjpaintro.service.book;

import guru.springframework.sdjpaintro.entity.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

	@Override
	public Book mapRow(final ResultSet rs, final int rowNum) throws SQLException {

		Book book = new Book();
		book.setAuthorId(rs.getLong(1));
		book.setId(rs.getLong(2));
		book.setIsbn(rs.getString(3));
		book.setPublisher(rs.getString(4));
		book.setTitle(rs.getString(5));

		return book;
	}
}
