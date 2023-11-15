package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.objects.heroes.Hero;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Memento for capturing and restoring the state of the Dungeon Adventure game.
 * This Memento includes information about the player name, hero, dungeon, and room mementos.
 */
public class DungeonAdventureMemento {
    /**
     * The saved player name.
     */
    private final String mySavedPlayerName;

    /**
     * The saved hero instance.
     */
    private final Hero mySavedHero;

    /**
     * The saved dungeon instance.
     */
    private final Dungeon mySavedDungeon;

    /**
     * The list of room mementos to store room states.
     */
    private final List<RoomMemento> myRoomMementos;

    /**
     * Constructs a DungeonAdventureMemento with the specified player name, hero, and dungeon.
     *
     * @param thePlayerName The name of the player.
     * @param theHero       The hero instance.
     * @param theDungeon    The dungeon instance.
     */
    public DungeonAdventureMemento(final String thePlayerName,
                                   final Hero theHero, final Dungeon theDungeon) {
        this.mySavedPlayerName = thePlayerName;
        this.mySavedHero = theHero;
        this.mySavedDungeon = theDungeon;
        this.myRoomMementos = new ArrayList<>();
        // Initialize other fields as needed
    }

    /**
     * Adds a room memento to the list of room mementos.
     *
     * @param theRoomMemento The room memento to add.
     */
    public void addRoomMemento(final RoomMemento theRoomMemento) {
        this.myRoomMementos.add(theRoomMemento);
    }

    /**
     * Gets a copy of the list of room mementos.
     *
     * @return A copy of the list of room mementos.
     */
    public List<RoomMemento> getRoomMementos() {
        // Return a copy of the list to prevent external modifications
        return new ArrayList<>(myRoomMementos);
    }

    /**
     * Gets the saved player name.
     *
     * @return The saved player name.
     */
    public String getSavedPlayerName() {
        return mySavedPlayerName;
    }

    /**
     * Gets the saved hero instance.
     *
     * @return The saved hero instance.
     */
    public Hero getSavedHero() {
        return mySavedHero;
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