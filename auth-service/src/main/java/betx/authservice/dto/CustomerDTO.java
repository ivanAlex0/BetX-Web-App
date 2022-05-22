package betx.authservice.dto;

import betx.authservice.model.Address;
import betx.authservice.model.users.User;
import betx.authservice.model.Wallet;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
@Builder
public class CustomerDTO {

    private Long customerId;
    private String name;
    private User user;
    private Address address;
    private Wallet wallet;
    private ArrayList<BetDTO> bets;

}
