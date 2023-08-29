package guru.springframework.sdjpaintro.bootstrap;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({ "local", "default" })
@Component
public class DataInitializer implements CommandLineRunner {

	private final BookRepository bookRepository;

	public DataInitializer(final BookRepository bookRepository) {

		this.bookRepository = bookRepository;
	}

	@Override
	public void run(final String... args) throws Exception {

		bookRepository.deleteAll();

		Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse", null);
		bookRepository.save(bookDDD);

		Book bookSIA = new Book("Spring In Action", "232424", "Oreilly", null);
		bookRepository.save(bookSIA);

		bookRepository.findAll().forEach(book -> {
			System.out.println("Book Id: " + book.getId());
			System.out.println("Book Title: " + book.getTitle());
		});
	}
}
