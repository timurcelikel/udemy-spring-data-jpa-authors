package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface BookRepository extends JpaRepository<Book, Long> {

	//Book jpaNamed(@Param("title") String title);
	@Query(value = "SELECT * FROM book WHERE title = :title", nativeQuery = true)
	Book findBookByTitleNativeQuery(@Param("title") String title);

	@Query("SELECT b FROM Book b where b.title = :title")
	Book findBookByTitleWithQueryNamed(@Param("title") String title);

	@Query("SELECT b FROM Book b where b.title = ?1")
	Book findBookByTitleWithQuery(String title);

	Optional<Book> findBookByTitle(String title);

	Book readByTitle(String title);

	Book getByTitle(String title);

	Stream<Book> findAllByTitleNotNull();

	@Async
	Future<Book> queryByTitle(String title);

}
