package guru.springframework.sdjpaintro.dao.author;

import guru.springframework.sdjpaintro.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

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

		// Hibernate JQL
		TypedQuery<Author> query = getEntityManager().createQuery("SELECT a FROM Author a WHERE a.firstName = :first_name and a.lastName = "
				+ ":last_name", Author.class);
		query.setParameter("first_name", firstName);
		query.setParameter("last_name", lastName);

		return query.getSingleResult();
	}

	@Override
	public Author saveAuthor(final Author author) {

		EntityManager em = getEntityManager();            // We are getting an EntityManager which gives us a connection to the db
		em.getTransaction().begin();                      // Start the transaction
		em.persist(author);                               // Save it
		em.flush();                                       // Flush it to the db
		em.getTransaction().commit();                     // Commit

		return author;
	}

	@Override
	public Author updateAuthor(final Author author) {

		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.merge(author);        // Take an existing entity and update it
		em.flush();
		em.clear();            // Clear Hibernate's first level cache to force it to go back out to the database
		em.getTransaction().commit();

		return em.find(Author.class, author.getId());
	}

	@Override
	public void deleteAuthorById(final Long id) {

		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Author author = em.find(Author.class, id);
		em.remove(author);
		em.flush();
		em.getTransaction().commit();
	}

	private EntityManager getEntityManager() {

		return entityManagerFactory.createEntityManager();
	}
}
