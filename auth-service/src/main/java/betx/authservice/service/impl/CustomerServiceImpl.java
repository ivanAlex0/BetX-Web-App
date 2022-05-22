package betx.authservice.service.impl;

import betx.authservice.dto.CustomerDTO;
import betx.authservice.dto.CustomerMapper;
import betx.authservice.model.*;
import betx.authservice.model.users.Customer;
import betx.authservice.model.users.User;
import betx.authservice.repository.AddressCountryRepository;
import betx.authservice.repository.CustomerRepository;
import betx.authservice.repository.RoleRepository;
import betx.authservice.service.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Saves a customer in the DB
     *
     * @param customer Customer to be saved
     * @return The customer instance
     */
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

        User _user = userService.saveUser(customer.getUser());
        Address _address = addressService.save(customer.getAddress());

        Customer _customer = customerRepository.save(
                Customer
                        .builder()
                        .name(customer.getName())
                        .address(_address)
                        .user(_user)
                        .bets(new ArrayList<>())
                        .verified(false)
                        .suspended(false)
                        .build()
        );
        Wallet _wallet = walletService.save(_customer);
        _customer.setWallet(_wallet);
        log.info("New customer added with {name}=" + _customer.getName() + " and {email}=" + _customer.getUser().getEmail());
        return _customer;
    }

    /**
     * Places a new Bet
     *
     * @param bet        Bet object to be saved in the DB
     * @param customerId Customer that places the bet
     * @return The Bet instance
     */
    @Override
    public Bet placeBet(Bet bet, Long customerId) {
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

        if (!_customer.getVerified()) {
            log.error("Customer with {customerId}=" + customerId + " is not verified yet!");
            throw new RuntimeException("Your account was not yet verified. Please wait 24 to 48 hours.");
        }

        if (_customer.getSuspended()) {
            log.error("Customer with {customerId}=" + customerId + " is suspended!");
            throw new RuntimeException("Your account was suspended. Please contact us at betx@yahoo.com");
        }

        walletService.withdraw(_customer.getWallet(), bet.getAmount());
        bet.setCustomer(_customer);

        return betService.save(bet);
    }

    /**
     * Authenticates a user in the DB
     *
     * @param user The user to be authenticated
     * @return The CustomerDTO instance
     */
    @Override
    public CustomerDTO authenticate(User user) {
        User _user = userService.authenticate(user);

        Customer _customer = customerRepository.findByUser(_user).orElseThrow(
                () -> new RuntimeException("Invalid type of user. Try to log in as another type.")
        );

        //bet checking
        if (_customer.getBets() != null)
            this.checkBets(_customer);

        return new CustomerMapper().CustomerToCustomerDTO(_customer);
    }

    /**
     * Deposits an amount of money to a customer
     *
     * @param customerId The customer that deposits
     * @param amount     The amount to be deposited
     * @return The Wallet instance
     */
    @Override
    public Wallet deposit(Long customerId, Float amount) {
        if (amount <= 0) {
            log.error("Amount <= 0 was tried to be deposited for {customerId}=" + customerId);
            throw new RuntimeException("You cannot deposit an amount that is less or equal to 0");
        }

        Customer _customer = customerRepository.findById(customerId).orElseThrow(
                () -> {
                    log.error("No customer found for {customerId}=" + customerId);
                    throw new RuntimeException("No customer found for {customerId}=" + customerId);
                }
        );

        Wallet _wallet = walletService.deposit(_customer.getWallet(), amount);
        return walletService.update(_wallet);
    }

    /**
     * Withdraws an amount of money to a customer
     *
     * @param customerId The customer that Withdraws
     * @param amount     The amount to be Withdrawn
     * @return The Wallet instance
     */
    @Override
    public Wallet withdraw(Long customerId, Float amount) {
        if (amount <= 0) {
            log.error("Amount <= 0 was tried to be withdraw for {customerId}=" + customerId);
            throw new RuntimeException("You cannot withdraw an amount that is less or equal to 0");
        }

        Customer _customer = customerRepository.findById(customerId).orElseThrow(
                () -> {
                    log.error("No customer found for {customerId}=" + customerId);
                    throw new RuntimeException("No customer found for {customerId}=" + customerId);
                }
        );

        if (!_customer.getVerified()) {
            log.error("Customer with {customerId}=" + customerId + " is not verified yet!");
            throw new RuntimeException("Your account was not yet verified. Please wait 24 to 48 hours.");
        }

        if (_customer.getSuspended()) {
            log.error("Customer with {customerId}=" + customerId + " is not suspended!");
            throw new RuntimeException("Your account was suspended. Please contact us at betx@yahoo.com");
        }

        Wallet _wallet = walletService.withdraw(_customer.getWallet(), amount);
        return walletService.update(_wallet);
    }

    /**
     * Checks all bets of a particular customer
     *
     * @param customer Customer whose bets are checked
     * @return The Customer instance
     */
    @Override
    public CustomerDTO checkBets(Customer customer) {
        Customer _customer = customerRepository.findById(customer.getCustomerId()).orElseThrow(
                () -> new RuntimeException("No customer could be found for {customerId}=" + customer.getCustomerId())
        );

        for (Bet bet : _customer.getBets()) {
            if (bet.getWinner() == null) {
                if (betService.checkWinner(bet)) {
                    Wallet _wallet = walletService.deposit(_customer.getWallet(), betService.getTotalWin(bet));
                    walletService.update(_wallet);
                }
            }
        }

        _customer.setBets(betService.findAllByCustomer(_customer));
        return new CustomerMapper().CustomerToCustomerDTO(_customer);
    }

    /**
     * Retrieves all bets of a customer
     *
     * @param customer Customer whose bets are retieved
     * @return List of all Bets
     */
    @Override
    public List<Bet> getBets(Long customerId) {
        Customer _customer = customerRepository.findById(customerId).orElseThrow(
                () -> new RuntimeException("No customer could be found for {customerId}=" + customerId)
        );

        return _customer.getBets();
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }


}
