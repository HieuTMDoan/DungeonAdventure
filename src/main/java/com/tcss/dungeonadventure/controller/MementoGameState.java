package com.tcss.dungeonadventure.controller;

import com.tcss.dungeonadventure.objects.DungeonCharacter;

import java.util.ArrayList;
import java.util.List;

public class MementoGameState {
    private final DungeonCharacter hero;
    private final List<DungeonCharacter> monsters;

    public MementoGameState(DungeonCharacter hero, List<DungeonCharacter> monsters) {
        this.hero = hero;
        this.monsters = new ArrayList<>(monsters); // Create a copy of the list
    }

    public DungeonCharacter getHero() {
        return hero;
    }

    public List<DungeonCharacter> getMonsters() {
        return new ArrayList<>(monsters); // Return a copy of the list
    }
}
