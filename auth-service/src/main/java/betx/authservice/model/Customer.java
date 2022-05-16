package betx.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @OneToOne
    private Address address;

    @OneToOne
    private User user;
}
