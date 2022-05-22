package betx.authservice.model;


import betx.apiservice.model.Odd;
import betx.authservice.model.users.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "bet_gen")
    private Long betId;

    private Float amount;
    private Boolean winner;

    @ManyToMany
    private List<Odd> odds;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
