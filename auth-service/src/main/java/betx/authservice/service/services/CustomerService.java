package betx.authservice.service.services;

import betx.authservice.dto.CustomerDTO;
import betx.authservice.model.Bet;
import betx.authservice.model.Wallet;
import betx.authservice.model.users.Customer;
import betx.authservice.model.users.User;

import java.util.List;

public interface CustomerService {

    Customer save(Customer customer);

    Bet placeBet(Bet bet, Long customerId);

    CustomerDTO authenticate(User user);

    Wallet deposit(Long customerId, Float amount);

    Wallet withdraw(Long customerId, Float amount);

    CustomerDTO checkBets(Customer customer);

    List<Bet> getBets(Long customerId);
}
