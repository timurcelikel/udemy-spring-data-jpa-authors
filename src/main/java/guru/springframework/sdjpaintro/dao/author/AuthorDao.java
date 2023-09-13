package guru.springframework.sdjpaintro.dao.author;

import guru.springframework.sdjpaintro.domain.Author;

import java.util.List;

public interface AuthorDao {

	Author getById(Long id);

	Author findAuthorByName(String firstName, String lastName);

	Author saveAuthor(Author author);

	Author updateAuthor(Author author);

	void deleteAuthorById(Long id);

	List<Author> listAuthorByLastNameLike(String lastName);
}
