package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	Optional<Author> findAuthorByFirstNameAndLastName(final String firstName, final String lastName);

}
