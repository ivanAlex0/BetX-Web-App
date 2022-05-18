package betx.apiservice.APIModel;

import betx.apiservice.model.Team;
import betx.apiservice.model.Venue;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class APITeamObject {

    private Team team;
    private Venue venue;
}
