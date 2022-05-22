package betx.authservice.dto;

import betx.apiservice.model.Odd;
import betx.authservice.model.Bet;
import betx.authservice.model.users.Customer;

import java.util.ArrayList;

public class CustomerMapper {

    public CustomerDTO CustomerToCustomerDTO(Customer customer) {
        ArrayList<BetDTO> betDTOS = new ArrayList<>();
        for (Bet bet : customer.getBets()) {
            ArrayList<OddDTO> oddDTOS = new ArrayList<>();
            for (Odd odd : bet.getOdds()) {
                oddDTOS.add(
                        OddDTO.builder()
                                .id(odd.getId())
                                .odd(odd.getOdd())
                                .oddType(odd.getOddType())
                                .fixture(odd.getFixture().getHome().getName() + " vs " + odd.getFixture().getAway().getName())
                                .winner(odd.getWinner())
                                .build()
                );
            }

            betDTOS.add(
                    BetDTO.builder()
                            .odds(oddDTOS)
                            .amount(bet.getAmount())
                            .betId(bet.getBetId())
                            .winner(bet.getWinner())
                            .build()
            );
        }

        return CustomerDTO.builder()
                .address(customer.getAddress())
                .user(customer.getUser())
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .wallet(customer.getWallet())
                .bets(betDTOS)
                .build();
    }
}
