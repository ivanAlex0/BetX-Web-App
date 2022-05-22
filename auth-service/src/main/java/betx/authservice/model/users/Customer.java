package betx.authservice.model.users;

import betx.authservice.model.Address;
import betx.authservice.model.Bet;
import betx.authservice.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "customer_gen")
    private Long customerId;

    private String name;
    private Boolean verified;
    private Boolean suspended;

    @OneToOne
    private Address address;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "customer")
    private List<Bet> bets;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
    private Wallet wallet;
}
