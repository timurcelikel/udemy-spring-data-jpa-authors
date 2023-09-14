package guru.springframework.sdjpaintro.dao.author;

import guru.springframework.sdjpaintro.domain.Author;

import java.util.List;

public interface AuthorQueryDao extends AuthorDao {

	Author getByIdQuery(Long id);

	Author getByIdTypedQuery(Long id);

	List<Author> findAll();
}
