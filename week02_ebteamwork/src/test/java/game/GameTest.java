package game;

import game.Game;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void getFirstCountry() {
    }

    @Test
    void getSecondCountry() {
    }

    @Test
    void getFirstCountryScore() {
    }

    @Test
    void getSecondCountryScore() {
    }

    @Test
    void getWinner() {
        Game g1 = new Game("Mexico", "Brazil", 2,3);
        Game g2 = new Game("Holland", "France",1,3);
        Game g3 = new  Game("Brazil", "France",4,4);

        Optional result = g1.getWinner();
        if (result.isPresent()) {
            assertEquals(result.get(),"Brazil");
        } else {
            fail();
        }

        result = g2.getWinner();

        if (result.isPresent()) {
            assertEquals(result.get(),"France");
        } else {
            fail();
        }

        result = g3.getWinner();

        assertFalse(result.isPresent());
    }

    @Test
    void parse() {
        assertEquals(Game.parse("Brazil;France;2;3"), new Game("Brazil","France",2,3));
    }
}