package betx.apiservice.service.impl;

import betx.apiservice.model.Fixture;
import betx.apiservice.model.League;
import betx.apiservice.repository.FixtureRepository;
import betx.apiservice.service.apiServices.APIHandler;
import betx.apiservice.service.services.FixtureService;
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
public class FixtureServiceImpl implements FixtureService {

    @Autowired
    LeagueServiceImpl leagueService;

    @Autowired
    FixtureRepository fixtureRepository;

    @Autowired
    APIHandler apiHandler;

    private boolean executed = false;

    @Override
    //@PostConstruct
    public void initFixtures() {
        if (!executed) {
            List<Fixture> fixtures = fixtureRepository.findAll();
            List<League> leagues = leagueService.findAll();

            if (fixtures.size() == 0) {
                try {
                    for (League league : leagues) {
                        ArrayList<Fixture> fetchedFixtures = apiHandler.getFixturesForLeague(league);

                        for (Fixture fetchedFixture : fetchedFixtures) {
                            System.out.println(fetchedFixture.getHome());
                        }
                    }

                    executed = true;
                } catch (JsonSyntaxException jsonSyntaxException) {
                    log.error(jsonSyntaxException.getMessage());
                }
            } else executed = true;
        }
    }
}
