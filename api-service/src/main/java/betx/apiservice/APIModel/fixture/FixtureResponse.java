package betx.apiservice.APIModel.fixture;

import betx.apiservice.model.Fixture;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FixtureResponse {

    Fixture fixture;
    FixtureTeamObject teams;
}
