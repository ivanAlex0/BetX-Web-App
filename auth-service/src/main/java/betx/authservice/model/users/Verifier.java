package betx.authservice.model.users;

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
public class Verifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "verifier_gen")
    private Long verifierId;

    @OneToOne
    private User user;
}
