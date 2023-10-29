package com.tcss.dungeonadventure.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Represents a randomly generated maze of {@link Room}.
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360, Fall 2023
 */
public class Dungeon {
    /**
     * The 2D representation of the dungeon.
     */
    private final Room[][] myMaze;

    /**
     * The entrance of the dungeon.
     */
    private final Room myStartingRoom;

    /**
     * The exit of the dungeon.
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
     * Initializes a 6x6 traversable dungeon.
     * @param theMaze the empty maze of rooms of size 6x6
     * @param theStartingRoom the entrance of the dungeon
     * @param theExitRoom the exit of the dungeon
     * @param thePillarRooms the room that contains a pillar of Object-Oriented
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
     * Constructs a random-generated maze of rooms
     * with a traversable path from the entrance to the exit
     * and 4 pillars of Object-Oriented randomly placed in the maze.
     */
    @SuppressWarnings("checkstyle:FinalLocalVariable")
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

        // Shuffles the list of objects randomly
        Collections.shuffle(essentialRooms, random);

        /*
        totalSpots is the total # of indices in the maze,
        filledCount is the # for occupied indices
        essentialRoomsIndex is the index of the shuffled essentialRooms
         */
        final int totalSpots = myMaze.length * myMaze[0].length;
        int filledCount = 0;
        int essentialRoomsIndex = 0;

        // Fills the 2D array until no empty spots are left
        while (filledCount < totalSpots && essentialRoomsIndex < essentialRooms.size()) {
            final int randomRow = random.nextInt(myMaze.length);
            final int randomCol = random.nextInt(myMaze[0].length);

            // A list which its chosen element can be either an essential room or a dead-end room
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
     * Returns a maze of rooms.
     * @return a maze of rooms
     */
    public Room[][] getRooms() {
        return myMaze;
    }

    /**
     * Returns the player's current location in the Dungeon.
     * @return the player's current location in the Dungeon
     */
    public Room getCharacterLocation() {
        return myCharacterLocation;
    }

    /**
     * Returns the room in the maze at the specified coordinates.
     * @param theX the x-coordinate of the room
     * @param theY the y-coordinate of the room
     * @return the room in the maze at the specified coordinates
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
