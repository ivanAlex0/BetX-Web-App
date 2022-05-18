package betx.apiservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
}
