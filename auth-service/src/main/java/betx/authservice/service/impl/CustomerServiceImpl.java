package betx.authservice.service.impl;

import betx.authservice.model.*;
import betx.authservice.repository.CountryRepository;
import betx.authservice.repository.CustomerRepository;
import betx.authservice.repository.RoleRepository;
import betx.authservice.service.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    AddressServiceImpl addressService;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Customer save(Customer customer) {
        if (customer.getName().isEmpty()) {
            log.error("Customer's {name} cannot be empty");
            throw new RuntimeException("Customer's {name} cannot be empty");
        }

        ArrayList<Role> roles = new ArrayList<>() {{
            add(roleRepository.findByName("CUSTOMER"));
        }};
        customer.getUser().setRoles(roles);
        Country _customerCountry = customer.getAddress().getCountry();
        Country _country = countryRepository.findByName(_customerCountry.getName()).orElseThrow(
                () -> new RuntimeException("Address's {country} wasn't found in the database" + _customerCountry.getName())
        );
        customer.getAddress().setCountry(_country);

        Address _address = addressService.save(customer.getAddress());
        User _user = userService.saveUser(customer.getUser());

        return customerRepository.save(
                Customer
                        .builder()
                        .name(customer.getName())
                        .address(_address)
                        .user(_user)
                        .build()
        );
    }
}
