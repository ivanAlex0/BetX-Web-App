package betx.authservice.service.services;

import betx.authservice.model.Bet;
import betx.authservice.model.Customer;
import betx.authservice.model.User;

public interface CustomerService {

    Customer save(Customer customer);
    Bet placeBet(Bet bet, Long customerId);
    Customer authenticate(User user);
}
