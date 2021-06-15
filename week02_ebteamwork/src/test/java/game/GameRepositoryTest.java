package game;

import game.Game;
import game.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GameRepositoryTest {
    GameRepository repo;
    List<Game> games;

    @BeforeEach
    void init() {
        repo = new GameRepository();
        games = Arrays.asList(
                new Game("Turkey", "Italy", 0, 3),
                new Game("Wales", "Switzerland", 1, 1),
                new Game("Denmark", "Finnland", 0, 1));

    }

    @RepeatedTest(3)
    void testAdd(RepetitionInfo ri) {
        int i = ri.getCurrentRepetition() - 1;
        repo.addGame(games.get(i));
        assertEquals(1, repo.getGames().size());
    }

    @Test
    void testAddWithNull() {
        repo.addGame(null);
        assertEquals(0, repo.getGames().size());
    }

    @Test
    void testAddGamesFromFile() {
        Path path = Path.of("results.csv");
        repo.addGamesFromFile(path);
        assertThat(repo.getGames())
                .hasSize(15)
                .extracting("firstCountry")
                .contains("Italy");
    }

    @Test
    void testAddGamesFromFileWithWrongPath() {
        Path path = Path.of("/results.csv");
        assertThrows(IllegalArgumentException.class,()->repo.addGamesFromFile(path));
    }
}