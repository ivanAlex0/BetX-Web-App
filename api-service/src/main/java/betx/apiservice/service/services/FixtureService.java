package betx.apiservice.service.services;

import betx.apiservice.dto.FixtureDTO;
import betx.apiservice.model.Fixture;

import java.util.List;

public interface FixtureService {
    Fixture save(Fixture fixture);
    List<FixtureDTO> findAll();
}
