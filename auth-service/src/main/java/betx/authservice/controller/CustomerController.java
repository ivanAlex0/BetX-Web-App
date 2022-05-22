package betx.authservice.controller;

import betx.authservice.dto.CustomerDTO;
import betx.authservice.model.Bet;
import betx.authservice.model.Wallet;
import betx.authservice.model.users.Customer;
import betx.authservice.model.users.User;
import betx.authservice.service.impl.CustomerServiceImpl;
import betx.authservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    UserServiceImpl userService;

    /**
     * Endpoint that saves Customer in the DB
     *
     * @param customer The Customer info
     * @return the new Customer instance
     */
    @PostMapping("/save")
    public ResponseEntity<Customer> test(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.OK);
    }

    /**
     * Endpoint that authenticates a Customer
     *
     * @param user The User information
     * @return The CustomerDTO instance
     */
    @PostMapping("/auth")
    public ResponseEntity<CustomerDTO> auth(@RequestBody User user) {
        return new ResponseEntity<>(customerService.authenticate(user), HttpStatus.OK);
    }

    /**
     * Endpoint that places a Bet
     *
     * @param customerId The Customer's id
     * @param bet        The Bet info
     * @return The new Bet instance
     */
    @PostMapping("/placeBet")
    public ResponseEntity<Bet> placeBet(@RequestParam(name = "customerId", required = false) long customerId, @RequestBody Bet bet) {
        return new ResponseEntity<>(customerService.placeBet(bet, customerId), HttpStatus.OK);
    }

    /**
     * Endpoint that deposits an amount
     *
     * @param customerId The Customer who deposits
     * @param amount     The amount
     * @return The updated Wallet instance
     */
    @PostMapping("deposit")
    public ResponseEntity<Wallet> deposit(@RequestParam(name = "customerId", required = false) long customerId, @RequestParam(name = "amount", required = false) float amount) {
        return new ResponseEntity<>(customerService.deposit(customerId, amount), HttpStatus.OK);
    }

    /**
     * Endpoint that withdraws an amount
     *
     * @param customerId The Customer who withdraws
     * @param amount     The amount
     * @return The updated Wallet instance
     */
    @PostMapping("withdraw")
    public ResponseEntity<Wallet> withdraw(@RequestParam(name = "customerId", required = false) long customerId, @RequestParam(name = "amount", required = false) float amount) {
        return new ResponseEntity<>(customerService.withdraw(customerId, amount), HttpStatus.OK);
    }

    /**
     * Endpoint that checks all bets of a customer
     *
     * @param customer The Customer info
     * @return the updated CustomerDTO instance
     */
    @PostMapping("checkBets")
    public ResponseEntity<CustomerDTO> checkBets(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.checkBets(customer), HttpStatus.OK);
    }

    /**
     * Endpoint that retrieves all Bets of a Customer
     *
     * @param customerId The Customer's id
     * @return List of Bet
     */
    @GetMapping("getBets")
    public ResponseEntity<List<Bet>> getBets(@RequestParam(name = "customerId") long customerId) {
        return new ResponseEntity<>(customerService.getBets(customerId), HttpStatus.OK);
    }
}
