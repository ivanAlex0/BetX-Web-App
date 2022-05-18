package betx.apiservice.service.impl;

import betx.apiservice.model.api.Country;
import betx.apiservice.repository.CountryRepository;
import betx.apiservice.service.apiServices.APIHandler;
import betx.apiservice.service.services.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    APIHandler apiHandler;

    //@PostConstruct
    @Override
    public void initCountries() {
        List<Country> countries = countryRepository.findAll();

        if (countries.size() == 0) {
            ArrayList<Country> fetchedCountries = apiHandler.getCountries();
            for (Country fetchedCountry : fetchedCountries) {
                log.info("New Country saved for {name}=" + fetchedCountry.getName());
                countryRepository.save(fetchedCountry);
            }
        }
    }

    public Country findByName(String name) {
        return countryRepository.findByName(name).orElseThrow(
                () -> {
                    log.error("No country found for {name}=" + name);
                    throw new RuntimeException("No country found for {name}=" + name);
                }
        );
    }
}
