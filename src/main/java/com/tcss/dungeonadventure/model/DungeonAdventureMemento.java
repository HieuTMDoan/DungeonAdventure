package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.objects.heroes.Hero;

import java.io.IOException;
import java.io.ObjectInputStream;
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
    private transient List<RoomMemento> myRoomMementos;

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
        if (myRoomMementos == null) {
            // Handle the case where myRoomMementos is null, e.g., return an empty list
            return new ArrayList<>();
        }
        // Return a copy of the list to prevent external modifications
        return new ArrayList<>(myRoomMementos);
    }
    /**
     * Custom deserialization method to initialize transient fields.
     *
     * @param ois The ObjectInputStream.
     * @throws IOException            If an I/O error occurs.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();

        // Initialize transient fields here
        this.myRoomMementos = new ArrayList<>();
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