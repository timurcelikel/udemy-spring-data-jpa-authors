package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.dao.author.AuthorDao;
import guru.springframework.sdjpaintro.dao.author.AuthorHibernateDaoImpl;
import guru.springframework.sdjpaintro.dao.author.AuthorJdbcDaoImpl;
import guru.springframework.sdjpaintro.dao.author.AuthorSpringJdbcTemplateDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DaoBeanConfiguration {

	private final DataSource dataSource;

	private final JdbcTemplate jdbcTemplate;

	public DaoBeanConfiguration(final DataSource dataSource, final JdbcTemplate jdbcTemplate) {

		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Bean
	public AuthorDao authorJdbcDao() {

		return new AuthorJdbcDaoImpl(dataSource);
	}

	@Bean
	public AuthorDao authorSpringJdbcTemplateDao() {

		return new AuthorSpringJdbcTemplateDaoImpl(jdbcTemplate);
	}

	@Bean
	public AuthorDao authorHibernateDao() {

		return new AuthorHibernateDaoImpl();
	}

}
