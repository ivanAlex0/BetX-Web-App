package betx.apiservice.service.impl;

import betx.apiservice.model.League;
import betx.apiservice.model.Position;
import betx.apiservice.model.Standing;
import betx.apiservice.repository.StandingRepository;
import betx.apiservice.service.apiServices.APIHandler;
import betx.apiservice.service.services.StandingService;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StandingServiceImpl implements StandingService {

    @Autowired
    PositionServiceImpl positionService;

    @Autowired
    StandingRepository standingRepository;

    @Autowired
    LeagueServiceImpl leagueService;

    @Autowired
    APIHandler apiHandler;

    private boolean executed = false;

    @Override
    //@PostConstruct
    public void initStandings() {
        if (!executed) {
            List<Standing> standings = standingRepository.findAll();

            if (standings.size() == 0) {
                try {
                    List<League> leagues = leagueService.findAll();
                    for (League league : leagues) {
                        ArrayList<Position> positions = apiHandler.getStandingByLeague(league);
                        ArrayList<Position> transientPositions = new ArrayList<>();
                        for (Position position : positions) {
                            Position _pos = positionService.save(position);
                            transientPositions.add(_pos);
                        }

                        log.info("New standing saved for {league}=" + league.getName());
                        Standing _standing = standingRepository.save(
                                Standing.builder()
                                        .id(league.getId())
                                        .league(league)
                                        .standing(transientPositions)
                                        .build()
                        );
                    }

                    executed = true;
                } catch (JsonSyntaxException jsonSyntaxException) {
                    log.error(jsonSyntaxException.getMessage());
                }
            } else executed = true;
        }
    }
}
