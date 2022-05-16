package betx.authservice.service.impl;

import betx.authservice.model.Role;
import betx.authservice.repository.RoleRepository;
import betx.authservice.service.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    @PostConstruct
    public void initRoles() {
        ArrayList<String> roles = new ArrayList<>() {{
            add("CUSTOMER");
            add("ADMIN");
        }};
        List<Role> rolesFromDB = roleRepository.findAll();

        if (rolesFromDB.size() != roles.size()) {
            for (String role : roles) {
                if (!rolesFromDB.contains(Role.builder().name(role).build())) {
                    Role _role = roleRepository.save(
                            Role
                                    .builder()
                                    .name(role)
                                    .build()
                    );
                    log.info("New role saved with {name}=" + _role.getName());
                }
            }
        }
    }
}
