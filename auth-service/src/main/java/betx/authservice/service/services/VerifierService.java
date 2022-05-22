package betx.authservice.service.services;

import betx.authservice.model.users.User;
import betx.authservice.model.users.Verifier;

public interface VerifierService {
    Verifier save(User user);

    Verifier auth(User user);

    String changeCustomerStatus(Long customerId, Boolean verified);
    String changeCustomerSuspension(Long customerId, Boolean suspended);
}
