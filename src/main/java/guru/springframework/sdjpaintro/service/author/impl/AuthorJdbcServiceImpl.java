package guru.springframework.sdjpaintro.service.author.impl;

import guru.springframework.sdjpaintro.entity.Author;
import guru.springframework.sdjpaintro.service.author.AuthorService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorJdbcServiceImpl implements AuthorService {

	private final DataSource dataSource;

	public AuthorJdbcServiceImpl(final DataSource dataSource) {

		this.dataSource = dataSource;
	}

	@Override
	public List<Author> findAuthorsByLastNameLike(final String lastName) {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();

			ps = connection.prepareStatement("SELECT * FROM author where last_name like ?");
			ps.setString(1, lastName + "%");
			resultSet = ps.executeQuery();

			List<Author> authors = new ArrayList<>();

			if (resultSet.next()) {
				authors.add(getAuthorFromResultSet(resultSet));
			}
			return authors;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(resultSet, ps, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<Author> findAllAuthorsByLastName(final String lastName, final Pageable pageable) {
		return null;
	}

	@Override
	public Author getById(final Long id) {

		Connection connection = null;
		Statement statement = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();

			// Definitely don't do this in prod without a prepared statement and bind parameters
			// Can lead to SQL injection
			//resultSet = statement.executeQuery("SELECT * FROM author where id = " + id);
			// Instead use prepared statements
			ps = connection.prepareStatement("SELECT * FROM author where id = ?");
			ps.setLong(1, id);    // Not zero indexed. Sub in the value for the placeholder
			resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return getAuthorFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(resultSet, ps, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Author findAuthorByName(final String firstName, final String lastName) {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();

			ps = connection.prepareStatement("SELECT * FROM author where first_name = ? and last_name = ?");
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			resultSet = ps.executeQuery();

			if (resultSet.next()) {
				return getAuthorFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(resultSet, ps, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Author saveAuthor(final Author author) {

		Connection connection = null;
		PreparedStatement ps = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();

			ps = connection.prepareStatement("INSERT INTO author (first_name, last_name) values (?, ?)");
			ps.setString(1, author.getFirstName());
			ps.setString(2, author.getLastName());
			ps.execute();

			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
			if (resultSet.next()) {
				Long savedId = resultSet.getLong(1);
				return this.getById(savedId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(resultSet, ps, connection);
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Author updateAuthor(final Author author) {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();

			ps = connection.prepareStatement("UPDATE author SET first_name = ?, last_name = ? where id = ?");
			ps.setString(1, author.getFirstName());
			ps.setString(2, author.getLastName());
			ps.setLong(3, author.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(resultSet, ps, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this.getById(author.getId());
	}

	@Override
	public void deleteAuthorById(final Long id) {

		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = dataSource.getConnection();

			ps = connection.prepareStatement("DELETE from author where id = ?");
			ps.setLong(1, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(null, ps, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private Author getAuthorFromResultSet(final ResultSet resultSet) throws SQLException {

		Author author = new Author();
		author.setId(resultSet.getLong("id"));
		author.setFirstName(resultSet.getString("first_name"));
		author.setLastName(resultSet.getString("last_name"));
		return author;
	}

	private void closeAll(final ResultSet resultSet, final PreparedStatement ps, final Connection connection)
		throws SQLException {

		if (resultSet != null) {
			resultSet.close();
		}
		if (ps != null) {
			ps.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
}
