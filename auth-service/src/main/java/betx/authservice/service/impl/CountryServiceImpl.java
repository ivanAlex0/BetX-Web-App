package betx.authservice.service.impl;

import betx.authservice.model.Country;
import betx.authservice.repository.CountryRepository;
import betx.authservice.service.services.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Override
    @PostConstruct
    public void initCountries() {
        String[] countryCodes = Locale.getISOCountries();
        List<Country> countries = countryRepository.findAll();

        if (countries.size() != countryCodes.length) {
            for (String countryCode : Locale.getISOCountries()) {
                Locale obj = new Locale("", countryCode);
                if (!countries.contains(Country.builder().name(obj.getDisplayCountry()).build())) {
                    Country _country = countryRepository.save(
                            Country.builder()
                                    .name(obj.getDisplayCountry())
                                    .build()
                    );
                    log.info("New country saved with {name}=" + _country.getName());
                }
            }
        }
    }
}
