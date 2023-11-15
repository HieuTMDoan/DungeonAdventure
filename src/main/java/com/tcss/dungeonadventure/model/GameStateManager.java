package com.tcss.dungeonadventure.model;



/**
 * Manages the game state using the Memento pattern.
 * Singleton class responsible for creating and restoring game state Mementos.
 * @author Sunny, Aaron, Hieu
 * @version Fall 2023
 */
public final class GameStateManager {
    /** The sole instance of the GameStateManager. */
    private static GameStateManager INSTANCE;

    /** The current game state Memento. */
    private DungeonAdventureMemento myMemento;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private GameStateManager() {
    }

    /**
     * Retrieves the singleton instance of the GameStateManager.
     *
     * @return The GameStateManager instance.
     */
    public static GameStateManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameStateManager();
        }
        return INSTANCE;
    }

    /**
     * Creates a Memento to capture the current game state.
     */
    public void createMemento() {
        myMemento = DungeonAdventure.getInstance().createMemento();
    }

    /**
     * Restores the game state from a Memento.
     */
    public void restoreFromMemento() {
        DungeonAdventure.getInstance().restoreFromMemento(myMemento);
    }

    /**
     * Retrieves the current game state Memento.
     *
     * @return The current game state Memento.
     */
    public DungeonAdventureMemento getMemento() {
        return myMemento;
    }

    /**
     * Sets the current game state Memento.
     *
     * @param theMemento The Memento to set.
     */
    public void setMemento(final DungeonAdventureMemento theMemento) {
        this.myMemento = theMemento;
    }
}
