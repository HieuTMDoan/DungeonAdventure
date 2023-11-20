package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import com.tcss.dungeonadventure.objects.tiles.WallTile;
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
    private static List<Room> generatePillarRooms() {
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
    }

    /**
     * Places the starting {@link Room} and the exit {@link Room} in random positions
     * on the edges of the dungeon such that they won't be
     * on the same row or column.
     */
    private void placeEntranceAndExit() {
        // separate maps of key-value index pairs (row : column) for starting and exit rooms
        final Map<Integer, Integer> randomEdgesStarting = new HashMap<>();
        final Map<Integer, Integer> randomEdgesExit = new HashMap<>();

        // upper/lower limits of where random row/column indices can be
        final int columnLimit = MAZE_SIZE.width / 2;
        final int rowLimit = MAZE_SIZE.height / 2;

        // random upper edge index pair for starting room
        randomEdgesStarting.put(0, Helper.getRandomIntBetween(0, columnLimit));
        // random left edge index pair for starting room
        randomEdgesStarting.put(Helper.getRandomIntBetween(0, rowLimit), 0);
        // random lower edge index pair for exit room
        randomEdgesExit.put(MAZE_SIZE.height - 1,
                Helper.getRandomIntBetween(columnLimit, MAZE_SIZE.width));
        // random right edge index pair for exit room
        randomEdgesExit.put(Helper.getRandomIntBetween(rowLimit, MAZE_SIZE.height),
                MAZE_SIZE.width - 1);

        // uses separate random index pairs for starting and exit rooms
        final Map.Entry<Integer, Integer> randomStartingEntry =
                getRandomIndexPair(randomEdgesStarting);
        final Map.Entry<Integer, Integer> randomExitEntry =
                getRandomIndexPair(randomEdgesExit);

        final int startingRow = randomStartingEntry.getKey();
        final int startingCol = randomStartingEntry.getValue();
        final int exitRow = randomExitEntry.getKey();
        final int exitCol = randomExitEntry.getValue();

        myMaze[startingRow][startingCol] = myStartingRoom;
        myStartingRoom.setDungeonLocation(new Point(startingRow, startingCol));
        myMaze[exitRow][exitCol] = myExitRoom;
        myExitRoom.setDungeonLocation(new Point(exitRow, exitCol));
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
        // Shuffles the list of pillar rooms randomly
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
     * Checks if the newly constructed dungeon is traversable,
     * meaning it has at least one available route from the entrance room to the exit room.
     *
     * @param theMaze   the dungeon to be checked
     * @param theRow    the row index of the room to be checked for accessibility
     * @param theCol    the col index of the room to be checked for accessibility
     * @return          True if the dungeon is traversable
     */
    private boolean isTraversable(final Room[][] theMaze,
                                  final char[][] theTestMaze,
                                  final int theRow,
                                  final int theCol) {
        boolean traversable = false;

        if (validMove(theMaze, theTestMaze, theRow, theCol)) {
            theTestMaze[theRow][theCol] = 'v'; //marks the room visited

            if (theMaze[theRow][theCol].isExitRoom()) {
                return true; //returns true if at exit room
            }

            //not at exit so need to try other directions
            traversable = isTraversable(theMaze, theTestMaze,
                    theRow + 1, theCol); //travel down
            if (!traversable) {
                traversable = isTraversable(theMaze, theTestMaze,
                        theRow, theCol + 1); //travel right
            }
            if (!traversable) {
                traversable = isTraversable(theMaze, theTestMaze,
                        theRow - 1, theCol); //travel up
            }
            if (!traversable) {
                traversable = isTraversable(theMaze, theTestMaze,
                        theRow, theCol - 1); //traverse left
            }
        }

        return traversable;
    }

    /**
     * Checks if the room at the specified position in the dungeon can be accessed.
     *
     * @param theMaze     the actual dungeon
     * @param theTestMaze the dummy dungeon with markings of visited rooms
     * @param theRow      the row index of the room in the dungeon
     * @param theColumn   the column index of the room in the dungeon
     * @return            True if the room can be accessed
     */
    private boolean validMove(final Room[][] theMaze,
                              final char[][] theTestMaze,
                              final int theRow,
                              final int theColumn) {
        return theRow >= 0 && theRow < theMaze.length
                && theColumn >= 0 && theColumn < theMaze[0].length
                && theMaze[theRow][theColumn].getDoorNumber() > 1
                && theTestMaze[theRow][theColumn] == '\u0000';
    }

    /**
     * Places doors in each room of the dungeon.
     */
    /**
     * Places doors in each room of the dungeon.
     * Limits the number of attempts to prevent infinite recursion.
     */
    public void placeDoors() {
        int attempts = 0;
        final int maxAttempts = 100; // Set an appropriate maximum attempt limit

        do {
            for (Room[] rooms : myMaze) {
                for (Room room : rooms) {
                    if (room != null) {
                        // Place doors at wall locations with a limit of 4 doors
                        room.placeDoors(room, getWallLocations(room));
                    }
                }
            }

            // Dummy maze for marking visited and dead-end locations in the dungeon
            final char[][] testMaze = new char[MAZE_SIZE.height][MAZE_SIZE.width];

            // Recursively regenerates the dungeon if not traversable
            if (!isTraversable(myMaze, testMaze,
                    myStartingRoom.getDungeonLocation().x,
                    myStartingRoom.getDungeonLocation().y)) {
                // Increment attempts and print a debug statement
                attempts++;
                System.out.println("Attempt #" + attempts + ": Dungeon regeneration failed.");
            } else {
                // Dungeon is traversable, break out of the loop
                break;
            }
        } while (attempts < maxAttempts);

        if (attempts >= maxAttempts) {
            // Handle the case where maximum attempts are reached
            System.out.println("Maximum attempts reached. Unable to generate a traversable dungeon.");
        }
    }


    /**
     * Returns a list of wall locations in the specified room.
     *
     * @param theRoom The room to get wall locations from.
     * @return A list of wall locations in the room.
     */
    private List<Point> getWallLocations(final Room theRoom) {
        final List<Point> wallLocations = new ArrayList<>();
        final Tile[][] roomTiles = theRoom.getRoomTiles();

        for (int i = 0; i < roomTiles.length; i++) {
            for (int j = 0; j < roomTiles[i].length; j++) {
                if (roomTiles[i][j] instanceof WallTile) {
                    wallLocations.add(new Point(j, i));
                }
            }
        }

        return wallLocations;
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
     * Accessor for the room that the player is currently in.
     *
     * @return The players' current room.
     */
    public Room getCurrentRoom() {
        return this.myCurrentRoom;
    }

    /**
     * Accessor for the starting room.
     *
     * @return The starting room.
     */
    public Room getStartingRoom() {
        return this.myStartingRoom;
    }

    /**
     * Loads the player into the dungeon at a specific XY,
     * and in a specific XY in the room.
     */
    public void loadPlayerTo(final Room theRoom,
                             final Point theRoomXY) {
        // TODO: Needs bound checks

        theRoom.setPlayerLocation(theRoomXY);
        this.myCurrentRoom = theRoom;
    }

    public void loadPlayerTo(final Room theRoom,
                             final Directions.Cardinal theOriginalDirection) {
        this.myCurrentRoom.setPlayerLocation((Point) null);
        theRoom.setPlayerLocation(theOriginalDirection);
        this.myCurrentRoom = theRoom;
    }

    /**
     * Returns the {@link Room} in the maze at the specified coordinates.
     *
     * @param theX the x-coordinate of the {@link Room}
     * @param theY the y-coordinate of the {@link Room}
     * @return The {@link Room} in the maze at the specified coordinates
     */
    public Room getRoomAt(final int theX, final int theY) {
        try {
            return myMaze[theX][theY];
        } catch (final ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (Room[] rooms : myMaze) {
            for (Room room : rooms) {
                if (room == null) {
                    stringBuilder.append("null"); // IDEALLY nothing should be null.
                } else if (room.getPlayerXPosition() != null) {
                    stringBuilder.append("HERE");
                } else if (room.isEntranceRoom()) {
                    stringBuilder.append("ENTR");
                } else if (room.isExitRoom()) {
                    stringBuilder.append("EXIT");
                } else if (room.getPillar() != null) {
                    stringBuilder.append(" ").
                            append(room.getPillar().getDisplayChar()).
                            append("  ");
                } else {
                    stringBuilder.append("|").
                            append(room.getDungeonLocation().x).
                            append(room.getDungeonLocation().y).
                            append("|");
                }

                stringBuilder.append(" ");
            }

            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}