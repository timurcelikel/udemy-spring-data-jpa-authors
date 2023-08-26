package guru.springframework.sdjpaintro.bootstrap;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

	private final BookRepository bookRepository;

	public DataInitializer(final BookRepository bookRepository) {

		this.bookRepository = bookRepository;
	}

	@Override
	public void run(final String... args) throws Exception {

		Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse");
		bookRepository.save(bookDDD);

		Book bookSIA = new Book("Spring In Action", "232424", "Oreilly");
		bookRepository.save(bookSIA);

		bookRepository.findAll().forEach(book -> {
			System.out.println("Book Id: " + book.getId());
			System.out.println("Book Title: " + book.getTitle());
		});
	}
}
