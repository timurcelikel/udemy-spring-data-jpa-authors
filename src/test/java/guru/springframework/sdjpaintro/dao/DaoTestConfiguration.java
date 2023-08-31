package guru.springframework.sdjpaintro.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DaoTestConfiguration {

	private final DataSource dataSource;

	private final JdbcTemplate jdbcTemplate;

	public DaoTestConfiguration(final DataSource dataSource, final JdbcTemplate jdbcTemplate) {

		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Bean
	public AuthorDao authorSpringJdbcTemplateDao() {

		return new AuthorSpringJdbcTemplateDaoImpl(jdbcTemplate);
	}

	@Bean
	public AuthorDao authorJdbcDao() {

		return new AuthorJdbcDaoImpl(dataSource);
	}

}
