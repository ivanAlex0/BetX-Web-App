package betx.apiservice.controller;

import betx.apiservice.model.League;
import betx.apiservice.service.impl.LeagueServiceImpl;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class ControllerTest {

    @Autowired
    LeagueServiceImpl leagueService;

    @GetMapping("test")
    public ResponseEntity<String> test() throws UnirestException {
        /*HttpResponse<String> httpResponse = Unirest.get("https://v3.football.api-sports.io/teams")
                .header("x-rapidapi-key", "47dd9da86c04bb020f4e26e77bd6dee5")
                .queryString("league", 39)
                .queryString("season", 2021)
                .asString();
        System.out.println(httpResponse.getBody());
        APIResponse response = new Gson().fromJson(httpResponse.getBody(), APIResponse.class);
        for (APITeamObject teamObject : response.getResponse()) {
            log.info(teamObject.getTeam().toString());
            log.info(teamObject.getVenue().toString());
        }*/
        return new ResponseEntity<>("ULALA", HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
    }

    /*@GetMapping("/getLeague")
    public ResponseEntity<League> getLeague(@RequestBody League league) {
        return new ResponseEntity<>(leagueService.getLeague(league), HttpStatus.OK);
    }*/
}
