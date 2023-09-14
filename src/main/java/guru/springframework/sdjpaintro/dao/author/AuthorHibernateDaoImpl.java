package guru.springframework.sdjpaintro.dao.author;

import guru.springframework.sdjpaintro.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AuthorHibernateDaoImpl implements AuthorDao, AuthorQueryDao {

	private final EntityManagerFactory entityManagerFactory;

	public AuthorHibernateDaoImpl(final EntityManagerFactory entityManagerFactory) {

		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	public List<Author> findAll() {

		try (EntityManager em = getEntityManager()) {
			TypedQuery<Author> typedQuery = em.createNamedQuery("author_find_all", Author.class);
			return typedQuery.getResultList();
		}
	}

	@Override
	public List<Author> listAuthorByLastNameLike(final String lastName) {

		// Better to use try with resources
		EntityManager em = getEntityManager();
		try {
			Query query = em.createQuery("SELECT a from Author a where a.lastName like :last_name");
			query.setParameter("last_name", lastName + "%");
			List<Author> authors = query.getResultList();
			return authors;
		} finally {
			em.close();
		}
	}

	@Override
	public Author getByIdQuery(final Long id) {

		// This Non-Typed Query requires us to cast to Author
		EntityManager em = getEntityManager();
		Query query = em.createQuery("SELECT a from Author a where a.id = :id");
		query.setParameter("id", id);
		Author author = (Author) query.getSingleResult();        // Non-Typed Query requires casting
		em.close();
		return author;
	}

	@Override
	public Author getByIdTypedQuery(final Long id) {

		// This Non-Typed Query requires us to cast to Author
		EntityManager em = getEntityManager();
		TypedQuery<Author> query = em.createQuery("SELECT a from Author a where a.id = :id", Author.class);
		query.setParameter("id", id);
		Author author = query.getSingleResult();        // Typed Query does not require casting
		em.close();
		return author;
	}

	@Override
	public Author getById(final Long id) {

		EntityManager em = getEntityManager();
		// Here we are working with the JPA entities and we are asking Hibernate to create a query for an Author with the passed in id.
		Author author = em.find(Author.class, id);
		em.close();
		return author;
	}

	@Override
	public Author findAuthorByName(final String firstName, final String lastName) {

		EntityManager em = getEntityManager();
		// Hibernate JQL
		TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE a.firstName = :first_name and a.lastName = "
				+ ":last_name", Author.class);
		query.setParameter("first_name", firstName);
		query.setParameter("last_name", lastName);

		Author author = query.getSingleResult();
		em.close();

		return author;
	}

	@Override
	public Author saveAuthor(final Author author) {

		EntityManager em = getEntityManager();            // We are getting an EntityManager which gives us a connection to the db
		em.getTransaction().begin();                      // Start the transaction
		em.persist(author);                               // Save it
		em.flush();                                       // Flush it to the db
		em.getTransaction().commit();                     // Commit
		em.close();

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
