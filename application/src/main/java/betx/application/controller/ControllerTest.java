package betx.application.controller;

import betx.authservice.model.Country;
import betx.authservice.model.Customer;
import betx.authservice.model.User;
import betx.authservice.service.impl.CustomerServiceImpl;
import betx.authservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@ComponentScan("betx.authservice")
public class ControllerTest {

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/save")
    public ResponseEntity<Customer> test(@RequestBody Customer customer) {
        System.out.println(customer);
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.OK);
    }

    @GetMapping("/securityTest")
    public ResponseEntity<Country> test1(@RequestBody Country country) {
        System.out.println(country);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<User> auth(@RequestBody User user) {
        System.out.println(user);
        return new ResponseEntity<>(userService.authenticate(user), HttpStatus.OK);

    }
}
