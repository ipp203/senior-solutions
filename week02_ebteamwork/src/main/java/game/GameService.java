package game;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GameService {
    GameRepository repo;

    public GameService(GameRepository repo) {
        this.repo = repo;
    }

    public Optional<Game> getGameBiggestDifference() {
        return repo.getGames().stream().sorted((g1, g2) ->
                Math.abs(g2.getFirstCountryScore()-g2.getSecondCountryScore())
                        - Math.abs(g1.getFirstCountryScore()-g1.getSecondCountryScore())
        ).findFirst();
    }

    public int getNumberOfGoalsByCountry(String country){
        return repo.getGames().stream()
                .mapToInt(g->g.getNumberOfGoalsByCountry(country))
                .sum();
    }

    public Optional<String> getCountryWithMostGoals(){
        return repo.getGames().stream()
                .map(g-> List.of(g.getFirstCountry(),g.getSecondCountry()))
                .flatMap(Collection::stream)
                .distinct()
                .max(Comparator.comparingInt(this::getNumberOfGoalsByCountry));

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
//​