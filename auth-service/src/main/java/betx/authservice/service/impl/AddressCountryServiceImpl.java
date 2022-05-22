package betx.authservice.service.impl;

import betx.authservice.model.AddressCountry;
import betx.authservice.repository.AddressCountryRepository;
import betx.authservice.service.services.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class AddressCountryServiceImpl implements CountryService {

    @Autowired
    AddressCountryRepository addressCountryRepository;

    /**
     * Initializes the countries table in the DB
     */
    @Override
    @PostConstruct
    public void initCountries() {
        String[] countryCodes = Locale.getISOCountries();
        List<AddressCountry> countries = addressCountryRepository.findAll();

        if (countries.size() != countryCodes.length) {
            for (String countryCode : Locale.getISOCountries()) {
                Locale obj = new Locale("", countryCode);
                if (!countries.contains(AddressCountry.builder().name(obj.getDisplayCountry()).build())) {
                    AddressCountry _Address_country = addressCountryRepository.save(
                            AddressCountry.builder()
                                    .name(obj.getDisplayCountry())
                                    .build()
                    );
                    log.info("New country saved with {name}=" + _Address_country.getName());
                }
            }
        }
    }

    public List<AddressCountry> findAll(){
        return addressCountryRepository.findAll();
    }
}
