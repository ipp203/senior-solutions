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
//???
//## Feladat
//A mai feladatban az EB meccsek eredm??nyeit kell egy alkalmaz??sban
//t??rolnod, ??s k??l??nb??z?? feladatokat elv??gezned.
//???
//### game.Game
//Legyen egy `game.Game` nev?? oszt??lyod a k??vetkez?? attrib??tumokkal
//+ `firstCountry (String)`
//+ `secondCountry (String)`
//+ `firstCountryScore (int)`
//+ `secondCountryScore (int)`
//???
//Legyen benne egy met??dus ami visszaadja a gy??ztes orsz??g nev??t!
//???
//???
//### game.GameRepository
//Legyen egy `game.GameRepository` nev?? oszt??lyod, melynek van egy meccseket
//mem??ri??ban t??rol?? list??ja van.
//???
//A list??hoz elemet k??t f??le k??ppen lehet hozz??adni. Vagy egy `addGame(game.Game game)` met??dussal,
//vagy f??jlb??l beolvasva, ahol a f??jl egy csv ??llom??ny.
//???
//### game.GameService
//Legyen egy `game.GameService` nev?? oszt??lyod, ami k??l??nb??z?? statisztikai adatokat jelen??t meg.
//Legyen egy `game.GameRepository` attrib??tuma amin kereszt??l el??ri a benne l??v?? list??t.
//???
//Megval??s??tand?? met??dusok:
//???
//+ Hat??rozd meg a legnagyobb g??lk??l??nbs??ggel v??get ??rt m??rk??z??st
//+ Hat??rozd meg hogy egy param??ter??l kapott orsz??g h??ny g??lt r??gott eddig
//+ Hat??rozd meg az eddig legt??bb g??lt r??g?? orsz??got
//???
//???
//### Tesztel??s
//Mindegyik oszt??lyhoz legyen k??l??n teszt oszt??ly. A nem gener??lt met??dusokhoz legyen teszt eset, lehet??leg t??bb.
//A `game.GameService` oszt??ly m??sodik met??dus??t param??terezett teszttel v??gezd. Ez lehet ak??r dinamikus teszteset is.
//???egyen egy `game.GameRepository` attrib??tuma amin kereszt??l el??ri a benne l??v?? list??t.