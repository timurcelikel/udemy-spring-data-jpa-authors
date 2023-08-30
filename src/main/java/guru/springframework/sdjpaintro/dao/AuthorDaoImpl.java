package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();

			// Definitely don't do this in prod without a prepared statement and bind parameters
			// Can lead to SQL injection
			resultSet = statement.executeQuery("SELECT * FROM author where id = " + id);
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
