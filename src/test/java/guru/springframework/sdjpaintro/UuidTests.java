package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.AuthorUuid;
import guru.springframework.sdjpaintro.domain.BookUuid;
import guru.springframework.sdjpaintro.repositories.AuthorUuidRepository;
import guru.springframework.sdjpaintro.repositories.BookUuidRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = { "guru.springframework.sdjpaintro.bootstrap" })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UuidTests {

	@Autowired
	private BookUuidRepository bookUuidRepository;

	@Autowired
	private AuthorUuidRepository authorUuidRepository;

	@Test
	void testBookUuid() {

		BookUuid bookUuid = new BookUuid();
		bookUuid.setTitle("My Test Title");
		bookUuid.setIsbn("12345");
		BookUuid savedBook = bookUuidRepository.save(bookUuid);
		assertThat(savedBook).isNotNull();
		assertThat(savedBook.getId()).isNotNull();

		BookUuid fetchedBook = bookUuidRepository.getReferenceById(savedBook.getId());
		assertThat(fetchedBook).isNotNull();
	}

	@Test
	void testAuthorUuid() {

		AuthorUuid authorUuid = new AuthorUuid();
		authorUuid.setFirstName("Pee-Wee");
		authorUuid.setLastName("Herman");
		AuthorUuid savedAuthor = authorUuidRepository.save(authorUuid);
		assertThat(savedAuthor.getId()).isNotNull();

		AuthorUuid fetchedAuthor = authorUuidRepository.getReferenceById(savedAuthor.getId());
		assertThat(fetchedAuthor).isNotNull();
	}

}
