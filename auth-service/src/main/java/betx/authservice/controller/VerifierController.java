package betx.authservice.controller;

import betx.authservice.model.users.Customer;
import betx.authservice.model.users.User;
import betx.authservice.model.users.Verifier;
import betx.authservice.service.impl.CustomerServiceImpl;
import betx.authservice.service.services.VerifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("verifier")
public class VerifierController {

    @Autowired
    VerifierService verifierService;

    @Autowired
    CustomerServiceImpl customerService;

    /**
     * Endpoint that saves a Verifier
     *
     * @param user The User info
     * @return the new Verifier instance
     */
    @PostMapping("/save")
    public ResponseEntity<Verifier> save(@RequestBody User user) {
        return new ResponseEntity<>(verifierService.save(user), HttpStatus.OK);
    }

    /**
     * Endpoint that authenticates a Verifier
     *
     * @param user The User info
     * @return the Verifier instance
     */
    @PostMapping("/auth")
    public ResponseEntity<Verifier> auth(@RequestBody User user) {
        return new ResponseEntity<>(verifierService.auth(user), HttpStatus.OK);
    }

    /**
     * Endpoint tha changes the verified status of a Customer
     *
     * @param customerId The Customer's id
     * @param verified   The new status
     * @return the Successful/not String
     */
    @PostMapping("changeStatus")
    public ResponseEntity<String> changeStatus(@RequestParam(name = "customerId") long customerId, @RequestParam(name = "verified") boolean verified) {
        return new ResponseEntity<>(verifierService.changeCustomerStatus(customerId, verified), HttpStatus.OK);
    }

    /**
     * Endpoint tha changes the suspended status of a Customer
     *
     * @param customerId The Customer's id
     * @param suspension The new status
     * @return the Successful/not String
     */
    @PostMapping("changeSuspension")
    public ResponseEntity<String> changeSuspension(@RequestParam(name = "customerId") long customerId, @RequestParam(name = "suspended") boolean suspension) {
        return new ResponseEntity<>(verifierService.changeCustomerSuspension(customerId, suspension), HttpStatus.OK);
    }

    /**
     * Endpoint that retrieves all Customers
     *
     * @return List of Customer
     */
    @GetMapping("findAll")
    public ResponseEntity<List<Customer>> findAll() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }
}
