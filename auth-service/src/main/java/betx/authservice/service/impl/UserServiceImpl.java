package betx.authservice.service.impl;

import betx.authservice.model.User;
import betx.authservice.repository.UserRepository;
import betx.authservice.service.Validator;
import betx.authservice.service.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    Validator validator = Validator.getInstance();

    @Override
    public User saveUser(User user) {
        if (user.getEmail().isEmpty()) {
            log.error("User's {email} cannot be empty");
            throw new RuntimeException("User's {email} cannot be empty");
        }
        if (!validator.isEmailValid(user.getEmail())) {
            log.error("User's {email} doesn't meet the requirements (must contain a '@'");
            throw new RuntimeException("User's {email} doesn't meet the requirements (must contain a '@'");
        }
        if (!validator.isPasswordValid(user.getPassword())) {
            log.error("User's {password} doesn't meet the requirements");
            throw new RuntimeException("User's {password} doesn't meet the requirements");
        }


        try {
            User _user = userRepository.save(
                    User
                            .builder()
                            .email(user.getEmail())
                            .password(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                            .roles(user.getRoles())
                            .build());
            log.info("New user added with {email}=" + _user.getEmail());
            return _user;
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            String error = "Duplicate entry exception for {User.email}=" + user.getEmail();
            log.error(error);
            throw new RuntimeException(error);
        }
    }

    @Override
    public User authenticate(User user) {
        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            log.error("User info is invalid = " + user);
            throw new RuntimeException("User info is invalid = " + user);
        }

        User _user = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new RuntimeException("No user found for {email}=" + user.getEmail())
        );

        if (BCrypt.checkpw(user.getPassword(), _user.getPassword())) {
            return _user;
        } else throw new RuntimeException("Invalid credentials!");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User _user = userRepository.findByEmail(username).orElseThrow(
                () -> {
                    log.error("No user found for {email}=" + username);
                    throw new RuntimeException("No user found for {email}=" + username);
                }
        );

        Collection<SimpleGrantedAuthority> authorities = _user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(_user.getEmail(), _user.getPassword(), authorities);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
