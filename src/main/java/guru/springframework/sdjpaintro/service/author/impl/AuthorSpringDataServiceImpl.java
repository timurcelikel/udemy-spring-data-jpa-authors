package guru.springframework.sdjpaintro.service.author.impl;

import guru.springframework.sdjpaintro.entity.Author;
import guru.springframework.sdjpaintro.repositories.AuthorRepository;
import guru.springframework.sdjpaintro.service.author.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class AuthorSpringDataServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;

	public AuthorSpringDataServiceImpl(final AuthorRepository authorRepository) {

		this.authorRepository = authorRepository;
	}

	@Override
	public List<Author> findAllAuthorsByLastNameSortByFirstName(final String lastName, final Pageable pageable) {
		return null;
	}

	@Override
	public Author getById(final Long id) {

		return authorRepository.getReferenceById(id);
	}

	public List<Author> findAllAuthors() {

		return authorRepository.findAll();
	}

	public Author findAuthorByFirstAndLastNameNativeQuery(final String firstName, final String lastName) {

		return authorRepository.findAuthorByFirstAndLastNameWithNativeQuery(firstName, lastName);
	}

	public Author findAuthorByLastNameWithNamedQuery(final String lastName) {

		return authorRepository.findAuthorByLastNameWithQueryNamed(lastName);
	}

	public Author findAuthorByFirstNameWithQuery(final String firstName) {

		return authorRepository.findAuthorByFirstNameWithQuery(firstName);
	}

	public Future<Author> queryByFirstName(final String firstName) {

		return authorRepository.queryByFirstName(firstName);
	}

	@Override
	public Author findAuthorByName(final String firstName, final String lastName) {

		return authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName).orElseThrow(
				EntityNotFoundException::new);
	}

	public List<Author> findAllAuthorsByFirstName(final String firstName) {

		return authorRepository.findAllByFirstName(firstName);
	}

	@Override
	public Author saveAuthor(final Author author) {

		return authorRepository.save(author);
	}

	@Override
	public Author updateAuthor(final Author author) {

		Author foundAuthor = authorRepository.getReferenceById(author.getId());
		foundAuthor.setFirstName(author.getFirstName());
		foundAuthor.setFirstName(author.getLastName());

		return authorRepository.save(foundAuthor);
	}

	@Override
	public void deleteAuthorById(final Long id) {

		authorRepository.deleteById(id);
	}

	@Override
	public List<Author> listAuthorByLastNameLike(final String lastName) {

		return null;
	}

	public List<Author> findAllByFirstName(final String firstName) {
		return authorRepository.findAllByFirstName(firstName);
	}
}
