package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.dao.author.*;
import guru.springframework.sdjpaintro.repositories.AuthorRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class AuthorDaoBeanConfiguration {

	private final DataSource dataSource;

	private final JdbcTemplate jdbcTemplate;

	private final EntityManagerFactory entityManagerFactory;

	private final AuthorRepository authorRepository;

	public AuthorDaoBeanConfiguration(final DataSource dataSource, final JdbcTemplate jdbcTemplate,
			final EntityManagerFactory entityManagerFactory, final AuthorRepository authorRepository) {

		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
		this.entityManagerFactory = entityManagerFactory;
		this.authorRepository = authorRepository;
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

		return new AuthorHibernateDaoImpl(entityManagerFactory);
	}

	@Bean
	public AuthorQueryDao authorHibernateQueryDao() {

		return new AuthorHibernateDaoImpl(entityManagerFactory);
	}

	@Bean
	public AuthorDao authorSpringDataDao() {

		return new AuthorSpringDataDaoImpl(authorRepository);
	}

}
