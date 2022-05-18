package betx.authservice.service.impl;

import betx.authservice.model.*;
import betx.authservice.repository.AddressCountryRepository;
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
    AddressCountryRepository addressCountryRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BetServiceImpl betService;

    @Autowired
    WalletServiceImpl walletService;

    @Override
    public Customer save(Customer customer) {
        if (customer.getName() == null ||
                customer.getName().isEmpty() ||
                customer.getAddress() == null ||
                customer.getAddress().getNumber().isEmpty() ||
                customer.getAddress().getCity().isEmpty() ||
                customer.getAddress().getStreet().isEmpty() ||
                customer.getAddress().getAddressCountry() == null ||
                customer.getAddress().getAddressCountry().getName().isEmpty() ||
                customer.getUser().getEmail().isEmpty() ||
                customer.getUser().getPassword().isEmpty()
        ) {
            log.error("Customer has missing information");
            throw new RuntimeException("Customer has missing information");
        }

        ArrayList<Role> roles = new ArrayList<>() {{
            add(roleRepository.findByName("CUSTOMER"));
        }};
        customer.getUser().setRoles(roles);
        AddressCountry _customerAddressCountry = customer.getAddress().getAddressCountry();
        AddressCountry _Address_country = addressCountryRepository.findByName(_customerAddressCountry.getName()).orElseThrow(
                () -> new RuntimeException("Address's {country} wasn't found in the database" + _customerAddressCountry.getName())
        );
        customer.getAddress().setAddressCountry(_Address_country);

        Address _address = addressService.save(customer.getAddress());
        User _user = userService.saveUser(customer.getUser());

        Customer _customer = customerRepository.save(
                Customer
                        .builder()
                        .name(customer.getName())
                        .address(_address)
                        .user(_user)
                        .bets(new ArrayList<>())
                        .build()
        );
        log.info("New customer added with {name}=" + _customer.getName() + " and {email}=" + _customer.getUser().getEmail());
        return _customer;
    }

    @Override
    public Bet placeBet(Bet bet, Long customerId) {
        //checks
        if (bet.getOdds() == null || bet.getOdds().isEmpty() || bet.getAmount() == null || bet.getAmount() <= 0) {
            log.error("Bet information is incorrect");
            throw new RuntimeException("Bet information is incorrect");
        }

        Customer _customer = customerRepository.findById(customerId).orElseThrow(
                () -> {
                    log.error("No customer found for {customerId}=" + customerId);
                    throw new RuntimeException("No customer found for {customerId}=" + customerId);
                }
        );
        walletService.withdraw(_customer.getWallet(), bet.getAmount());
        bet.setCustomer(_customer);

        return betService.save(bet);
    }

    @Override
    public Customer authenticate(User user) {
        User _user = userService.authenticate(user);

        return customerRepository.findByUser(_user);
    }
}
