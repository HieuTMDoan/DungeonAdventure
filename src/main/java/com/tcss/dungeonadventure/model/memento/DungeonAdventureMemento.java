package com.tcss.dungeonadventure.model.memento;

import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.model.Room;
import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a Memento for capturing and restoring the state of the Dungeon Adventure game.
 * This Memento includes information about the player name, hero, dungeon, and room mementos.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class DungeonAdventureMemento implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * The saved player instance.
     */
    private final Player myPlayer;

    /**
     * The saved dungeon instance.
     */
    private final Dungeon mySavedDungeon;

    /**
     * The discovered rooms in the dungeon.
     */
    private final Room[][] myDiscoveredRooms;


    /**
     * Constructs a DungeonAdventureMemento with the specified player name, hero, and dungeon.
     *
     * @param theDungeon The dungeon instance.
     */
    public DungeonAdventureMemento(final Player thePlayer,
                                   final Dungeon theDungeon,
                                   final Room[][] theDiscoveredRooms) {
        this.myPlayer = thePlayer;
        this.mySavedDungeon = theDungeon;
        this.myDiscoveredRooms = theDiscoveredRooms;

    }

    /**
     * Gets the discovered rooms in the dungeon.
     *
     * @return The discovered rooms in the dungeon.
     */
    public Room[][] getSavedDiscoveredRooms() {
        return this.myDiscoveredRooms;
    }

    /**
     * Gets the saved player instance.
     *
     * @return The saved player instance.
     */
    public Player getSavedPlayer() {
        return this.myPlayer;
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