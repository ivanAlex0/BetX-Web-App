package betx.apiservice.APIModel;

import betx.apiservice.model.League;
import betx.apiservice.model.api.Country;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class APILeagueObject {

    private League league;
    private Country country;
}
