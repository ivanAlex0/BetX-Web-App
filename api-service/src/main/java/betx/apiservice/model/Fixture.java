package betx.apiservice.model;

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
public class Fixture {

    @Id
    private Long id;
    private String referee;
    private Long timestamp;

    @ManyToOne
    @JoinColumn(name = "home")
    private Team home;

    @ManyToOne
    @JoinColumn(name = "away")
    private Team away;

    @OneToMany(mappedBy = "fixture")
    private List<Odd> odds;
}
