package betx.apiservice.service.services;

import betx.apiservice.dto.TeamDTO;
import betx.apiservice.model.Team;

public interface TeamService {

    Team save(Team team);

    TeamDTO searchTeam(String name, String country);
}
