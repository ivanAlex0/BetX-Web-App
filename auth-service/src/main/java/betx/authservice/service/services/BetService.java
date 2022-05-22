package betx.authservice.service.services;

import betx.authservice.model.Bet;
import betx.authservice.model.users.Customer;

import java.util.List;

public interface BetService {
    Bet update(Bet bet);

    Bet save(Bet bet);

    boolean checkWinner(Bet bet);

    Float getTotalWin(Bet bet);

    List<Bet> findAllByCustomer(Customer customer);
}
