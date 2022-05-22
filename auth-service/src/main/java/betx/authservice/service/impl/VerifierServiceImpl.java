package betx.authservice.service.impl;

import betx.authservice.model.Role;
import betx.authservice.model.users.Customer;
import betx.authservice.model.users.User;
import betx.authservice.model.users.Verifier;
import betx.authservice.repository.CustomerRepository;
import betx.authservice.repository.RoleRepository;
import betx.authservice.repository.VerifierRepository;
import betx.authservice.service.services.VerifierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class VerifierServiceImpl implements VerifierService {

    @Autowired
    VerifierRepository verifierRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserServiceImpl userService;

    /**
     * Saves a verifier in the DB
     *
     * @param user The user to be saved
     * @return The Verifier instance
     */
    @Override
    public Verifier save(User user) {
        if (user.getEmail().isEmpty() ||
                user.getPassword().isEmpty()) {
            log.error("Verifier information is invali!");
            throw new RuntimeException("Please ensure the email and password meet the requirements");
        }

        ArrayList<Role> roles = new ArrayList<>() {{
            add(roleRepository.findByName("VERIFIER"));
        }};
        user.setRoles(roles);

        User _user = userService.saveUser(user);

        Verifier _verifier = verifierRepository.save(
                Verifier.builder()
                        .user(_user)
                        .build()
        );
        log.info("New Verifier added with {verifierId}=" + _verifier.getVerifierId());
        return _verifier;
    }

    /**
     * Authenticates a user in the DB
     *
     * @param user The user to be saved
     * @return The Verifier instance
     */
    @Override
    public Verifier auth(User user) {
        User _user = userService.authenticate(user);

        return verifierRepository.findByUser(_user).orElseThrow(
                () -> new RuntimeException("Invalid type of user. Try to log in as another type.")
        );
    }

    /**
     * Changes a customer verified status
     *
     * @param customerId The customer whose status will be changed
     * @param verified   The actual new status
     * @return A successful/not String
     */
    @Override
    public String changeCustomerStatus(Long customerId, Boolean verified) {
        Customer _customer = customerRepository.findById(customerId).orElseThrow(
                () -> {
                    log.error("No customer found for {customerId}=" + customerId);
                    throw new RuntimeException("No customer found for {customerId}=" + customerId);
                }
        );

        String result = "Customer with {customerId}=" + customerId + " changed status from " + _customer.getVerified() + " into " + verified;
        _customer.setVerified(verified);
        customerRepository.save(_customer);
        log.info(result);
        return result;
    }

    /**
     * Changes a customer suspended status
     *
     * @param customerId The customer whose status will be changed
     * @param suspended  The actual new status
     * @return A successful/not String
     */
    @Override
    public String changeCustomerSuspension(Long customerId, Boolean suspended) {
        Customer _customer = customerRepository.findById(customerId).orElseThrow(
                () -> {
                    log.error("No customer found for {customerId}=" + customerId);
                    throw new RuntimeException("No customer found for {customerId}=" + customerId);
                }
        );

        String result = "Customer with {customerId}=" + customerId + " changed suspension status from " + _customer.getSuspended() + " into " + suspended;
        _customer.setSuspended(suspended);
        customerRepository.save(_customer);
        log.info(result);
        return result;
    }
}
