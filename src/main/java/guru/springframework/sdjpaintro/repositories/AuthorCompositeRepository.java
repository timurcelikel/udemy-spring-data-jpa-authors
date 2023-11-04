package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.entity.AuthorComposite;
import guru.springframework.sdjpaintro.entity.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {

}
