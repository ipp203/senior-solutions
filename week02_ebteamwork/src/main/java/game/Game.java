package game;

import java.util.Objects;
import java.util.Optional;

public class Game {

    private String firstCountry;
    private String secondCountry;
    private int firstCountryScore;
    private int secondCountryScore;

    public Game(String firstCountry, String secondCountry, int firstCountryScore, int secondCountryScore) {
        this.firstCountry = firstCountry;
        this.secondCountry = secondCountry;
        this.firstCountryScore = firstCountryScore;
        this.secondCountryScore = secondCountryScore;
    }

    //proba modositas
    public String getFirstCountry() {
        return firstCountry;
    }

    public String getSecondCountry() {
        return secondCountry;
    }

    public int getFirstCountryScore() {
        return firstCountryScore;
    }

    public int getSecondCountryScore() {
        return secondCountryScore;
    }

    public Optional<String> getWinner() {
        String winner = null;
        if (firstCountryScore > secondCountryScore) {
            winner = firstCountry;
        } else if (firstCountryScore < secondCountryScore) {
            winner = secondCountry;
        }

        return Optional.ofNullable(winner);
    }

    public static Game parse(String str) {
        String[] data = str.split(";");
        return new Game(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
    }

    public int getNumberOfGoalsByCountry(String country) {
        if (firstCountry.equals(country)) {
            return firstCountryScore;
        }
        if (secondCountry.equals(country)) {
            return secondCountryScore;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return getFirstCountryScore() == game.getFirstCountryScore()
                && getSecondCountryScore() == game.getSecondCountryScore()
                && getFirstCountry().equals(game.getFirstCountry())
                && getSecondCountry().equals(game.getSecondCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstCountry(), getSecondCountry(), getFirstCountryScore(), getSecondCountryScore());
    }
}
//# Csapatmunka feladat
//​
//## Feladat
//A mai feladatban az EB meccsek eredményeit kell egy alkalmazásban
//tárolnod, és különböző feladatokat elvégezned.
//​
//### game.Game
//Legyen egy `game.Game` nevű osztályod a következő attribútumokkal
//+ `firstCountry (String)`
//+ `secondCountry (String)`
//+ `firstCountryScore (int)`
//+ `secondCountryScore (int)`
//​
//Legyen benne egy metódus ami visszaadja a győztes ország nevét!
//​
//​
//### game.GameRepository
//Legyen egy `game.GameRepository` nevű osztályod, melynek van egy meccseket
//memóriában tároló listája van.
//​
//A listához elemet két féle képpen lehet hozzáadni. Vagy egy `addGame(game.Game game)` metódussal,
//vagy fájlból beolvasva, ahol a fájl egy csv állomány.
//​
//### game.GameService
//Legyen egy `game.GameService` nevű osztályod, ami különböző statisztikai adatokat jelenít meg.
//Legyen egy `game.GameRepository` attribútuma amin keresztül eléri a benne lévő listát.
//​
//Megvalósítandó metódusok:
//​
//+ Határozd meg a legnagyobb gólkülönbséggel véget ért mérkőzést
//+ Határozd meg hogy egy paraméterül kapott ország hány gólt rúgott eddig
//+ Határozd meg az eddig legtöbb gólt rúgó országot
//​
//​
//### Tesztelés
//Mindegyik osztályhoz legyen külön teszt osztály. A nem generált metódusokhoz legyen teszt eset, lehetőleg több.
//A `game.GameService` osztály második metódusát paraméterezett teszttel végezd. Ez lehet akár dinamikus teszteset is.
//​egyen egy `game.GameRepository` attribútuma amin keresztül eléri a benne lévő listát.