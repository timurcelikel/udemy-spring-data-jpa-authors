package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SpringBootJpaSliceTest {

	@Autowired
	BookRepository bookRepository;

	@Test
	void testJpaTestSlice() {

		long countBefore = bookRepository.count();
		bookRepository.save(new Book("My Book", "12345", "Self"));
		long countAfter = bookRepository.count();

		assertThat(countBefore).isLessThan(countAfter);
	}
}
