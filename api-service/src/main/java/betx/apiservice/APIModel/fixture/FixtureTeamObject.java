package betx.apiservice.APIModel.fixture;

import betx.apiservice.model.Team;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FixtureTeamObject {

    Team home;
    Team away;
}
