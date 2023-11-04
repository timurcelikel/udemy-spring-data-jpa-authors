package guru.springframework.sdjpaintro.bootstrap;

import guru.springframework.sdjpaintro.entity.Author;
import guru.springframework.sdjpaintro.entity.AuthorUuid;
import guru.springframework.sdjpaintro.entity.Book;
import guru.springframework.sdjpaintro.entity.BookUuid;
import guru.springframework.sdjpaintro.repositories.AuthorRepository;
import guru.springframework.sdjpaintro.repositories.AuthorUuidRepository;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import guru.springframework.sdjpaintro.repositories.BookUuidRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({ "test" })
@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

	private final BookRepository bookRepository;

	private final AuthorRepository authorRepository;

	private final AuthorUuidRepository authorUuidRepository;

	private final BookUuidRepository bookUuidRepository;

	public DataInitializer(final BookRepository bookRepository, final AuthorRepository authorRepository,
			final AuthorUuidRepository authorUuidRepository,
			final BookUuidRepository bookUuidRepository) {

		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.authorUuidRepository = authorUuidRepository;
		this.bookUuidRepository = bookUuidRepository;
	}

	@Override
	public void run(final String... args) throws Exception {

		bookRepository.deleteAll();
		authorRepository.deleteAll();
		authorUuidRepository.deleteAll();
		bookUuidRepository.deleteAll();

		Book bookDDD = Book.builder().title("Domain Driven Design").isbn("123").publisher("Random House").build();
		bookRepository.save(bookDDD);

		Book bookSIA = Book.builder().title("Spring In Action").isbn("23424").publisher("Oreilly").build();
		bookRepository.save(bookSIA);

		bookRepository.findAll().forEach(book -> {
			log.info("Book Id: " + book.getId());
			log.info("Book Title: " + book.getTitle());
		});

		Author author = Author.builder().firstName("John").lastName("Steinbeck").build();
		authorRepository.save(author);

		Author author2 = Author.builder().firstName("Robert").lastName("Martin").build();
		authorRepository.save(author2);

		AuthorUuid authorUuid = AuthorUuid.builder().firstName("Joe").lastName("Buck").build();
		AuthorUuid savedAuthor = authorUuidRepository.save(authorUuid);
		log.info("Saved Author UUID: " + savedAuthor.getId());

		BookUuid bookUuid = new BookUuid();
		bookUuid.setIsbn("1243434353");
		bookUuid.setTitle("East of Eden");
		BookUuid savedBook = bookUuidRepository.save(bookUuid);
		log.info("Saved Book UUID: " + savedBook.getId());
	}
}
