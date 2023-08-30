package guru.springframework.sdjpaintro.bootstrap;

import guru.springframework.sdjpaintro.domain.Author;
import guru.springframework.sdjpaintro.domain.AuthorUuid;
import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.domain.BookUuid;
import guru.springframework.sdjpaintro.repositories.AuthorRepository;
import guru.springframework.sdjpaintro.repositories.AuthorUuidRepository;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import guru.springframework.sdjpaintro.repositories.BookUuidRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({ "local", "default" })
@Component
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

		Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse", null);
		bookRepository.save(bookDDD);

		Book bookSIA = new Book("Spring In Action", "232424", "Oreilly", null);
		bookRepository.save(bookSIA);

		bookRepository.findAll().forEach(book -> {
			System.out.println("Book Id: " + book.getId());
			System.out.println("Book Title: " + book.getTitle());
		});

		Author author = new Author();
		author.setFirstName("John");
		author.setLastName("Steinbeck");
		authorRepository.save(author);

		AuthorUuid authorUuid = new AuthorUuid();
		authorUuid.setFirstName("Joe");
		authorUuid.setLastName("Buck");
		AuthorUuid savedAuthor = authorUuidRepository.save(authorUuid);
		System.out.println("Saved Author UUID: " + savedAuthor.getId());

		BookUuid bookUuid = new BookUuid();
		bookUuid.setIsbn("1243434353");
		bookUuid.setTitle("East of Eden");
		BookUuid savedBook = bookUuidRepository.save(bookUuid);
		System.out.println("Saved Book UUID: " + savedBook.getId());
	}
}
