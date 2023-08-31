package guru.springframework.sdjpaintro.dao.author;

import guru.springframework.sdjpaintro.domain.Author;

public interface AuthorDao {

	Author getById(Long id);

	Author findAuthorByName(String firstName, String lastName);

	Author saveAuthor(Author author);

	Author updateAuthor(Author author);

	void deleteAuthorById(Long id);
}
