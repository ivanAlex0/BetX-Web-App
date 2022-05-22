package betx.apiservice.APIModel.odd;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
public class BookmakerObject {

    private Integer id;
    private String name;
    private ArrayList<BetObject> bets;
}
