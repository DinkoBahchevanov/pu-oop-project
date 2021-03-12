package com.company.player;

import com.company.heroes.Dwarf;
import com.company.heroes.Elf;
import com.company.heroes.Hero;
import com.company.heroes.Knight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.company.globalConstants.Constants.*;

public class Player {
    private HashMap<String, Integer> heroesForPlacing;
    private List<Hero> army;
    private int points;

    public Player() {
        heroesForPlacing = new HashMap<>();
        army = new ArrayList<>();

        points = 0;

        setHeroes();
    }

    private void setHeroes() {
        heroesForPlacing.put(KNIGHT, 2);
        heroesForPlacing.put(DWARF, 2);
        heroesForPlacing.put(ELF, 2);
    }

    public HashMap<String, Integer> getHeroesForPlacing() {
        return heroesForPlacing;
    }

    public List<Hero> getArmy() {
        return army;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
