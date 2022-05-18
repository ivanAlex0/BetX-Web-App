package betx.apiservice.APIModel.standing;

import betx.apiservice.model.Position;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
public class StandingLeagueObject {

    private Integer id;
    private String name;
    private ArrayList<ArrayList<Position>> standings;
}
