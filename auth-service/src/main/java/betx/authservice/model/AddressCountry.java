package betx.authservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "address_country")
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "country_gen")
    private Long countryId;

    private String name;
}
