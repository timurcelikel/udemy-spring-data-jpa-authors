package guru.springframework.sdjpaintro.service.author;

import guru.springframework.sdjpaintro.entity.Author;

import java.util.List;

public interface AuthorService {

	Author getById(Long id);

	Author findAuthorByName(String firstName, String lastName);

	Author saveAuthor(Author author);

	Author updateAuthor(Author author);

	void deleteAuthorById(Long id);

	List<Author> listAuthorByLastNameLike(String lastName);
}
