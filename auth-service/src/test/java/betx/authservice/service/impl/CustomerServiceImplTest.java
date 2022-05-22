package betx.authservice.service.impl;

import betx.apiservice.model.Odd;
import betx.authservice.model.*;
import betx.authservice.model.users.Customer;
import betx.authservice.model.users.User;
import betx.authservice.repository.AddressCountryRepository;
import betx.authservice.repository.CustomerRepository;
import betx.authservice.repository.RoleRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    AddressCountryRepository addressCountryRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    AddressServiceImpl addressService;

    @Mock
    WalletServiceImpl walletService;

    @Mock
    BetServiceImpl betService;

    @InjectMocks
    CustomerServiceImpl customerService;

    @Test(expected = RuntimeException.class)
    public void testSave_badInput() {
        customerService.save(Customer.builder().build());
    }

    @Test(expected = RuntimeException.class)
    public void testSave_countryNotFound() {
        Mockito.when(addressCountryRepository.findByName(Mockito.any())).thenReturn(null);

        customerService.save(validCustomer());
    }

    @Test
    public void testSave_happyFlow() {
        Mockito.when(addressCountryRepository.findByName(Mockito.any())).thenReturn(Optional.of(validCountry()));
        Mockito.when(userService.saveUser(Mockito.any())).thenReturn(validUser());
        Mockito.when(addressService.save(Mockito.any())).thenReturn(validAddress());
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(validCustomer());
        Mockito.when(roleRepository.findByName(Mockito.any())).thenReturn(Role.builder().build());

        Customer _customer = customerService.save(validCustomer());

        Assertions.assertEquals(_customer.getName(), validCustomer().getName());
    }

    @Test(expected = RuntimeException.class)
    public void testPlaceBet_Invalid() {
        customerService.placeBet(Bet.builder().build(), 12L);
    }

    @Test(expected = RuntimeException.class)
    public void testPlaceBet_notVerified() {
        Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(validCustomerNotVerified()));

        customerService.placeBet(validBet(), 1L);
    }

    @Test(expected = RuntimeException.class)
    public void testPlaceBet_suspended() {
        Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(validCustomerSuspended()));

        customerService.placeBet(validBet(), 1L);
    }

    @Test
    public void testPlaceBet_success() {
        Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(validCustomer()));
        Mockito.when(walletService.withdraw(Mockito.any(), Mockito.any())).thenReturn(Wallet.builder().build());
        Mockito.when(betService.save(Mockito.any())).thenReturn(Bet.builder().betId(12L).build());

        Bet _bet = customerService.placeBet(validBet(), 1L);

        Assertions.assertEquals(_bet.getBetId(), 12L);
    }


    public Address validAddress() {
        return Address.builder()
                .number("12")
                .street("st")
                .city("city")
                .addressCountry(AddressCountry.builder().name("country").build())
                .build();
    }

    public User validUser() {
        return User.builder()
                .email("email")
                .password("pass")
                .build();
    }

    public AddressCountry validCountry() {
        return AddressCountry.builder()
                .name("name")
                .countryId(12L)
                .build();
    }

    public Customer validCustomer() {
        return Customer.builder()
                .name("Name")
                .address(validAddress())
                .user(validUser())
                .verified(true)
                .suspended(false)
                .build();
    }


    public Customer validCustomerNotVerified() {
        return Customer.builder()
                .name("Name")
                .address(validAddress())
                .user(validUser())
                .verified(false)
                .build();
    }

    public Customer validCustomerSuspended() {
        return Customer.builder()
                .name("Name")
                .address(validAddress())
                .user(validUser())
                .verified(true)
                .suspended(true)
                .build();
    }

    public Bet validBet() {
        return Bet.builder()
                .odds(new ArrayList<>() {{
                    add(Odd.builder().build());
                }})
                .amount((float) 12L)
                .build();
    }
}
