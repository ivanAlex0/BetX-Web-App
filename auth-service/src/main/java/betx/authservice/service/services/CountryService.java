package betx.authservice.service.services;

import betx.authservice.model.AddressCountry;

import java.util.List;

public interface CountryService {
    void initCountries();
    List<AddressCountry> findAll();
}
