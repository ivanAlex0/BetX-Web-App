package betx.apiservice.repository;

import betx.apiservice.model.Standing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandingRepository extends JpaRepository<Standing, Long> {
}
