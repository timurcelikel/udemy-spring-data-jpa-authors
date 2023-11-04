package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.entity.AuthorUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorUuidRepository extends JpaRepository<AuthorUuid, UUID> {

}
