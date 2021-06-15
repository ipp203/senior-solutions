package game;

import game.Game;
import game.GameRepository;
import game.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GameServiceTest {
    private GameService service;
    private GameService serviceWithEmptyRepo;
    private List<Game> games;

    @BeforeEach
    void init() {
        GameRepository emptyRepo = new GameRepository();
        serviceWithEmptyRepo = new GameService(emptyRepo);
        GameRepository repo = new GameRepository();
        games = List.of(
                new Game("Turkey", "Italy", 0, 3),
                new Game("Italy", "Switzerland", 1, 1),
                new Game("Denmark", "Italy", 0, 1));
        games.forEach(repo::addGame);

        service = new GameService(repo);
    }

    @Test
    void testGetGameBiggestDifference() {
        Optional<Game> result = service.getGameBiggestDifference();
        if(result.isPresent()) {
            assertEquals(games.get(0), result.get());
        }else{
            fail();
        }
    }
    @Test
    void testGetGameBiggestDifferenceWithNull() {
        Optional<Game> result = serviceWithEmptyRepo.getGameBiggestDifference();
        assertTrue(result.isEmpty());
    }

    private static Stream<Arguments> createGames() {
        return Stream.of(
                arguments("Turkey", 0),
                arguments("Italy", 5),
                arguments("Hungary", 0));
    }

    @ParameterizedTest
    @MethodSource("createGames")
    void testGetNumberOfGoalsByCountry(String country,int goals) {
        assertEquals(goals,service.getNumberOfGoalsByCountry(country));
    }

    @ParameterizedTest
    @MethodSource("createGames")
    void testGetNumberOfGoalsByCountryWithEmptyRepo(String country,int goals) {
        assertEquals(0,serviceWithEmptyRepo.getNumberOfGoalsByCountry(country));
    }

    @Test
    void testGetCountryWithMostGoals() {
        assertEquals("Italy",service.getCountryWithMostGoals().get());
    }

    @Test
    void testGetCountryWithMostGoalsWithEmptyRepo() {
        assertEquals(Optional.empty(),serviceWithEmptyRepo.getCountryWithMostGoals());
    }
}