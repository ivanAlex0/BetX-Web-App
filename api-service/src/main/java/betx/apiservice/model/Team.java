package betx.apiservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    private Long id;

    private String name;

    private String code;

    private String logo;

    private String country;

    @OneToMany(mappedBy = "home")
    private List<Fixture> homeFixtures;

    @OneToMany(mappedBy = "away")
    private List<Fixture> awayFixtures;
}
