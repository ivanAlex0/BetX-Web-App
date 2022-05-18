package betx.apiservice.service.impl;

import betx.apiservice.model.Team;
import betx.apiservice.repository.TeamRepository;
import betx.apiservice.service.services.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRepository teamRepository;

    @Override
    public Team save(Team team) {
        log.info("New Team saved with {name}=" + team.getName());
        return teamRepository.save(team);
    }
}
