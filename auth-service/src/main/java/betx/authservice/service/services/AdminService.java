package betx.authservice.service.services;

import betx.authservice.model.users.Admin;
import betx.authservice.model.users.User;

public interface AdminService {
    Admin save(User user);

    Admin auth(User user);

    String changeOddStatus(Long oddId, Boolean status);
}
