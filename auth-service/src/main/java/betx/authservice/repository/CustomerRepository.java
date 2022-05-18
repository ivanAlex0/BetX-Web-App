package betx.authservice.repository;

import betx.authservice.model.Customer;
import betx.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByUser(User user);
}
