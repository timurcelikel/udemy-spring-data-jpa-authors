package guru.springframework.sdjpaintro.service.author;

import guru.springframework.sdjpaintro.entity.Author;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {

	List<Author> findAllAuthorsByLastName(final String lastName, final Pageable pageable);

	Author getById(Long id);

	Author findAuthorByName(String firstName, String lastName);

	Author saveAuthor(Author author);

	Author updateAuthor(Author author);

	void deleteAuthorById(Long id);

	List<Author> listAuthorByLastNameLike(String lastName);
}
