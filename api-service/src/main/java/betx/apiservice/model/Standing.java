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
public class Standing {

    @Id
    private Long id;

    @OneToOne
    private League league;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "standing_id")
    private List<Position> standing;
}
