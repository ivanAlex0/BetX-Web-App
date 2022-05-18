package betx.apiservice.service.apiServices;

import betx.apiservice.APIModel.APILeagueObject;
import betx.apiservice.APIModel.APIResponse;
import betx.apiservice.APIModel.APITeamObject;
import betx.apiservice.APIModel.fixture.FixtureResponse;
import betx.apiservice.APIModel.standing.StandingResponse;
import betx.apiservice.model.Fixture;
import betx.apiservice.model.League;
import betx.apiservice.model.Position;
import betx.apiservice.model.Team;
import betx.apiservice.model.api.Country;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class APIHandler {

    private final String BASE_PATH = "https://v3.football.api-sports.io";
    private final String API_KEY2 = "47dd9da86c04bb020f4e26e77bd6dee5";
    private final String API_KEY = "b99f782a76643d18f93ad34f7e20d8bd";
    private final Integer season = 2021;
    private final ArrayList<String> acceptedCountries = new ArrayList<>() {{
        add("GB");
        add("DE");
        add("ES");
        add("IT");
    }};
    private final ArrayList<String> acceptedLeagues = new ArrayList<>() {{
        add("Bundesliga 1");
        add("Premier League");
        add("Serie A");
        add("La Liga");
    }};

    public ArrayList<League> getLeagues() {
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = Unirest.get(BASE_PATH + "/leagues")
                    .header("x-rapidapi-key", API_KEY)
                    .queryString("season", season)
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        assert httpResponse != null;
        APIResponse<APILeagueObject> response = new Gson().fromJson(httpResponse.getBody(), new TypeToken<APIResponse<APILeagueObject>>() {
        }.getType());

        return (ArrayList<League>) response.getResponse().stream()
                .filter(apiLeagueObject -> acceptedCountries.contains(apiLeagueObject.getCountry().getCode())
                        && acceptedLeagues.contains(apiLeagueObject.getLeague().getName()))
                .peek(apiLeagueObject -> apiLeagueObject.getLeague().setCountry(apiLeagueObject.getCountry()))
                .map(APILeagueObject::getLeague)
                .map(this::getTeamsByLeague)
                .collect(Collectors.toList());
    }

    public League getTeamsByLeague(League league) {
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = Unirest.get(BASE_PATH + "/teams")
                    .header("x-rapidapi-key", API_KEY)
                    .queryString("league", league.getId())
                    .queryString("season", season)
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        assert httpResponse != null;
        APIResponse<APITeamObject> response = new Gson().fromJson(httpResponse.getBody(), new TypeToken<APIResponse<APITeamObject>>() {
        }.getType());
        ArrayList<Team> teams = new ArrayList<>();
        for (APITeamObject apiTeamObject : response.getResponse()) {
            teams.add(apiTeamObject.getTeam());
        }
        league.setTeams(teams);
        return league;
    }

    public ArrayList<Country> getCountries() {
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = Unirest.get(BASE_PATH + "/countries")
                    .header("x-rapidapi-key", API_KEY)
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        assert httpResponse != null;
        APIResponse<Country> response = new Gson().fromJson(httpResponse.getBody(), new TypeToken<APIResponse<Country>>() {
        }.getType());

        return new ArrayList<>(response.getResponse());
    }

    public ArrayList<Position> getStandingByLeague(League league) throws JsonSyntaxException {
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = Unirest.get(BASE_PATH + "/standings")
                    .header("x-rapidapi-key", API_KEY)
                    .queryString("league", league.getId())
                    .queryString("season", season)
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        assert httpResponse != null;
        log.info(httpResponse.getBody());
        APIResponse<StandingResponse> response = new Gson().fromJson(httpResponse.getBody(), new TypeToken<APIResponse<StandingResponse>>() {
        }.getType());

        return response.getResponse().get(0).getLeague().getStandings().get(0);
    }

    public ArrayList<Fixture> getFixturesForLeague(League league) {
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = Unirest.get(BASE_PATH + "/fixtures")
                    .header("x-rapidapi-key", API_KEY)
                    .queryString("league", league.getId())
                    .queryString("season", season)
                    .queryString("status", "NS")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        assert httpResponse != null;
        log.info(httpResponse.getBody());
        APIResponse<FixtureResponse> response = new Gson().fromJson(httpResponse.getBody(), new TypeToken<APIResponse<FixtureResponse>>() {
        }.getType());

        for (FixtureResponse fixtureResponse : response.getResponse()) {
            log.info(fixtureResponse.getFixture().getId().toString() + " " + fixtureResponse.getTeams().getHome().getName() + " " + fixtureResponse.getTeams().getAway().getName());
        }

        return (ArrayList<Fixture>) response.getResponse().stream()
                .map(fixtureResponse ->
                        Fixture.builder()
                                .id(fixtureResponse.getFixture().getId())
                                .referee(fixtureResponse.getFixture().getReferee())
                                .timestamp(fixtureResponse.getFixture().getTimestamp())
                                .home(fixtureResponse.getTeams().getHome())
                                .away(fixtureResponse.getTeams().getAway())
                                .build())
                .collect(Collectors.toList());
    }
}
