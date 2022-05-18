package betx.authservice.controller;

import betx.authservice.model.Bet;
import betx.authservice.model.Customer;
import betx.authservice.model.User;
import betx.authservice.service.impl.CustomerServiceImpl;
import betx.authservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/save")
    public ResponseEntity<Customer> test(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<Customer> auth(@RequestBody User user) {
        return new ResponseEntity<>(customerService.authenticate(user), HttpStatus.OK);
    }

    @PostMapping("/placeBet")
    public ResponseEntity<Bet> placeBet(@RequestParam(name = "customerId", required = false) long customerId, @RequestBody Bet bet) {
        return new ResponseEntity<>(customerService.placeBet(bet, customerId), HttpStatus.OK);
    }
}
