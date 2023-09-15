package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	Optional<Author> findAuthorByFirstNameAndLastName(final String firstName, final String lastName);

	Stream<Author> findAllByFirstName(final String firstName);

	@Async
	Future<Author> queryByFirstName(final String firstName);

	@Query("SELECT a FROM Author a where a.firstName = ?1")
	Author findAuthorByFirstNameWithQuery(final String firstName);

	@Query("SELECT a FROM Author a where a.lastName = :lastName")
	Author findAuthorByLastNameWithQueryNamed(@Param("lastName") final String lastName);

	@Query(value = "SELECT * FROM author WHERE first_name = :firstName AND last_name = :lastName", nativeQuery = true)
	Author findAuthorByFirstAndLastNameWithNativeQuery(@Param("firstName") final String firstName, @Param("lastName") final String lastName);
}
