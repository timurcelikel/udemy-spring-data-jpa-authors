package guru.springframework.sdjpaintro.dao.author;

import guru.springframework.sdjpaintro.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class AuthorHibernateDaoImpl implements AuthorDao {

	private final EntityManagerFactory entityManagerFactory;

	public AuthorHibernateDaoImpl(final EntityManagerFactory entityManagerFactory) {

		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	public Author getById(final Long id) {

		// Here we are working with the JPA entities and we are asking Hibernate to create a query for an Author with the passed in id.
		return getEntityManager().find(Author.class, id);
	}

	@Override
	public Author findAuthorByName(final String firstName, final String lastName) {

		return null;
	}

	@Override
	public Author saveAuthor(final Author author) {

		return null;
	}

	@Override
	public Author updateAuthor(final Author author) {

		return null;
	}

	@Override
	public void deleteAuthorById(final Long id) {

	}

	private EntityManager getEntityManager() {

		return entityManagerFactory.createEntityManager();
	}
}
