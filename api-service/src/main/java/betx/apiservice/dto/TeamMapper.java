package betx.apiservice.dto;

import betx.apiservice.model.Fixture;
import betx.apiservice.model.Team;

import java.util.ArrayList;

public class TeamMapper {

    public TeamDTO teamToTeamDTO(Team team) {
        ArrayList<FixtureDTO> homeFixtures = new ArrayList<>();
        ArrayList<FixtureDTO> awayFixtures = new ArrayList<>();

        if (team.getAwayFixtures() != null) {
            for (Fixture awayFixture : team.getAwayFixtures()) {

                awayFixtures.add(
                        FixtureDTO.builder()
                                .id(awayFixture.getId())
                                .referee(awayFixture.getReferee())
                                .timestamp(awayFixture.getTimestamp())
                                .home(awayFixture.getHome())
                                .away(awayFixture.getAway())
                                .odds(awayFixture.getOdds())
                                .build()
                );
            }
        }

        if (team.getHomeFixtures() != null) {
            for (Fixture homeFixture : team.getHomeFixtures()) {

                homeFixtures.add(
                        FixtureDTO.builder()
                                .id(homeFixture.getId())
                                .referee(homeFixture.getReferee())
                                .timestamp(homeFixture.getTimestamp())
                                .home(homeFixture.getHome())
                                .away(homeFixture.getAway())
                                .odds(homeFixture.getOdds())
                                .build()
                );
            }
        }

        for (FixtureDTO homeFixture : homeFixtures) {
            homeFixture.getHome().setHomeFixtures(null);
            homeFixture.getHome().setAwayFixtures(null);
            homeFixture.getAway().setHomeFixtures(null);
            homeFixture.getAway().setAwayFixtures(null);
        }

        for (FixtureDTO awayFixture : awayFixtures) {
            awayFixture.getHome().setHomeFixtures(null);
            awayFixture.getHome().setAwayFixtures(null);
            awayFixture.getAway().setHomeFixtures(null);
            awayFixture.getAway().setAwayFixtures(null);
        }


        return TeamDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .code(team.getCode())
                .country(team.getCountry())
                .homeFixtures(homeFixtures)
                .awayFixtures(awayFixtures)
                .build();
    }

    public FixtureDTO fixtureToFixtureDTO(Fixture fixture) {
        FixtureDTO _fixture = FixtureDTO.builder()
                .id(fixture.getId())
                .referee(fixture.getReferee())
                .timestamp(fixture.getTimestamp())
                .home(fixture.getHome())
                .away(fixture.getAway())
                .odds(fixture.getOdds())
                .build();

        _fixture.getHome().setHomeFixtures(null);
        _fixture.getHome().setAwayFixtures(null);
        _fixture.getAway().setHomeFixtures(null);
        _fixture.getAway().setAwayFixtures(null);

        return _fixture;
    }
}
