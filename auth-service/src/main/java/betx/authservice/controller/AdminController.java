package betx.authservice.controller;

import betx.apiservice.model.Odd;
import betx.apiservice.service.impl.OddServiceImpl;
import betx.authservice.model.users.Admin;
import betx.authservice.model.users.User;
import betx.authservice.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    OddServiceImpl oddService;

    /**
     * Endpoint that saves an Admin
     *
     * @param user User information
     * @return the new Admin instance
     */
    @PostMapping("/save")
    public ResponseEntity<Admin> save(@RequestBody User user) {
        return new ResponseEntity<>(adminService.save(user), HttpStatus.OK);
    }

    /**
     * Endpoint that authenticates an Admin
     *
     * @param user User information
     * @return the Admin instance
     */
    @PostMapping("/auth")
    public ResponseEntity<Admin> auth(@RequestBody User user) {
        return new ResponseEntity<>(adminService.auth(user), HttpStatus.OK);
    }

    /**
     * Endpoint that changes the status of an odd
     *
     * @param oddId  Odd's id
     * @param status New status
     * @return Successful/not String
     */
    @PostMapping("changeOddStatus")
    public ResponseEntity<String> changeOddStatus(@RequestParam(name = "oddId") long oddId, @RequestParam(name = "status") boolean status) {
        return new ResponseEntity<>(adminService.changeOddStatus(oddId, status), HttpStatus.OK);
    }

    /**
     * Endpoint that retrieves all Odds from DB
     *
     * @return List of Odds
     */
    @GetMapping("findAllOdds")
    public ResponseEntity<List<Odd>> findAllOdds() {
        return new ResponseEntity<>(oddService.findAll(), HttpStatus.OK);
    }
}
