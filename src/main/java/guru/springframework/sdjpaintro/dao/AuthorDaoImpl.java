package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class AuthorDaoImpl implements AuthorDao {

	private final DataSource dataSource;

	public AuthorDaoImpl(final DataSource dataSource) {

		this.dataSource = dataSource;
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
				Author author = new Author();
				author.setId(id);
				author.setFirstName(resultSet.getString("first_name"));
				author.setLastName(resultSet.getString("last_name"));

				return author;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
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
				Author author = new Author();
				author.setId(resultSet.getLong("id"));
				author.setFirstName(firstName);
				author.setLastName(lastName);

				return author;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
