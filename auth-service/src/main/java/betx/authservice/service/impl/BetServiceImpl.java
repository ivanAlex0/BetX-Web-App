package betx.authservice.service.impl;

import betx.authservice.model.Bet;
import betx.authservice.repository.BetRepository;
import betx.authservice.service.services.BetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BetServiceImpl implements BetService {

    @Autowired
    BetRepository betRepository;

    @Override
    public Bet save(Bet bet) {
        Bet _bet = betRepository.save(
                Bet.builder()
                        .odds(bet.getOdds())
                        .customer(bet.getCustomer())
                        .amount(bet.getAmount())
                        .build()
        );
        log.info("New Bet saved with {betId}=" + _bet.getBetId());
        return _bet;
    }
}
