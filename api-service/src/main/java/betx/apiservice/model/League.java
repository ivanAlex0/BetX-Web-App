package betx.apiservice.model;

import betx.apiservice.model.api.Country;
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
public class League {

    @Id
    private Long id;

    private String name;

    private String logo;

    @OneToMany
    @JoinColumn(name = "league_id")
    private List<Team> teams;

    @ManyToOne
    @JoinColumn(name = "country_id")
    Country country;
}
