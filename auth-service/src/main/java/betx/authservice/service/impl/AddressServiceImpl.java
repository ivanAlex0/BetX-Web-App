package betx.authservice.service.impl;

import betx.authservice.model.Address;
import betx.authservice.repository.AddressRepository;
import betx.authservice.service.services.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        if (address.getNumber().isEmpty()) {
            log.error("Address's {number} cannot be empty");
            throw new RuntimeException("Address's {number} cannot be empty");
        }
        if (address.getStreet().isEmpty()) {
            log.error("Address's {street} cannot be empty");
            throw new RuntimeException("Address's {street} cannot be empty");
        }
        if (address.getCity().isEmpty()) {
            log.error("Address's {city} cannot be empty");
            throw new RuntimeException("Address's {city} cannot be empty");
        }


        return addressRepository.save(
                Address
                        .builder()
                        .number(address.getNumber())
                        .street(address.getStreet())
                        .city(address.getCity())
                        .country(address.getCountry())
                        .build()
        );
    }
}
