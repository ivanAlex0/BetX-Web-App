package betx.apiservice.service.impl;

import betx.apiservice.dto.FixtureDTO;
import betx.apiservice.dto.TeamMapper;
import betx.apiservice.model.Fixture;
import betx.apiservice.repository.FixtureRepository;
import betx.apiservice.service.apiServices.APIHandler;
import betx.apiservice.service.services.FixtureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FixtureServiceImpl implements FixtureService {

    @Autowired
    FixtureRepository fixtureRepository;

    @Autowired
    APIHandler apiHandler;

    @Override
    public Fixture save(Fixture fixture) {
        Fixture _fixture = fixtureRepository.save(
                Fixture.builder()
                        .referee(fixture.getReferee())
                        .timestamp(fixture.getTimestamp())
                        .id(fixture.getId())
                        .home(fixture.getHome())
                        .away(fixture.getAway())
                        .odds(new ArrayList<>())
                        .build()
        );
        log.info("New fixture added with {fixtureId}=" + _fixture.getId());
        return _fixture;
    }

    @Override
    public List<FixtureDTO> findAll() {
        return fixtureRepository.findAll().stream()
                .map(new TeamMapper()::fixtureToFixtureDTO)
                .collect(Collectors.toList());
    }


}
