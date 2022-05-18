package betx.apiservice.service.services;

import betx.apiservice.model.League;

import java.util.List;

public interface LeagueService {

    void initLeagues();

    List<League> findAll();
}
