package betx.authservice.repository;

import betx.authservice.model.users.User;
import betx.authservice.model.users.Verifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifierRepository extends JpaRepository<Verifier, Long> {

    Optional<Verifier> findByUser(User user);
}
