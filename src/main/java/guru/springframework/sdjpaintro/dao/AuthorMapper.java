package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

	@Override
	public Author mapRow(final ResultSet rs, final int rowNum) throws SQLException {

		// Note this method is not thread safe
		Author author = new Author();
		author.setId(rs.getLong("id"));
		author.setFirstName(rs.getString("first_name"));
		author.setLastName(rs.getString("last_name"));
		return author;
	}
}
