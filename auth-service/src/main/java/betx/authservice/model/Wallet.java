package betx.authservice.model;

import betx.authservice.model.users.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "wallet_gen")
    private Long walletId;

    private Double balance;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
