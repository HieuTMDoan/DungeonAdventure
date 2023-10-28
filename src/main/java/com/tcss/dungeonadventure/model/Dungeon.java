package com.tcss.dungeonadventure.model;

import java.util.Arrays;

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
     * The entrance of the dungeon
     */
    private final Room myStartingRoom;

    /**
     * The exit of the dungeon
     */
    private final Room myExitRoom;

    /**
     * The room that contains a pillar of Object-Oriented
     */
    private final Room[] myPillarRooms;

    /**
     * The current player's location
     */
    private Room myCharacterLocation;

    /**
     * Initializes a 6x6 traversable dungeon.
     * @param theMaze the maze of rooms of size 6x6
     * @param theStartingRoom the entrance of the dungeon
     * @param theExitRoom the exit of the dungeon
     * @param thePillarRoom the room that contains a pillar of Object-Oriented
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
        //How do we go about initializing the starting, exiting, and pillar Rooms?
        generateDungeon();
    }

    /**
     * Constructs a random-generated maze of rooms
     * with a traversable path from the entrance to the exit
     * and 4 pillars of Object-Oriented randomly placed in the maze.
     */
    private void generateDungeon() {
        //TODO: implement the algorithm to generate the Dungeon
        for (int row = 0; row < myMaze.length; row++) {
            for (int col = 0; col < myMaze[row].length; col++) {
                myMaze[row][col] =
            }
        }
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
        StringBuilder stringBuilder = new StringBuilder();

        //TODO: implement operations to build the String representation of Dungeon
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
