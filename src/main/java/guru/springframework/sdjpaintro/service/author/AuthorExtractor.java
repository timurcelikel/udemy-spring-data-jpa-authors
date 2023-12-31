package guru.springframework.sdjpaintro.service.author;

import guru.springframework.sdjpaintro.entity.Author;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorExtractor implements ResultSetExtractor<Author> {

	@Override
	public Author extractData(final ResultSet rs) throws SQLException, DataAccessException {

		rs.next();
		return new AuthorMapper().mapRow(rs, 0);
	}
}
