package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a randomly generated maze of type {@link Room}.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class Dungeon {

    /**
     * The default dungeon size.
     */
    private static final Dimension MAZE_SIZE = new Dimension(6, 6);

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
    private final List<Room> myPillarRooms;

    /**
     * The room that the player is currently in.
     */
    private Room myCurrentRoom;


    /**
     * Initializes a 6x6 traversable {@link Dungeon}.
     *
     * @param theStartingRoom the entrance of the {@link Dungeon}
     * @param theExitRoom     the exit of the {@link Dungeon}
     * @param thePillarRooms  the room that contains a pillar of Object-Oriented
     */
    private Dungeon(final Room theStartingRoom,
                    final Room theExitRoom,
                    final List<Room> thePillarRooms) {

        myMaze = new Room[MAZE_SIZE.height][MAZE_SIZE.width];
        myExitRoom = theExitRoom;
        myStartingRoom = theStartingRoom;
        myPillarRooms = thePillarRooms;

        generateDungeon();
    }

    /**
     * Default constructor of {@link Dungeon} class.
     */
    public Dungeon() {
        this(
                new Room(true, false, null),
                new Room(false, true, null),
                generatePillarRooms()
        );
    }

    /**
     * Generates and returns an array of all Pillar rooms.
     *
     * @return a list of Pillar rooms.
     */
    public static List<Room> generatePillarRooms() {
        final List<Room> pillarRooms = new ArrayList<>();
        final Class<?>[] pillars = Helper.getPillarList();

        for (Class<?> pillar : pillars) {
            pillarRooms.add(new Room(false, false, pillar));
        }

        return pillarRooms;
    }


    /**
     * Constructs a random-generated maze of type {@link Room}
     * with a traversable path from the entrance to the exit
     * and 4 pillars of Object-Oriented randomly placed in the maze.
     */
    private void generateDungeon() {
        placeEntranceAndExit();
        placePillarRooms();
        placeFillerRooms();

//        if (!isTraversable()) {
//            generateDungeon();
//        }
    }

    /**
     * Places the starting {@link Room} and the exit {@link Room} in random positions
     * on the edges of the dungeon such that they won't be
     * on the same row or column.
     */
    private void placeEntranceAndExit() {
        //separate maps of key-value index pairs (row : column) for starting and exit rooms
        final Map<Integer, Integer> randomEdgesStarting = new HashMap<>();
        final Map<Integer, Integer> randomEdgesExit = new HashMap<>();

        // random upper edge index pair for starting room
        randomEdgesStarting.put(0, Helper.getRandomIntBetween(0, MAZE_SIZE.width));
        // random lower edge index pair for exit room
        randomEdgesExit.put(MAZE_SIZE.height - 1,
                Helper.getRandomIntBetween(0, MAZE_SIZE.width));
        // random left edge index pair for starting room
        randomEdgesStarting.put(Helper.getRandomIntBetween(0, MAZE_SIZE.height), 0);
        // random right edge index pair for exit room
        randomEdgesExit.put(Helper.getRandomIntBetween(0, MAZE_SIZE.height),
                MAZE_SIZE.width - 1);

        //Uses separate random index pairs for starting and exit rooms
        final Map.Entry<Integer, Integer> randomStartingEntry =
                getRandomIndexPair(randomEdgesStarting);
        final Map.Entry<Integer, Integer> randomExitEntry =
                getRandomIndexPair(randomEdgesExit);

        final int startingRow = randomStartingEntry.getKey();
        final int startingCol = randomStartingEntry.getValue();
        final int exitRow = randomExitEntry.getKey();
        final int exitCol = randomExitEntry.getValue();

        /*
        Recursively invokes this method if the extreme cases are satisfied
        (entrance and exit are on the same horizontal or vertical side)
         */
        if (startingRow == 0 && exitRow == 0) {
            placeEntranceAndExit();
        } else if (startingRow == MAZE_SIZE.width - 1 && exitRow == MAZE_SIZE.width - 1) {
            placeEntranceAndExit();
        } else if (startingCol == 0 && exitCol == 0) {
            placeEntranceAndExit();
        } else if (startingCol == MAZE_SIZE.height - 1 && exitCol == MAZE_SIZE.height - 1) {
            placeEntranceAndExit();
        } else {
            myMaze[startingRow][startingCol] = myStartingRoom;
            myStartingRoom.setDungeonLocation(new Point(startingRow, startingCol));
            myMaze[exitRow][exitCol] = myExitRoom;
            myExitRoom.setDungeonLocation(new Point(exitRow, exitCol));

        }
    }

    /**
     * Returns a random entry from the map of index pairs.
     * Utility method for {@link #placeEntranceAndExit()}.
     *
     * @param theRandomEdges the map of index pairs
     * @return A random entry from the map of index pairs.
     */
    private Map.Entry<Integer, Integer> getRandomIndexPair(
            final Map<Integer, Integer> theRandomEdges) {
        //Converts the set of the map to a list
        final List<Map.Entry<Integer, Integer>> entryList =
                new ArrayList<>(theRandomEdges.entrySet());

        // Get a random index pair from the entry list
        final int randomIndexPair = Helper.getRandomIntBetween(0, entryList.size());

        return entryList.get(randomIndexPair);
    }

    /**
     * Randomly places the pillar {@link Room} throughout the dungeon.
     */
    private void placePillarRooms() {
        // Shuffles the list of rooms randomly
        Collections.shuffle(myPillarRooms, Helper.getRandom());
        int pillarRoomsIndex = 0;

        // Fills the maze until no empty spots are left
        while (pillarRoomsIndex < myPillarRooms.size()) {
            final int randomRow = Helper.getRandomIntBetween(0, MAZE_SIZE.height);
            final int randomCol = Helper.getRandomIntBetween(0, MAZE_SIZE.width);

            //Fills an unoccupied spot in the maze with a pillar room
            if (myMaze[randomRow][randomCol] == null) {
                final Room pillarRoom = myPillarRooms.get(pillarRoomsIndex);
                pillarRoom.setDungeonLocation(new Point(randomRow, randomCol));
                myMaze[randomRow][randomCol] = pillarRoom;
                pillarRoomsIndex++;
            }
        }
    }

    /**
     * Fully fills the dungeon with random dead-end or other non-essential rooms.
     */
    private void placeFillerRooms() {
        final int totalSpotsLeft = MAZE_SIZE.height * MAZE_SIZE.width - 6;
        int filledSpots = 0;

        while (filledSpots < totalSpotsLeft) {
            final int randomRow = Helper.getRandomIntBetween(0, MAZE_SIZE.height);
            final int randomCol = Helper.getRandomIntBetween(0, MAZE_SIZE.width);

            //Fills an unoccupied spot in the maze with a room
            if (myMaze[randomRow][randomCol] == null) {
                final Room room = new Room(false, false, null);
                room.setDungeonLocation(new Point(randomRow, randomCol));
                myMaze[randomRow][randomCol] = room;
                filledSpots++;
            }
        }
    }

    /**
     * Checks if the newly constructed dungeon is traversable.
     *
     * @return True if the dungeon is traversable
     */
    private boolean isTraversable() {
        boolean isTraversable = false;


        return isTraversable;
    }

    /**
     * Returns the dungeon as a maze of type {@link Room}.
     *
     * @return A maze of type {@link Room}
     */
    public Room[][] getRooms() {
        return myMaze;
    }

    /**
     * Places doors in each room of the dungeon.
     */
    public void placeDoors() {
        for (int i = 0; i < myMaze.length; i++) {
            for (int j = 0; j < myMaze[i].length; j++) {
                if (myMaze[i][j] != null) {
                    myMaze[i][j].placeDoors();
                }
            }
        }
    }

    public Room getCurrentRoom() {
        return this.myCurrentRoom;
    }

    public Room getStartingRoom() {
        return this.myStartingRoom;
    }

    /**
     * Loads the Hero character into the starting room
     * once the dungeon is created.
     */
    public void loadPlayerTo(final Point theDungeonXY,
                             final Point theRoomXY) {
        // TODO: Needs bound checks

        final Room room = this.myMaze[(int) theDungeonXY.getX()][(int) theDungeonXY.getY()];
        room.movePlayerTo(theRoomXY);
        this.myCurrentRoom = room;
    }

    /**
     * Returns the {@link Room} in the maze at the specified coordinates.
     *
     * @param theX the x-coordinate of the {@link Room}
     * @param theY the y-coordinate of the {@link Room}
     * @return The {@link Room} in the maze at the specified coordinates
     */
    public Room getRoomAt(final int theX, final int theY) {
        return myMaze[theX][theY];
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (Room[] rooms : myMaze) {
            for (Room room : rooms) {
                if (room == null) {
                    stringBuilder.append("null"); // IDEALLY nothing should be null.
                } else if (room.isEntranceRoom()) {
                    stringBuilder.append("ENTR");
                } else if (room.isExitRoom()) {
                    stringBuilder.append("EXIT");
                } else if (room.getPillar() != null) {
                    stringBuilder.append(" ").
                            append(room.getPillar().getDisplayChar()).
                            append("  ");
                } else {
                    stringBuilder.append("ROOM");
                }

                stringBuilder.append(" ");
            }

            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}