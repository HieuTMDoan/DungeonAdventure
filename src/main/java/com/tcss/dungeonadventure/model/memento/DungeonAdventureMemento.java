package com.tcss.dungeonadventure.model.memento;

import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import javafx.util.Pair;

import java.awt.Point;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Memento for capturing and restoring the state of the Dungeon Adventure game.
 * This Memento includes information about the player name, hero, dungeon, and room mementos.
 * @author Sunny, Aaron, Hieu
 * @version Fall 2023
 */
public class DungeonAdventureMemento implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Player myPlayer;

    /**
     * The saved dungeon instance.
     */
    private final Dungeon mySavedDungeon;

    private final Room[][] myDiscoveredRooms;



    /**
     * Constructs a DungeonAdventureMemento with the specified player name, hero, and dungeon.
     *
     * @param theDungeon    The dungeon instance.
     */
    public DungeonAdventureMemento(final Player thePlayer,
                                   final Dungeon theDungeon,
                                   final Room[][] theDiscoveredRooms) {
        this.myPlayer = thePlayer;
        this.mySavedDungeon = theDungeon;
        this.myDiscoveredRooms = theDiscoveredRooms;


    }

    public Player getSavedPlayer() {
        return this.myPlayer;
    }

    public Room[][] getSavedDiscoveredRooms() {
        return this.myDiscoveredRooms;
    }


    /**
     * Gets the saved dungeon instance.
     *
     * @return The saved dungeon instance.
     */
    public Dungeon getSavedDungeon() {
        return mySavedDungeon;
    }

}