package betx.apiservice.service.impl;

import betx.apiservice.model.Odd;
import betx.apiservice.repository.OddRepository;
import betx.apiservice.service.services.OddService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OddServiceImpl implements OddService {

    @Autowired
    OddRepository oddRepository;

    @Override
    public Odd save(Odd odd) {
        Odd _odd = oddRepository.save(odd);
        log.info("New odd saved for {fixture}=" + odd.getFixture().getId());
        return _odd;
    }

    public List<Odd> findAll() {
        return oddRepository.findAll();
    }
}
