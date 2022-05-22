package betx.apiservice.dto;

import betx.apiservice.model.Odd;
import betx.apiservice.model.Team;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@Builder
public class FixtureDTO {

    private Long id;
    private String referee;
    private Long timestamp;
    private Team home;
    private Team away;
    private List<Odd> odds;
}
