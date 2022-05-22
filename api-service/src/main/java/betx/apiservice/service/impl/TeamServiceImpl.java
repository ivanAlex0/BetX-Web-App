package betx.apiservice.service.impl;

import betx.apiservice.dto.TeamDTO;
import betx.apiservice.dto.TeamMapper;
import betx.apiservice.model.Fixture;
import betx.apiservice.model.Odd;
import betx.apiservice.model.Team;
import betx.apiservice.repository.TeamRepository;
import betx.apiservice.service.apiServices.APIHandler;
import betx.apiservice.service.services.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    FixtureServiceImpl fixtureService;

    @Autowired
    OddServiceImpl oddService;

    @Autowired
    APIHandler apiHandler;

    @Override
    public Team save(Team team) {
        Optional<Team> _team = teamRepository.findById(team.getId());

        if (_team.isPresent())
            return _team.get();

        try {
            log.info("New Team saved with {name}=" + team.getName());
            team.setHomeFixtures(new ArrayList<>());
            team.setAwayFixtures(new ArrayList<>());
            return teamRepository.save(team);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            log.error("Duplicate entry for team {name}=" + team.getName());
            return teamRepository.findById(team.getId()).orElse(null);
        }
    }

    @Override
    public TeamDTO searchTeam(String name, String country) {
        Optional<Team> _team = teamRepository.findByName(name);

        if (_team.isPresent()) {
            //return
            Team team = _team.get();
            if (team.getHomeFixtures().isEmpty() && team.getAwayFixtures().isEmpty()) {
                ArrayList<Fixture> fixtures = apiHandler.getFixturesByTeam(team);

                for (Fixture fixture : fixtures) {
                    if (team.getName().equals(fixture.getHome().getName())) {
                        Team _away = this.save(fixture.getAway());
                        fixture.setAway(_away);

                        Fixture _fix = fixtureService.save(fixture);
                        for (Odd odd : apiHandler.getOddsForFixture(fixture)) {
                            odd.setFixture(fixture);
                            Odd _odd = oddService.save(odd);
                            _fix.getOdds().add(_odd);
                        }

                        team.getHomeFixtures().add(_fix);
                    } else {
                        Team _home = this.save(fixture.getHome());
                        fixture.setHome(_home);

                        Fixture _fix = fixtureService.save(fixture);
                        for (Odd odd : apiHandler.getOddsForFixture(fixture)) {
                            odd.setFixture(fixture);
                            Odd _odd = oddService.save(odd);
                            _fix.getOdds().add(_odd);
                        }

                        team.getAwayFixtures().add(_fix);
                    }
                }
                Team _savedTeam = teamRepository.save(team);

                return new TeamMapper().teamToTeamDTO(_savedTeam);
            } else return new TeamMapper().teamToTeamDTO(team);
        } else {
            //fetch from api
            Team _fetchedTeam = apiHandler.getTeamByName(name, country);
            Team _savedTeam = this.save(_fetchedTeam);
            ArrayList<Fixture> fixtures = apiHandler.getFixturesByTeam(_fetchedTeam);

            for (Fixture fixture : fixtures) {
                if (_savedTeam.getName().equals(fixture.getHome().getName())) {
                    Team _away = this.save(fixture.getAway());
                    fixture.setAway(_away);

                    Fixture _fix = fixtureService.save(fixture);
                    for (Odd odd : apiHandler.getOddsForFixture(fixture)) {
                        odd.setFixture(fixture);
                        Odd _odd = oddService.save(odd);
                        _fix.getOdds().add(_odd);
                    }

                    _savedTeam.getHomeFixtures().add(_fix);
                } else {
                    Team _home = this.save(fixture.getHome());
                    fixture.setHome(_home);

                    Fixture _fix = fixtureService.save(fixture);
                    for (Odd odd : apiHandler.getOddsForFixture(fixture)) {
                        odd.setFixture(fixture);
                        Odd _odd = oddService.save(odd);
                        _fix.getOdds().add(_odd);
                    }

                    _savedTeam.getAwayFixtures().add(_fix);
                }
            }

            return new TeamMapper().teamToTeamDTO(_savedTeam);
        }
    }
}
