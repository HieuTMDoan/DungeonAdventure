package com.tcss.dungeonadventure.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Represents a randomly generated maze of type {@link Room}.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360, Fall 2023
 */
public class Dungeon {
    /**
     * The 2D representation of the {@link Dungeon}.
     */
    private final Room[][] myMaze;

    /**
     * The entrance of the {@link Dungeon}.
     */
    private final Room myStartingRoom;

    /**
     * The exit of the {@link Dungeon}.
     */
    private final Room myExitRoom;

    /**
     * The room that contains a pillar of Object-Oriented.
     */
    private final Room[] myPillarRooms;

    /**
     * The current player's location.
     */
    private final Room myCharacterLocation;

    /**
     * Initializes a 6x6 traversable {@link Dungeon}.
     *
     * @param theMaze         the empty maze of type {@link Room}
     * @param theStartingRoom the entrance of the {@link Dungeon}
     * @param theExitRoom     the exit of the {@link Dungeon}
     * @param thePillarRooms  the room that contains a pillar of Object-Oriented
     */
    public Dungeon(final Room[][] theMaze,
                   final Room theStartingRoom,
                   final Room theExitRoom,
                   final Room[] thePillarRooms) {
        myMaze = theMaze;
        myStartingRoom = theStartingRoom;
        myExitRoom = theExitRoom;
        myPillarRooms = thePillarRooms;
        myCharacterLocation = theStartingRoom;
        generateDungeon();
    }

    /**
     * Constructs a random-generated maze of type {@link Room}
     * with a traversable path from the entrance to the exit
     * and 4 pillars of Object-Oriented randomly placed in the maze.
     */
    private void generateDungeon() {
        // Generates an array of essentials rooms in the maze
        final List<Room> essentialRooms = new ArrayList<>();
        essentialRooms.addAll(Arrays.asList(myStartingRoom, myExitRoom));
        essentialRooms.addAll(Arrays.asList(myPillarRooms));

        /*
         A random generator for three uses:
            -shuffling rooms in essentialRooms
            -choosing random index in the maze
            -randomly either choosing an essential room or a dead-end room
         */
        final Random random = new Random();

        // Shuffles the list of rooms randomly
        Collections.shuffle(essentialRooms, random);

        /*
        totalSpots is the total # of indices in the maze,
        filledCount is the # of occupied indices
        essentialRoomsIndex is the index of the shuffled essentialRooms
         */
        final int totalSpots = myMaze.length * myMaze[0].length;
        int filledCount = 0;
        int essentialRoomsIndex = 0;

        // Fills the 2D array until no empty spots are left
        while (filledCount < totalSpots && essentialRoomsIndex < essentialRooms.size()) {
            final int randomRow = random.nextInt(myMaze.length);
            final int randomCol = random.nextInt(myMaze[0].length);

            // A list in which its chosen element
            // can be either an essential room or a dead-end room
            final List<Room> randomRooms = new ArrayList<>();
            randomRooms.add(essentialRooms.get(essentialRoomsIndex));
            randomRooms.add(new Room(randomRow, randomCol, 1, 1, false, false, null));

            final int randomRoomsIndex = random.nextInt(randomRooms.size());

            // Fills in the unoccupied spot in the maze with a room in randomRooms
            if (myMaze[randomRow][randomCol] == null) {
                myMaze[randomRow][randomCol] = randomRooms.get(randomRoomsIndex);
                filledCount++;
                essentialRoomsIndex++;
            }
        }

        //TODO: implement the algorithm to check
        // if the maze is traversable, otherwise regenerate a new maze
    }

    /**
     * Returns the dungeon as a maze of type {@link Room}.
     *
     * @return a maze of type {@link Room}
     */
    public Room[][] getRooms() {
        return myMaze;
    }

    /**
     * Returns the player's current location in the {@link Dungeon}.
     *
     * @return the player's current location in the {@link Dungeon}
     */
    public Room getCharacterLocation() {
        return myCharacterLocation;
    }

    /**
     * Returns the {@link Room} in the maze at the specified coordinates.
     *
     * @param theX the x-coordinate of the {@link Room}
     * @param theY the y-coordinate of the {@link Room}
     * @return the {@link Room} in the maze at the specified coordinates
     */
    public Room getRoomAt(final int theX, final int theY) {
        return myMaze[theX][theY];
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        //TODO: maybe improve this implementation
        for (Room[] rooms : myMaze) {
            for (Room room : rooms) {
                stringBuilder.append("[");

                if (room == myStartingRoom) {
                    stringBuilder.append("entrance, ");
                } else if (room == myExitRoom) {
                    stringBuilder.append("exit, ");
                } else if (Arrays.asList(myPillarRooms).contains(room)) {
                    stringBuilder.append("contains ").append(room.getPillar()).append(", ");
                } else if (room == myCharacterLocation) {
                    stringBuilder.append("you are here, ");
                } else {
                    stringBuilder.append("dead-end, ");
                }
            }

            stringBuilder.append("]");
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
