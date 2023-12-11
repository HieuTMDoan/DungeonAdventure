package com.tcss.dungeonadventure.model;

import java.io.*;

/**
 * Manages the game state using the Memento pattern.
 * Singleton class responsible for creating and restoring game state Mementos.
 * @author Sunny, Aaron, Hieu
 * @version Fall 2023
 */
public final class GameStateManager implements Serializable {
    private static final GameStateManager INSTANCE = new GameStateManager();
    private DungeonAdventureMemento myMemento;

    private GameStateManager() {
    }

    public static GameStateManager getInstance() {
        return INSTANCE;
    }

    public void createMemento() throws IOException {
        DungeonAdventure dungeonAdventure = DungeonAdventure.getInstance();
        if (dungeonAdventure == null) {
            // Initialize DungeonAdventure if not already initialized
            dungeonAdventure = DungeonAdventure.getInstance();
            dungeonAdventure.initialize();
        }

        myMemento = dungeonAdventure.createMemento();
        saveToFile("saved_game.ser");
    }

    public void setMemento(final DungeonAdventureMemento theMemento) {
        this.myMemento = theMemento;
    }

    private void saveToFile(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(myMemento);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception or throw a custom exception
        }
    }

    public DungeonAdventureMemento loadFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            DungeonAdventureMemento loadedMemento = (DungeonAdventureMemento) ois.readObject();
            System.out.println("Game loaded successfully!");
            return loadedMemento;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Log the exception or throw a custom exception
            return null;
        }
    }

    public void loadGame(String filePath) {
        DungeonAdventureMemento loadedMemento = loadFromFile(filePath);
        if (loadedMemento != null) {
            setMemento(loadedMemento);
        }
    }
}