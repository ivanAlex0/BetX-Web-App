package betx.authservice.model;

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

    @OneToOne
    @JsonIgnore
    private Customer customer;
}
