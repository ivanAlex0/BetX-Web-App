package betx.apiservice.APIModel;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class APIResponse<T> {

    private String get;
    private ArrayList<String> errors;
    private Integer results;
    private List<T> response;
}
