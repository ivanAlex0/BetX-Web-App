package betx.authservice.controller;

import betx.authservice.model.AddressCountry;
import betx.authservice.service.impl.AddressCountryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class AuthUtilsController {

    @Autowired
    AddressCountryServiceImpl addressCountryService;

    @GetMapping("/addressCountries")
    public ResponseEntity<List<AddressCountry>> fetchCountries() {
        return new ResponseEntity<>(addressCountryService.findAll(), HttpStatus.OK);
    }
}
