package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.domain.AuthorComposite;
import guru.springframework.sdjpaintro.domain.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {

}
