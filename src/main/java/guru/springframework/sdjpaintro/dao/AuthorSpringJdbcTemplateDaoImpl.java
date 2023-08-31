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

		return jdbcTemplate.queryForObject("SELECT * FROM author where id = ?", getRowMapper(), id);
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

		return null;
	}

	@Override
	public void deleteAuthorById(final Long id) {

	}

	private RowMapper<Author> getRowMapper() {

		return new AuthorMapper();
	}
}
