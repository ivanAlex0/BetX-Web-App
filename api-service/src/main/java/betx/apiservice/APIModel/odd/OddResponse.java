package betx.apiservice.APIModel.odd;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
public class OddResponse {

    private ArrayList<BookmakerObject> bookmakers;
}
