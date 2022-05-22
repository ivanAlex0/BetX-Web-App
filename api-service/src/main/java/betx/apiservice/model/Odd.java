package betx.apiservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Odd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "odd_gen")
    private Long id;

    private Float odd;
    private String oddType;
    private Boolean winner;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "fixture_id")
    private Fixture fixture;
}
