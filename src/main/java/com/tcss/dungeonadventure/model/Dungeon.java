package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.Directions;

import java.awt.Dimension;
import java.awt.Point;
import java.sql.SQLOutput;
import java.util.*;


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
    private static final Dimension MAZE_SIZE = new Dimension(10, 10);

    /**
     * The 2D representation of the {@link Dungeon}.
     */
    private Room[][] myMaze;

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
     * with an entrance, an exit
     * and 4 pillars of Object-Oriented randomly placed in the maze.
     */
    private void generateDungeon() {
        placeEntranceAndExit();

        if (!generatePathFromStartToExit()) { // reset dungeon and try again
            System.out.println("WARNING: Regenerating dungeon...");

            myMaze = new Room[MAZE_SIZE.height][MAZE_SIZE.width];
            generateDungeon();
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

    private boolean generatePathFromStartToExit() {
        final Point startingLocation = myStartingRoom.getDungeonLocation();
        final Point endingLocation = myExitRoom.getDungeonLocation();

        Point currentLocation = startingLocation;
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
            final List<Directions.Cardinal> possibleDirections = new ArrayList<>();

            if (currentLocation.getX() != 0) { // Not at the top
                possibleDirections.add(Directions.Cardinal.NORTH);
            }
            if (currentLocation.getY() < MAZE_SIZE.getWidth() - 1) { // Not on the right wall
                possibleDirections.add(Directions.Cardinal.EAST);
            }
            if (currentLocation.getX() < MAZE_SIZE.getHeight() - 1) { // Not at the bottom
                possibleDirections.add(Directions.Cardinal.SOUTH);
            }
            if (currentLocation.getY() != 0) { // Not on the left wall
                possibleDirections.add(Directions.Cardinal.WEST);
            }

            // If there are no possible directions, return false to
            // regenerate.
            if (possibleDirections.size() == 0) {
                return false;
            }

            // Chooses a random direction to go in
            final Directions.Cardinal randomDirection =
                    possibleDirections.get(
                            Helper.getRandomIntBetween(0, possibleDirections.size()));

            // NOTE: To have a more unique path, we could make the direction
            // to go not the same as the previous direction.
            // For example, if the previous traveled path was NORTH,
            // the next one CAN NOT be north UNLESS there are no valid directions
            // to travel in.


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
            // Creates a new room without a dimension. This indicates a path room.
            myMaze[x][y] = new Room();

            // Checks if the 4 adjacent rooms are the exit room.
            // If it is, then a path to the exit has been made successfully.

            boolean nextToExit = false;
            for (final Directions.Cardinal direction : Directions.Cardinal.values()) {
                final Room room = getRoomAt(x + direction.getXOffset(), y + direction.getYOffset());

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
        Collections.shuffle(pathRoomLocations);
        final List<Room> pillarRooms = generatePillarRooms();
        for (int i  = 0; i < 4; i++) {
            final Point p = pathRoomLocations.get(0);
            final Room pillarRoom = pillarRooms.get(0);
            myMaze[p.x][p.y] = pillarRoom;
            pillarRoom.setDungeonLocation(p);

            pathRoomLocations.remove(0);
            pillarRooms.remove(0);
        }

        for (final Point roomPoint : pathRoomLocations) {
            final Room room = new Room(false, false, null);
            room.setDungeonLocation(roomPoint);
            myMaze[roomPoint.x][roomPoint.y] = room;
        }

        // Generate doors connecting the path

        Room currentRoom = myStartingRoom;
        for (final Directions.Cardinal direction : path) {
            System.out.println(direction);

            final Room otherRoom = getRoomAt(
                    currentRoom.getDungeonLocation().x + direction.getXOffset(),
                    currentRoom.getDungeonLocation().y + direction.getYOffset());
            // connect currentRoom and otherRoom

            currentRoom.addDoorToWall(direction, otherRoom);
            otherRoom.addDoorToWall(direction.getOpposite(), currentRoom);

            currentRoom = otherRoom;
        }
        System.out.println(myStartingRoom);


        // Iterate over the dungeon row by row (horizontally), and
        // randomly generate doors connecting horizontally adjacent rooms.

        // TODO Implement me!


        // Iterate over the dungeon col by col (vertically), and
        // randomly generate doors connecting vertically adjacent rooms.

        // TODO Implement me!


        // Iterate over each room in the dungeon and check if it has
        // any doors leading into it. If there are no doors leading
        // into the room, choose a random, valid direction and add a door
        // connecting the room in the specified direction.

        // TODO Implement me!


        System.out.println(path + " \n");
        return true;
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
            return (Room) myMaze[theX][theY];
        } catch (final ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (Room[] row : myMaze) {
            for (Room room : row) {
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
