package betx.authservice.service.services;

import betx.authservice.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User saveUser(User user);

    User authenticate(User user);
}
