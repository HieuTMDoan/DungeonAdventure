package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;

import java.awt.Dimension;
import java.util.*;

import static com.tcss.dungeonadventure.model.Room.placeDoors;

/**
 * Represents a randomly generated maze of type {@link Room}.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class Dungeon {

    private static final Dimension MAZE_SIZE = new Dimension(6, 6);
    private Room[][] myMaze;
    private Room myStartingRoom;
    private Room myExitRoom;
    private List<Room> myPillarRooms;

    /**
     * Initializes a 6x6 traversable {@link Dungeon}.
     *
     * @param theStartingRoom the entrance of the {@link Dungeon}
     * @param theExitRoom     the exit of the {@link Dungeon}
     * @param thePillarRooms  the room that contains a pillar of Object-Oriented
     */
    private Dungeon(Room theStartingRoom, Room theExitRoom, List<Room> thePillarRooms) {
        myMaze = new Room[MAZE_SIZE.height][MAZE_SIZE.width];
        myExitRoom = theExitRoom;
        myStartingRoom = theStartingRoom;
        myPillarRooms = new ArrayList<>(thePillarRooms);

        generateDungeon();
    }

    /**
     * Memento class to store the state of the Dungeon.
     */
    private static class DungeonMemento {
        private final Room[][] myMaze;
        private final Room myStartingRoom;
        private final Room myExitRoom;
        private final List<Room> myPillarRooms;
        private final Room myCharacterLocation;

        public DungeonMemento(Room[][] maze, Room startingRoom, Room exitRoom,
                              List<Room> pillarRooms, Room characterLocation) {
            this.myMaze = deepCopyRooms(maze);
            this.myStartingRoom = startingRoom;
            this.myExitRoom = exitRoom;
            this.myPillarRooms = new ArrayList<>(pillarRooms);
            this.myCharacterLocation = characterLocation;
        }

        private Room[][] deepCopyRooms(Room[][] originalRooms) {
            Room[][] copy = new Room[originalRooms.length][];
            for (int i = 0; i < originalRooms.length; i++) {
                copy[i] = Arrays.copyOf(originalRooms[i], originalRooms[i].length);
            }
            return copy;
        }
    }


    public Dungeon() {
        this(
                new Room(true, false, null),
                new Room(false, true, null),
                generatePillarRooms()

        );
    }

    public static List<Room> generatePillarRooms() {
        final List<Room> pillarRooms = new ArrayList<>();
        final Class<?>[] pillars = Helper.getPillarList();

        for (Class<?> pillar : pillars) {
            pillarRooms.add(new Room(false, false, pillar));
        }

        return pillarRooms;
    }

    private void generateDungeon() {
        placeEntranceAndExit();
        placePillarRooms();
        placeFillerRooms();
        placeDoors();

        if (!isTraversable()) {
            generateDungeon();
        }
    }

    private void placeEntranceAndExit() {
        final Map<Integer, Integer> randomEdgesStarting = new HashMap<>();
        final Map<Integer, Integer> randomEdgesExit = new HashMap<>();

        randomEdgesStarting.put(0, Helper.getRandomIntBetween(0, MAZE_SIZE.width));
        randomEdgesExit.put(MAZE_SIZE.height - 1, Helper.getRandomIntBetween(0, MAZE_SIZE.width));
        randomEdgesStarting.put(Helper.getRandomIntBetween(0, MAZE_SIZE.height), 0);
        randomEdgesExit.put(Helper.getRandomIntBetween(0, MAZE_SIZE.height), MAZE_SIZE.width - 1);

        final Map.Entry<Integer, Integer> randomStartingEntry = getRandomIndexPair(randomEdgesStarting);
        final Map.Entry<Integer, Integer> randomExitEntry = getRandomIndexPair(randomEdgesExit);

        final int startingRow = randomStartingEntry.getKey();
        final int startingCol = randomStartingEntry.getValue();
        final int exitRow = randomExitEntry.getKey();
        final int exitCol = randomExitEntry.getValue();

        if (startingRow == exitRow || startingCol == exitCol) {
            placeEntranceAndExit();
        } else {
            myMaze[startingRow][startingCol] = myStartingRoom;
            myMaze[exitRow][exitCol] = myExitRoom;
        }
    }

    private Map.Entry<Integer, Integer> getRandomIndexPair(final Map<Integer, Integer> theRandomEdges) {
        final List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(theRandomEdges.entrySet());
        final int randomIndexPair = Helper.getRandomIntBetween(0, entryList.size());

        return entryList.get(randomIndexPair);
    }

    private void placePillarRooms() {
        Collections.shuffle(myPillarRooms, Helper.getRandom());
        int pillarRoomsIndex = 0;

        while (pillarRoomsIndex < myPillarRooms.size()) {
            final int randomRow = Helper.getRandomIntBetween(0, MAZE_SIZE.height);
            final int randomCol = Helper.getRandomIntBetween(0, MAZE_SIZE.width);

            if (myMaze[randomRow][randomCol] == null) {
                myMaze[randomRow][randomCol] = myPillarRooms.get(pillarRoomsIndex);
                pillarRoomsIndex++;
            }
        }

    }
    /**
     * Places doors in each room of the dungeon.
     */
    public void placeDoors() {
        for (int i = 0; i < myMaze.length; i++) {
            for (int j = 0; j < myMaze[i].length; j++) {
                if (myMaze[i][j] != null) {
                    Room.placeDoors(myMaze[i][j].getRoomTiles());
                }
            }
        }
    }
        //TODO: implement the algorithm to check
        // if the maze is traversable, otherwise regenerate a new maze

    /**
     * Fully fills the dungeon with random dead-end or other non-essential rooms.
     */

    private void placeFillerRooms() {
        final int totalSpotsLeft = MAZE_SIZE.height * MAZE_SIZE.width - 6;
        int filledSpots = 0;

        while (filledSpots < totalSpotsLeft) {
            final int randomRow = Helper.getRandomIntBetween(0, MAZE_SIZE.height);
            final int randomCol = Helper.getRandomIntBetween(0, MAZE_SIZE.width);

            if (myMaze[randomRow][randomCol] == null) {
                myMaze[randomRow][randomCol] = new Room(false, false, null);
                filledSpots++;
            }
        }
    }


    private boolean isTraversable() {
        // TODO: implement the algorithm to check if the maze is traversable, otherwise regenerate a new maze
        return false;
    }

    public Room[][] getRooms() {
        return myMaze;
    }

    public void loadHeroIntoStartingRoom() {
        myStartingRoom.loadPlayerToEntrance();
    }

    public Room getRoomAt(final int theX, final int theY) {
        return myMaze[theX][theY];
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (Room[] rooms : myMaze) {
            for (Room room : rooms) {
                if (room == null) {
                    stringBuilder.append("null");
                } else if (room.isEntranceRoom()) {
                    stringBuilder.append("ENTR");
                } else if (room.isExitRoom()) {
                    stringBuilder.append("EXIT");
                } else if (room.getPillar() != null) {
                    stringBuilder.append(" ").append(room.getPillar().getDisplayChar()).append("  ");
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
