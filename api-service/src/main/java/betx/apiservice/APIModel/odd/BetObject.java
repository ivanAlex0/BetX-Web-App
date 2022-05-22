package betx.apiservice.APIModel.odd;

import betx.apiservice.model.Odd;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
public class BetObject {

    private Integer id;
    private String name;
    private ArrayList<OddObject> values;
}
