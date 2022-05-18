package betx.authservice.repository;

import betx.authservice.model.AddressCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressCountryRepository extends JpaRepository<AddressCountry, Long> {

    Optional<AddressCountry> findByName(String name);
}
