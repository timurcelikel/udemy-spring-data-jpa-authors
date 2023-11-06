package guru.springframework.sdjpaintro.service;

import guru.springframework.sdjpaintro.repositories.AuthorRepository;
import guru.springframework.sdjpaintro.service.author.AuthorQueryService;
import guru.springframework.sdjpaintro.service.author.AuthorService;
import guru.springframework.sdjpaintro.service.author.impl.AuthorHibernateServiceImpl;
import guru.springframework.sdjpaintro.service.author.impl.AuthorJdbcServiceImpl;
import guru.springframework.sdjpaintro.service.author.impl.AuthorSpringDataJpaServiceImpl;
import guru.springframework.sdjpaintro.service.author.impl.AuthorSpringJdbcTemplateServiceImpl;
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
	public AuthorService authorJdbcDao() {

		return new AuthorJdbcServiceImpl(dataSource);
	}

	@Bean
	public AuthorService authorSpringJdbcTemplateDao() {

		return new AuthorSpringJdbcTemplateServiceImpl(jdbcTemplate);
	}

	@Bean
	public AuthorService authorHibernateDao() {

		return new AuthorHibernateServiceImpl(entityManagerFactory);
	}

	@Bean
	public AuthorQueryService authorHibernateQueryDao() {

		return new AuthorHibernateServiceImpl(entityManagerFactory);
	}

	@Bean
	public AuthorService authorSpringDataDao() {

		return new AuthorSpringDataJpaServiceImpl(authorRepository);
	}

}
