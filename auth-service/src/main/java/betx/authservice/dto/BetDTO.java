package betx.authservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
@Builder
public class BetDTO {

    private Long betId;
    private Float amount;
    private Boolean winner;
    private ArrayList<OddDTO> odds;
}
