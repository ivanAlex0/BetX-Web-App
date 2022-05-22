package betx.authservice.repository;

import betx.authservice.model.Bet;
import betx.authservice.model.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findAllByCustomer(Customer customer);
}
