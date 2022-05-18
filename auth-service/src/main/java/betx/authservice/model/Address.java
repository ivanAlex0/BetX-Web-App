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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "address_gen")
    private Long addressId;

    private String number;

    private String street;

    private String city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private AddressCountry addressCountry;
}
