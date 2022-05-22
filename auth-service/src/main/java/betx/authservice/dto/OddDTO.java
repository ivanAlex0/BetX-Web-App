package betx.authservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class OddDTO {

    private Long id;
    private Float odd;
    private String oddType;
    private Boolean winner;
    private String fixture;
}
