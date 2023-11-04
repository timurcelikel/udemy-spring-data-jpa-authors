package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.entity.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
// Leaving the below line here as a reference to how to run DataInitializer each time
@ComponentScan(basePackages = { "guru.springframework.sdjpaintro.bootstrap" })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpringBootJpaSliceTest {

	@Autowired
	BookRepository bookRepository;

	//@Commit 		-- leaving this here but bad idea not to rollback test data
	//@Rollback(value = false)
	@Order(1)
	@Test
	void testJpaTestSlice() {

		long countBefore = bookRepository.count();
		assertThat(countBefore).isEqualTo(2);

		Book book = Book.builder().title("My Book").isbn("12345").publisher(
				"Self").build();
		bookRepository.save(book);
		long countAfter = bookRepository.count();

		assertThat(countBefore).isLessThan(countAfter);
	}

	@Order(2)
	@Test
	void testJpaTestSliceTransaction() {

		long countBefore = bookRepository.count();
		assertThat(countBefore).isEqualTo(2);
	}
}
