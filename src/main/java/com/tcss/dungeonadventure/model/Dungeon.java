package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.monsters.Monster;
import com.tcss.dungeonadventure.objects.tiles.NPCTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serial;
import java.io.Serializable;
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
public class Dungeon implements Serializable {
    /**
     * The default dungeon size.
     */
    public static final Dimension MAZE_SIZE = new Dimension(10, 10);

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The chance for a door to generate.
     */
    private static final double DOOR_CHANCE = 0.7;

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
     * The 2D representation of the {@link Dungeon}.
     */
    private Room[][] myMaze;

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
    public Dungeon(final Room theStartingRoom,
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

        for (Class<?> pillar : Helper.getPillarList()) {
            pillarRooms.add(new Room(false, false, pillar));
        }

        return pillarRooms;
    }

    /**
     * Constructs a random-generated maze of type {@link Room}
     * with an entrance, an exit
     * and 4 pillars of Object-Oriented randomly placed in the maze.
     */
    private void generateDungeon() {
        while (true) {
            placeEntranceAndExit();

            if (!generatePath()) { // reset dungeon and try again
                System.out.println("WARNING: Regenerating dungeon...");
                myMaze = new Room[MAZE_SIZE.height][MAZE_SIZE.width];
                continue;
            }
            break;
        }
        generateFillerRooms();
        generateDoors();
        generateExtraWalls();

    }

    /**
     * Adds extra terrain to rooms. This needs to be called AFTER doors are
     * generated to ensure no doors are blocked by terrain.
     */
    private void generateExtraWalls() {
        for (final Room[] row : myMaze) {
            for (final Room room : row) {
                Room.addExtraWalls(room);
            }
        }
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
     * Returns a list of valid possible directions to generate a room in.
     * Will not add a direction to the list if it is against a wall.
     *
     * @param theLocation The location to check the 4 cardinal directions in.
     * @return A List<Cardinal> of valid directions.
     */
    private List<Directions.Cardinal> getPossibleDirections(final Point theLocation) {
        final List<Directions.Cardinal> possibleDirections = new ArrayList<>();

        if (theLocation.getX() != 0) { // Not at the top
            possibleDirections.add(Directions.Cardinal.NORTH);
        }
        if (theLocation.getY() < MAZE_SIZE.getWidth() - 1) { // Not on the right wall
            possibleDirections.add(Directions.Cardinal.EAST);
        }
        if (theLocation.getX() < MAZE_SIZE.getHeight() - 1) { // Not at the bottom
            possibleDirections.add(Directions.Cardinal.SOUTH);
        }
        if (theLocation.getY() != 0) { // Not on the left wall
            possibleDirections.add(Directions.Cardinal.WEST);
        }
        return possibleDirections;
    }


    /**
     * Generates a path to the start to the end, including
     * adding pillar rooms and random rooms along that path. Also generates the doors.
     *
     * @return True if the dungeon has a traversable path, false otherwise.
     */
    private boolean generatePath() {
        Point currentLocation = myStartingRoom.getDungeonLocation();
        final Point endingLocation = myExitRoom.getDungeonLocation();
        final List<Directions.Cardinal> path = new ArrayList<>();
        final List<Point> pathRoomLocations = new ArrayList<>();

        final int maxAttempts = 200;
        int currentAttempt = 0;

        while (!currentLocation.equals(endingLocation)) {
            if (currentAttempt == maxAttempts) {
                return false;
            }
            currentAttempt++;

            // Populates a list with possible valid directions
            // Ignores directions if it is on the maze boundary
            final List<Directions.Cardinal> possibleDirections
                    = getPossibleDirections(currentLocation);

            // If there are no possible directions, return false to try again.
            if (possibleDirections.isEmpty()) {
                return false;
            }

            // Chooses a random direction to go in
            final Directions.Cardinal randomDirection =
                    possibleDirections.get(
                            Helper.getRandomIntBetween(0, possibleDirections.size()));

            // Calculates offset
            final int x = currentLocation.x + randomDirection.getXOffset();
            final int y = currentLocation.y + randomDirection.getYOffset();

            // If the room is already populated with something, try again.
            if (getRoomAt(x, y) != null) {
                continue;
            }
            path.add(randomDirection);

            // Update the current location to the next room of the path
            currentLocation = new Point(x, y);
            pathRoomLocations.add(currentLocation);
            // Creates a new room without args. This indicates a path placeholder room.
            myMaze[x][y] = new Room();

            // Checks if the 4 adjacent rooms are the exit room.
            // If it is, then a path to the exit has been made successfully.
            boolean nextToExit = false;
            for (final Directions.Cardinal direction : Directions.Cardinal.values()) {
                final Room room =
                        getRoomAt(x + direction.getXOffset(), y + direction.getYOffset());
                if (room != null && room.isExitRoom()) {
                    path.add(direction);
                    nextToExit = true;
                    break;
                }
            }
            if (nextToExit) {
                System.out.println("Path to exit has been found.");
                break;
            }
        }

        // Now that the path has been created, replace path rooms with
        // pillar rooms and filler rooms
        // Shuffle the path points to add the pillar rooms to the first 4
        Collections.shuffle(pathRoomLocations);
        for (int i = 0; i < 4; i++) {
            final Point roomPoint = pathRoomLocations.get(0);
            final Room pillarRoom = this.myPillarRooms.get(i);
            myMaze[roomPoint.x][roomPoint.y] = pillarRoom;
            pillarRoom.setDungeonLocation(roomPoint);

            pathRoomLocations.remove(0);
        }
        // Fill in the rest of the path points with general rooms.
        for (final Point roomPoint : pathRoomLocations) {
            final Room room = new Room(false, false, null);
            room.setDungeonLocation(roomPoint);
            myMaze[roomPoint.x][roomPoint.y] = room;
        }

        // Generate doors connecting the path
        Room currentRoom = myStartingRoom;
        for (final Directions.Cardinal direction : path) {
            // find the room along the path
            final Room otherRoom = getRoomAt(
                    currentRoom.getDungeonLocation().x + direction.getXOffset(),
                    currentRoom.getDungeonLocation().y + direction.getYOffset());

            // connect currentRoom and otherRoom
            currentRoom.addDoorToWall(direction, otherRoom);
            otherRoom.addDoorToWall(direction.getOpposite(), currentRoom);

            currentRoom = otherRoom;
        }
        System.out.println(path + " \n");
        return true;
    }


    /**
     * Populates the dungeon with filler rooms.
     */
    private void generateFillerRooms() {
        // Fill in the rest of the dungeon with random rooms.
        for (int i = 0; i < myMaze.length; i++) {
            for (int j = 0; j < myMaze[i].length; j++) {
                if (myMaze[i][j] == null) {
                    final Room room = new Room(false, false, null);
                    room.setDungeonLocation(new Point(i, j));
                    myMaze[i][j] = room;
                }
            }
        }
    }

    /**
     * Iterates over the dungeon both horizontally and vertically
     * and randomly add doors.
     */
    private void generateDoors() {
        // Iterate over the dungeon row by row (horizontally), and
        // randomly generate doors connecting horizontally adjacent rooms.
        for (final Room[] row : myMaze) {
            for (int i = 0; i < row.length - 1; i++) {
                final Room room1 = row[i];
                final Room room2 = row[i + 1];

                if (Helper.getRandomDoubleBetween(0, 1) < DOOR_CHANCE) {
                    room1.addDoorToWall(Directions.Cardinal.EAST, room2);
                    room2.addDoorToWall(Directions.Cardinal.WEST, room1);
                }
            }
        }

        // Iterate over the dungeon col by col (vertically), and
        // randomly generate doors connecting vertically adjacent rooms.
        for (int i = 0; i < myMaze[0].length; i++) {
            for (int j = 0; j < myMaze.length - 1; j++) {

                final Room room1 = myMaze[j][i];
                final Room room2 = myMaze[j + 1][i];

                if (Helper.getRandomDoubleBetween(0, 1) < DOOR_CHANCE) {
                    room1.addDoorToWall(Directions.Cardinal.SOUTH, room2);
                    room2.addDoorToWall(Directions.Cardinal.NORTH, room1);
                }
            }
        }

        // Iterate over each room in the dungeon and check if it has
        // any doors leading into it. If there are no doors leading
        // into the room, choose a random, valid direction and add a door
        // connecting the room in the specified direction.

        for (int i = 0; i < myMaze.length; i++) {
            for (int j = 0; j < myMaze[i].length; j++) {
                final List<Directions.Cardinal> doorDirections = new ArrayList<>();

                final Room room = myMaze[i][j];

                for (final Directions.Cardinal direction : Directions.Cardinal.values()) {
                    if (room.findDoorOnWall(direction) != null) {
                        doorDirections.add(direction);
                    }
                }

                if (doorDirections.isEmpty()) {
                    // Choose a random possible direction and add a door there

                    final List<Directions.Cardinal> possibleDirections
                            = getPossibleDirections(new Point(i, j));

                    // Chooses a random direction to go in
                    final Directions.Cardinal randomDirection =
                            possibleDirections.get(
                                    Helper.getRandomIntBetween(0, possibleDirections.size()));

                    final int x = i + randomDirection.getXOffset();
                    final int y = j + randomDirection.getYOffset();

                    final Room otherRoom = getRoomAt(x, y);

                    room.addDoorToWall(randomDirection, otherRoom);
                    otherRoom.addDoorToWall(randomDirection.getOpposite(), otherRoom);


                }
            }
        }


    }


    /**
     * Searches the 8 surrounding tiles around the player if there are
     * any monsters around. If there are monsters, return them in an
     * array. Otherwise, return null.
     *
     * @return A Monster[] of the surrounding monsters, null if there are no monsters.
     */
    public Monster[] getAnySurroundingMonsters() {
        final List<Monster> surroundingMonsters = new ArrayList<>();
        final Tile[][] roomTiles = myCurrentRoom.getRoomTiles();
        final int x = myCurrentRoom.getPlayerXPosition();
        final int y = myCurrentRoom.getPlayerYPosition();

        for (final Directions.Cardinal d : Directions.Cardinal.values()) {
            try {
                final Tile tile = roomTiles[x + d.getXOffset()][y + d.getYOffset()];
                if (!(tile instanceof final NPCTile npcTile)) {
                    continue;
                }

                if (npcTile.getNPC() instanceof Monster && npcTile.getNPC().getHealth() > 0) {
                    surroundingMonsters.add((Monster) npcTile.getNPC());
                }
            } catch (final ArrayIndexOutOfBoundsException ignored) {
            }
        }

        return surroundingMonsters.size() == 0
                ? null : surroundingMonsters.toArray(new Monster[0]);
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

        for (final Room[] row : myMaze) {
            for (final Room room : row) {
                stringBuilder.append(" |");
                if (room == null) {
                    stringBuilder.append("null"); // IDEALLY nothing should be null.
                } else if (room.getPlayerXPosition() != null) {
                    stringBuilder.append("HERE");
                } else if (room.getRoomHeight() == null) {
                    stringBuilder.append("PATH");
                } else if (room.isEntranceRoom()) {
                    stringBuilder.append("ENTR");
                } else if (room.isExitRoom()) {
                    stringBuilder.append("EXIT");
                } else {
                    final String north
                            = room.findDoorOnWall(Directions.Cardinal.NORTH) != null
                            ? "N"
                            : " ";
                    final String south
                            = room.findDoorOnWall(Directions.Cardinal.SOUTH) != null
                            ? "S"
                            : " ";
                    final String east = room.findDoorOnWall(Directions.Cardinal.EAST) != null
                            ? "E"
                            : " ";
                    final String west
                            = room.findDoorOnWall(Directions.Cardinal.WEST) != null
                            ? "W"
                            : " ";
                    stringBuilder.append(north).append(south).append(east).append(west);
                }
//                } else if (room.getPillar() != null) {
//                    stringBuilder.append(" ").
//                            append(room.getPillar().getDisplayChar()).
//                            append("  ");
//                } else {
//                    stringBuilder.append("|").
//                            append(room.getDungeonLocation().x).
//                            append(room.getDungeonLocation().y).
//                            append("|");
//                }

                stringBuilder.append("| ");
            }

            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
