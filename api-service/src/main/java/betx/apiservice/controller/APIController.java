package betx.apiservice.controller;

import betx.apiservice.dto.FixtureDTO;
import betx.apiservice.dto.TeamDTO;
import betx.apiservice.service.impl.FixtureServiceImpl;
import betx.apiservice.service.impl.LeagueServiceImpl;
import betx.apiservice.service.impl.TeamServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@Slf4j
public class APIController {

    @Autowired
    LeagueServiceImpl leagueService;

    @Autowired
    TeamServiceImpl teamService;

    @Autowired
    FixtureServiceImpl fixtureService;

    @PostMapping("/searchTeam")
    public ResponseEntity<TeamDTO> searchTeam(@RequestParam(name = "name") String name, @RequestParam(name = "country") String country) {
        return new ResponseEntity<>(teamService.searchTeam(name, country), HttpStatus.OK);
    }

    @GetMapping("findAll")
    public ResponseEntity<List<FixtureDTO>> findAll() {
        return new ResponseEntity<>(fixtureService.findAll(), HttpStatus.OK);
    }
}
