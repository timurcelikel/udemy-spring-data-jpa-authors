package guru.springframework.sdjpaintro.dao;
import guru.springframework.sdjpaintro.domain.Author;
public interface AuthorDao {

	Author getById(Long id);
}
