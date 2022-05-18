package betx.apiservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "standing_position")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "position_gen")
    private Long positionId;

    @Column(name = "rank_no")
    private Integer rank;
    private Integer points;
    private String form;

    @OneToOne
    private Team team;
}
