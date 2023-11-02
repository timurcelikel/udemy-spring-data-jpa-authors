package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.*;
import guru.springframework.sdjpaintro.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MySqlIntegrationTest {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookUuidRepository bookUuidRepository;

	@Autowired
	private AuthorUuidRepository authorUuidRepository;

	@Autowired
	private BookNaturalRepository bookNaturalRepository;

	@Autowired
	private AuthorCompositeRepository authorCompositeRepository;

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

	@Test
	void testBookNaturalPrimaryKey() {

		BookNatural bookNatural = new BookNatural();
		bookNatural.setIsbn("123434");
		bookNatural.setTitle("My Test Title");
		BookNatural savedBook = bookNaturalRepository.save(bookNatural);
		assertThat(savedBook).isNotNull();

		BookNatural fetchedBook = bookNaturalRepository.getReferenceById(savedBook.getTitle());
		assertThat(fetchedBook).isNotNull();
	}

	@Test
	void testAuthorCompositePrimaryKey() {

		NameId nameId = new NameId("Ben", "Hur");
		AuthorComposite authorComposite = new AuthorComposite();
		authorComposite.setFirstName(nameId.getFirstName());
		authorComposite.setLastName(nameId.getLastName());
		authorComposite.setCountry("US");
		AuthorComposite savedAuthor = authorCompositeRepository.save(authorComposite);
		AuthorComposite fetchedAuthor = authorCompositeRepository.getReferenceById(nameId);
		assertThat(fetchedAuthor).isNotNull();
	}
}
