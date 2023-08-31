package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpringJdbcTemplateDaoImpl implements AuthorSpringJdbcTemplateDao {

	private final JdbcTemplate jdbcTemplate;

	public AuthorSpringJdbcTemplateDaoImpl(final JdbcTemplate jdbcTemplate) {

		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Author getById(final Long id) {

		String sql = "select author.id as id, first_name, last_name, book.id as book_id, book.isbn, book.publisher, book.title from author\n" +
				"left outer join book on author.id = book.author_id where author.id = ?";

		return jdbcTemplate.query(sql, new AuthorExtractor(), id);
	}

	@Override
	public Author findAuthorByName(final String firstName, final String lastName) {

		return jdbcTemplate.queryForObject("SELECT * FROM author where first_name = ? and last_name = ?", getRowMapper(), firstName, lastName);
	}

	@Override
	public Author saveAuthor(final Author author) {

		jdbcTemplate.update("INSERT INTO author (first_name, last_name) values (?, ?)", author.getFirstName(),
				author.getLastName());

		Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
		return this.getById(createdId);
	}

	@Override
	public Author updateAuthor(final Author author) {

		jdbcTemplate.update("UPDATE author set first_name = ?, last_name = ? where id = ?", author.getFirstName(), author.getLastName(),
				author.getId());
		return this.getById(author.getId());
	}

	@Override
	public void deleteAuthorById(final Long id) {

		jdbcTemplate.update("DELETE from author where id = ?", id);
	}

	private RowMapper<Author> getRowMapper() {

		return new AuthorMapper();
	}
}
