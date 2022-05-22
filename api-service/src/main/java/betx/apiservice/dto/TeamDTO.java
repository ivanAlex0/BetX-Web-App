package betx.apiservice.dto;

import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    private Long id;
    private String name;
    private String code;
    private String logo;
    private String country;
    private List<FixtureDTO> homeFixtures;
    private List<FixtureDTO> awayFixtures;
}
