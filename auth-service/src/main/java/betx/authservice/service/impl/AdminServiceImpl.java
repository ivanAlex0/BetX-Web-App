package betx.authservice.service.impl;

import betx.apiservice.model.Odd;
import betx.apiservice.repository.OddRepository;
import betx.authservice.model.Role;
import betx.authservice.model.users.Admin;
import betx.authservice.model.users.User;
import betx.authservice.repository.AdminRepository;
import betx.authservice.repository.RoleRepository;
import betx.authservice.service.services.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    OddRepository oddRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserServiceImpl userService;

    /**
     * Saves a new Admin in the DB
     *
     * @param user The user to be saved
     * @return The Admin instance
     */
    @Override
    public Admin save(User user) {
        if (user.getEmail().isEmpty() ||
                user.getPassword().isEmpty()) {
            log.error("Admin information is invalid!");
            throw new RuntimeException("Please ensure the email and password meet the requirements");
        }

        ArrayList<Role> roles = new ArrayList<>() {{
            add(roleRepository.findByName("ADMIN"));
        }};
        user.setRoles(roles);

        User _user = userService.saveUser(user);

        Admin _admin = adminRepository.save(
                Admin.builder()
                        .user(_user)
                        .build()
        );
        log.info("New Admin added with {adminId}=" + _admin.getId());
        return _admin;
    }

    /**
     * Authenticates a Admin
     *
     * @param user The admin info
     * @return The Admin instance
     */
    @Override
    public Admin auth(User user) {
        User _user = userService.authenticate(user);

        return adminRepository.findByUser(_user).orElseThrow(
                () -> new RuntimeException("Invalid type of user. Try to log in as another type.")
        );
    }

    /**
     * Changes a odd's status from null/false/true into false/true
     *
     * @param oddId  The odd's id to be changed
     * @param status The actual new status
     * @return The successful/not String
     */
    @Override
    public String changeOddStatus(Long oddId, Boolean status) {
        Odd _odd = oddRepository.findById(oddId).orElseThrow(
                () -> {
                    log.error("No Odd found with {oddId}=" + oddId);
                    throw new RuntimeException("No Odd found with {oddId}=" + oddId);
                }
        );

        String res = "Odd with {oddId}=" + oddId + " changed status from " + _odd.getWinner() + " into " + status;
        _odd.setWinner(status);
        log.info(res);
        oddRepository.save(_odd);
        return res;
    }
}
