package betx.apiservice.service.impl;

import betx.apiservice.model.Position;
import betx.apiservice.repository.PositionRepository;
import betx.apiservice.service.services.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {

    @Autowired
    PositionRepository positionRepository;

    @Override
    public Position save(Position position) {
        log.info("New position saved for {rank}=" + position.getRank() + " and {team}=" + position.getTeam().getName());
        return positionRepository.save(
                Position
                        .builder()
                        .team(position.getTeam())
                        .rank(position.getRank())
                        .form(position.getForm())
                        .points(position.getPoints())
                        .build()
        );
    }
}
