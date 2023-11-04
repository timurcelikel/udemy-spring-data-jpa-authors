package guru.springframework.sdjpaintro.service.author;

import guru.springframework.sdjpaintro.entity.Author;

import java.util.List;

public interface AuthorQueryService extends AuthorService {

	Author getByIdQuery(Long id);

	Author getByIdTypedQuery(Long id);

	Author findAuthorByNameNative(String firstName, String lastName);

	Author findAuthorByNameCriteria(String firstName, String lastName);

	List<Author> findAll();
}
