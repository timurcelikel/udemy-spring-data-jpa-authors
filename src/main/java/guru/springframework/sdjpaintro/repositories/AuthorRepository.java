package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	Page<Author> findAuthorByLastName(String lastName, Pageable pageable);
	
	Optional<Author> findAuthorByFirstNameAndLastName(final String firstName, final String lastName);

	List<Author> findAllByFirstName(final String firstName);

	List<Author> findAllByLastNameLike(final String lastName);

	List<Author> findAllByLastNameStartingWith(final String lastName);

	@Async
	Future<Author> queryByFirstName(final String firstName);

	@Query("SELECT a FROM Author a where a.firstName = ?1")
	Author findAuthorByFirstNameWithQuery(final String firstName);

	@Query("SELECT a FROM Author a where a.lastName = :lastName")
	Author findAuthorByLastNameWithQueryNamed(@Param("lastName") final String lastName);

	@Query(value = "SELECT * FROM author WHERE first_name = :firstName AND last_name = :lastName", nativeQuery = true)
	Author findAuthorByFirstAndLastNameWithNativeQuery(@Param("firstName") final String firstName,
		@Param("lastName") final String lastName);
}
