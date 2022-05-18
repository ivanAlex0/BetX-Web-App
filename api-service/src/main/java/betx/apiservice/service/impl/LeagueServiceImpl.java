package betx.apiservice.service.impl;

import betx.apiservice.model.League;
import betx.apiservice.model.Team;
import betx.apiservice.repository.LeagueRepository;
import betx.apiservice.service.apiServices.APIHandler;
import betx.apiservice.service.services.LeagueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
@EnableScheduling
public class LeagueServiceImpl implements LeagueService {

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    TeamServiceImpl teamService;

    @Autowired
    APIHandler apiHandler;

    @Autowired
    CountryServiceImpl countryService;

    @Override
    //@PostConstruct
    public void initLeagues() {
        List<League> leagues = leagueRepository.findAll();

        if (leagues.size() == 0) {
            List<League> fetchedLeagues = apiHandler.getLeagues();

            for (League fetchedLeague : fetchedLeagues) {
                for (Team team : fetchedLeague.getTeams()) {
                    teamService.save(team);
                }
                log.info("New league saved with {name}=" + fetchedLeague.getName());
                fetchedLeague.setCountry(countryService.findByName(fetchedLeague.getCountry().getName()));
                leagueRepository.save(fetchedLeague);
            }
        }
    }

    @Override
    public List<League> findAll() {
        return leagueRepository.findAll();
    }


}
