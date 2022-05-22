package betx.authservice.service.impl;

import betx.apiservice.model.Odd;
import betx.authservice.model.Bet;
import betx.authservice.model.users.Customer;
import betx.authservice.repository.BetRepository;
import betx.authservice.service.services.BetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BetServiceImpl implements BetService {

    @Autowired
    BetRepository betRepository;

    @Override
    public Bet update(Bet bet) {
        return betRepository.save(bet);
    }

    /**
     * Saves a new Bet into the DB
     *
     * @param bet The Bet to be saved
     * @return The Bet instance
     */
    @Override
    public Bet save(Bet bet) {
        Bet _bet = betRepository.save(
                Bet.builder()
                        .odds(bet.getOdds())
                        .customer(bet.getCustomer())
                        .amount(bet.getAmount())
                        .winner(null)
                        .build()
        );
        log.info("New Bet saved with {betId}=" + _bet.getBetId());
        return _bet;
    }

    /**
     * Check whether a given bet is winner or not
     *
     * @param bet The Bet to be checked
     * @return true if the Bet is winner, false otherwise
     */
    @Override
    public boolean checkWinner(Bet bet) {
        Bet _bet = betRepository.findById(bet.getBetId()).orElseThrow(
                () -> new RuntimeException("No bet found for {betId}=" + bet.getBetId())
        );

        boolean NULL = false;
        for (Odd odd : _bet.getOdds()) {
            if (odd.getWinner() == null)
                NULL = true;
            else if (!odd.getWinner()) {
                _bet.setWinner(Boolean.FALSE);
                this.update(_bet);
                log.info("Bet with id=" + _bet.getBetId() + " of customer {customerId}=" + bet.getCustomer().getCustomerId() + " is a looser!");
                return false;
            }
        }

        if(NULL)
            return false;
        log.info("Bet with id=" + _bet.getBetId() + " of customer {customerId}=" + bet.getCustomer().getCustomerId() + " is a winner!");
        _bet.setWinner(Boolean.TRUE);
        this.update(_bet);
        return true;
    }

    /**
     * Calculates the total amount of win a bet has
     *
     * @param bet The Bet whose amount in calculated
     * @return The total amount as a float
     */
    @Override
    public Float getTotalWin(Bet bet) {
        Float totalOdd = 1.0f;

        for (Odd odd : bet.getOdds()) {
            totalOdd *= odd.getOdd();
        }

        log.info("TOTAL= " + totalOdd * bet.getAmount());
        return totalOdd * bet.getAmount();
    }

    @Override
    public List<Bet> findAllByCustomer(Customer customer) {
        return betRepository.findAllByCustomer(customer);
    }
}
