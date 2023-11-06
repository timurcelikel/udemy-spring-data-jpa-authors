package guru.springframework.sdjpaintro.service.author.impl;

import guru.springframework.sdjpaintro.entity.Author;
import guru.springframework.sdjpaintro.service.author.AuthorExtractor;
import guru.springframework.sdjpaintro.service.author.AuthorMapper;
import guru.springframework.sdjpaintro.service.author.AuthorService;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorSpringJdbcTemplateServiceImpl implements AuthorService {

	private final JdbcTemplate jdbcTemplate;

	public AuthorSpringJdbcTemplateServiceImpl(final JdbcTemplate jdbcTemplate) {

		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Author> findAllAuthorsByLastName(final String lastName, final Pageable pageable) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * from author where last_name = ?");
		if (pageable.getSort().getOrderFor("firstName") != null) {
			sql.append("order by first_name " + pageable.getSort().getOrderFor("firstName").getDirection().name());
		}
		sql.append(" limit ? offset ?");
		return jdbcTemplate.query(sql.toString(), getRowMapper(), lastName, pageable.getPageSize(),
			pageable.getOffset());
	}

	@Override
	public List<Author> findAuthorsByLastNameLike(final String lastName) {

		return jdbcTemplate.query("SELECT * FROM author where last_name like ?", getRowMapper(), lastName + "%");
	}

	@Override
	public Author getById(final Long id) {

		String sql =
			"select author.id as id, first_name, last_name, book.id as book_id, book.isbn, book.publisher, book"
				+ ".title from author\n"
				+ "left outer join book on author.id = book.author_id where author.id = ?";

		return jdbcTemplate.query(sql, new AuthorExtractor(), id);
	}

	@Override
	public Author findAuthorByName(final String firstName, final String lastName) {

		return jdbcTemplate.queryForObject("SELECT * FROM author where first_name = ? and last_name = ?",
			getRowMapper(), firstName, lastName);
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

		jdbcTemplate.update("UPDATE author set first_name = ?, last_name = ? where id = ?", author.getFirstName(),
			author.getLastName(),
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
