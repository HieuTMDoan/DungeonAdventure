package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.items.Item;
import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.objects.items.PillarOfAbstraction;
import com.tcss.dungeonadventure.objects.items.PillarOfEncapsulation;
import com.tcss.dungeonadventure.objects.items.PillarOfInheritance;
import com.tcss.dungeonadventure.objects.items.PillarOfPolymorphism;
import com.tcss.dungeonadventure.objects.monsters.Monster;
import com.tcss.dungeonadventure.objects.tiles.DoorTile;
import com.tcss.dungeonadventure.objects.tiles.EmptyTile;
import com.tcss.dungeonadventure.objects.tiles.EntranceTile;
import com.tcss.dungeonadventure.objects.tiles.ExitTile;
import com.tcss.dungeonadventure.objects.tiles.ItemTile;
import com.tcss.dungeonadventure.objects.tiles.NPCTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import com.tcss.dungeonadventure.objects.tiles.WallTile;


import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class Room {

    /**
     * The maximum size of a room.
     */
    private static final Dimension MAX_ROOM_DIMENSION = new Dimension(10, 10);


    /**
     * The minimum size of a room.
     */
    private static final Dimension MIN_ROOM_DIMENSION = new Dimension(5, 5);

    /**
     * The chance for a room to contain two items.
     */
    private static final double TWO_ITEM_CHANCE = 0.15;


    /**
     * The chance for a room to contain one item.
     */
    private static final double ONE_ITEM_CHANCE = 0.35;


    /**
     * The chance for a room to contain two monsters.
     */
    private static final double TWO_MONSTER_CHANCE = 0.15;


    /**
     * The chance for a room to contain one monster.
     */
    private static final double ONE_MONSTER_CHANCE = 0.35;

    /**
     * The max number of doors that can generate in a room.
     */
    private static final int MAX_DOORS = 4;

    /**
     * Boolean if the room is the entrance room.
     */
    private final boolean myIsEntranceRoom;

    /**
     * Boolean if the room is the exit room.
     */
    private final boolean myIsExitRoom;

    /**
     * The dimensions of the room.
     */
    private final Dimension myRoomDimensions;
    /**
     * The pillar that this room contains. May be null.
     */
    private final Item myPillar;
    /**
     * The tiles in the room.
     */
    private Tile[][] myRoomData;
    /**
     * The location that the room is located at within
     * the dungeon.
     */
    private Point myDungeonLocation;
    /**
     * The current position of the player, or null if the player is not in the room.
     */
    private Point myPlayerPosition;


    /**
     * Constructor to creating a room with an existing tile set.
     * This may be useful when loading a room from files.
     *
     * @param theTiles The tiles inside the room.
     */
    public Room(final Tile[][] theTiles) {
        this.myRoomData = theTiles;
        this.myIsEntranceRoom = contains(TileChars.Room.ENTRANCE);
        this.myIsExitRoom = contains(TileChars.Room.EXIT);
        this.myRoomDimensions = new Dimension(theTiles[0].length, theTiles.length);

        myPillar = Arrays.stream(myRoomData).
                flatMap(Arrays::stream).
                filter(tile -> tile.getClass() == ItemTile.class).
                map(tile -> ((ItemTile) tile).getItem()).
                filter(item -> item.getItemType() == Item.ItemTypes.PILLAR).
                findFirst().
                orElse(null);
    }

    /**
     * Constructor to creating a NEW room based on parameters.
     *
     * @param theIsEntrance If the room is the entrance room.
     * @param theIsExit     If the room is the exit room.
     * @param thePillar     The class of the pillar contained in the room - can be null.
     */
    public Room(final boolean theIsEntrance,
                final boolean theIsExit,
                final Class<?> thePillar) {

        this(generateRandomTileSet(theIsEntrance, theIsExit, thePillar));

    }


    /**
     * Copy constructor for creating a deep copy of the Room.
     *
     * @param theOriginalRoom The Room to copy.
     */
    public Room(final Room theOriginalRoom) {
        myIsEntranceRoom = theOriginalRoom.myIsEntranceRoom;
        myIsExitRoom = theOriginalRoom.myIsExitRoom;
        myRoomDimensions = new Dimension(theOriginalRoom.myRoomDimensions);
        myPillar = (theOriginalRoom.myPillar != null) ? theOriginalRoom.myPillar.copy() : null;
        myPlayerPosition =
                (theOriginalRoom.myPlayerPosition != null)
                        ? new Point(theOriginalRoom.myPlayerPosition)
                        : null;
        deepCopyRoomData(theOriginalRoom.myRoomData);
    }

    /**
     * Generates the tile data of a random room based on a set of parameters.
     *
     * @param theIsEntrance If the room is the entrance room.
     * @param theIsExit     If the room is the exit room.
     * @param thePillar     The class of the pillar contained in the room - can be null.
     * @return Tile[][] that represents the room.
     */
    public static Tile[][] generateRandomTileSet(final boolean theIsEntrance,
                                                 final boolean theIsExit,
                                                 final Class<?> thePillar) {

        if (theIsEntrance && theIsExit) {
            throw new IllegalArgumentException(
                    "Room cannot be an entrance and an exit.");
        }

        if (thePillar != null
                && thePillar != PillarOfAbstraction.class
                && thePillar != PillarOfInheritance.class
                && thePillar != PillarOfEncapsulation.class
                && thePillar != PillarOfPolymorphism.class) {
            throw new IllegalArgumentException(
                    thePillar.getSimpleName() + " is not a pillar.");
        }


        final int roomWidth = Helper.getRandomIntBetween(
                (int) MIN_ROOM_DIMENSION.getWidth(),
                (int) MAX_ROOM_DIMENSION.getWidth() + 1);

        final int roomHeight = Helper.getRandomIntBetween(
                (int) MIN_ROOM_DIMENSION.getHeight(),
                (int) MAX_ROOM_DIMENSION.getHeight() + 1);


        final Tile[][] tiles = new Tile[roomHeight][roomWidth];

        for (int col = 0; col < tiles.length; col++) {
            for (int row = 0; row < tiles[col].length; row++) {
                if (row % (roomWidth - 1) == 0 || col % (roomHeight - 1) == 0) {
                    tiles[col][row] = new WallTile();
                } else {
                    tiles[col][row] = new EmptyTile();
                }
            }
        }

        /*
            x
          y [#, #, #, #, #, #, #]
            [#,  ,  ,  ,  ,  , #]
            [#,  ,  ,  ,  ,  , #]
            [#,  ,  ,  ,  ,  , #]
            [#,  ,  ,  ,  ,  , #]
            [#, #, #, #, #, #, #]

            Other tiles can only occupy from (1, 1) to (width - 1, height - 1)
        */

        // if the room is an exit or entrance, it shouldn't contain anything else.
        if (theIsEntrance || theIsExit) {
            putTileAtValidLocation(theIsEntrance ? new EntranceTile() : new ExitTile(), tiles);
            return tiles;
        }


        if (thePillar != null) {
            try {
                final Item pillar = (Item) thePillar.getConstructor().newInstance();
                putTileAtValidLocation(new ItemTile(pillar), tiles);
            } catch (final InstantiationException
                           | NoSuchMethodException
                           | IllegalAccessException
                           | InvocationTargetException e) {

                e.printStackTrace();
            }
        }


        final double itemRandom = Helper.getRandomDoubleBetween(0, 1);
        final int itemNum = (itemRandom < TWO_ITEM_CHANCE)
                ? 2
                : (itemRandom < ONE_ITEM_CHANCE)
                ? 1
                : 0;
        for (int i = 0; i < itemNum; i++) {
            final Item randomItem = Helper.getRandomItem();
            putTileAtValidLocation(new ItemTile(randomItem), tiles);
        }

        final double monsterRandom = Helper.getRandomDoubleBetween(0, 1);
        final int monsterNum = (monsterRandom < TWO_MONSTER_CHANCE)
                ? 2
                : (monsterRandom < ONE_MONSTER_CHANCE)
                ? 1
                : 0;
        for (int i = 0; i < monsterNum; i++) {
            final Monster randomMonster = Helper.getRandomMonster();
            putTileAtValidLocation(new NPCTile(randomMonster), tiles);
        }

        return tiles;
    }

    /**
     * Randomly places doors in the specified room. Doors are placed at random wall locations
     * making sure to avoid corners and that no two doors are placed right next to each other.
     *
     * @param theRoom          The Room to add doors to.
     * @param theWallLocations A list of wall locations where doors can potentially be placed.
     */
    public static void placeDoors(final Room theRoom,
                                  final List<Point> theWallLocations) {
        // Shuffle the wall locations to randomize door placement
        Collections.shuffle(theWallLocations, Helper.getRandom());

        int doorsPlaced = 0;
        final Tile[][] tiles = theRoom.getRoomTiles();
        final Set<Directions.Cardinal> usedLocations = new HashSet<>();


        for (final Point wallLocation : theWallLocations) {
            final int x = (int) wallLocation.getX();
            final int y = (int) wallLocation.getY();

            // Check if the location is in the corners, skip if true
            if (wallLocation.equals(new Point(0, 0))
                    || wallLocation.equals(new Point(theRoom.getRoomWidth() - 1, 0))
                    || wallLocation.equals(new Point(0, theRoom.getRoomHeight() - 1))
                    || wallLocation.equals(new Point(theRoom.getRoomWidth() - 1, theRoom.getRoomHeight() - 1))) {
                continue;
            }

            // Check to ensure only ONE door is placed along a wall.
            boolean foundDoor = false;
            if (x == 0 || x == theRoom.getRoomWidth() - 1) { // door is on left/right wall
                for (final Tile[] tile : tiles) {
                    if (tile[x].getClass() == DoorTile.class) {
                        foundDoor = true;
                        break;
                    }
                }
            } else if (y == 0 || y == theRoom.getRoomHeight() - 1) { // door is on top/bottom wall
                for (final Tile tile : tiles[y]) {
                    if (tile.getClass() == DoorTile.class) {
                        foundDoor = true;
                        break;
                    }
                }
            }
            if (foundDoor) {
                continue;
            }


            // Checks if there's a valid room to put a door to.
            // Not sure if I like this, but it works
            if (y == 0) { // top
                final Room room = theRoom.getAdjacentRoomByDirection(Directions.Cardinal.NORTH);
                if (room != null) {
                    tiles[y][x] = new DoorTile(Directions.Cardinal.NORTH, room);
                }
                if (usedLocations.add(Directions.Cardinal.NORTH)) {
                    doorsPlaced++;
                }
            }
            if (y == theRoom.getRoomHeight() - 1) { // bottom
                final Room room = theRoom.getAdjacentRoomByDirection(Directions.Cardinal.SOUTH);
                if (room != null) {
                    tiles[y][x] = new DoorTile(Directions.Cardinal.SOUTH, room);
                }
                if (usedLocations.add(Directions.Cardinal.SOUTH)) {
                    doorsPlaced++;
                }
            }
            if (x == 0) { // left
                final Room room = theRoom.getAdjacentRoomByDirection(Directions.Cardinal.EAST);
                if (room != null) {
                    tiles[y][x] = new DoorTile(Directions.Cardinal.EAST, room);
                }
                if (usedLocations.add(Directions.Cardinal.EAST)) {
                    doorsPlaced++;
                }

            }
            if (x == theRoom.getRoomWidth() - 1) { // right
                final Room room = theRoom.getAdjacentRoomByDirection(Directions.Cardinal.WEST);
                if (room != null) {
                    tiles[y][x] = new DoorTile(Directions.Cardinal.WEST, room);
                }
                if (usedLocations.add(Directions.Cardinal.WEST)) {
                    doorsPlaced++;
                }


            }




            if (doorsPlaced >= MAX_DOORS) {
                return;  // Limit reached, exit the method
            }
        }
    }


    /**
     * Helper method to use while generating a new Room.
     * Will ensure no overlap between tiles.
     *
     * @param theTile  The tile to add to the tile set.
     * @param theTiles The current tile set.
     */
    private static void putTileAtValidLocation(final Tile theTile,
                                               final Tile[][] theTiles) {

        final Dimension size = new Dimension(theTiles[0].length, theTiles.length);

        while (true) {
            final int x = Helper.getRandomIntBetween(1, (int) (size.getWidth() - 1));
            final int y = Helper.getRandomIntBetween(1, (int) (size.getHeight() - 1));

            if (theTiles[y][x] != null && theTiles[y][x].getClass() != EmptyTile.class) {
                continue;
            }
            theTiles[y][x] = theTile;
            return;
        }

    }

    /**
     * Checks if a specific character exists in the tile set.
     *
     * @param theChar The character to look for. Pull this from {@link TileChars}
     * @return True if the character is in the room, false otherwise.
     */
    public boolean contains(final char theChar) {
        for (final Tile[] row : myRoomData) {
            for (final Tile tile : row) {
                if (tile.getDisplayChar() == theChar) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Moves the player in a specific direction.
     *
     * @param theDirection The direction to move the player in.
     */
    public void movePlayer(final Directions.Cardinal theDirection) {
        if (this.myPlayerPosition == null) {
            this.myPlayerPosition = new Point(1, 1); // TODO: Change this to where the player enters the room
        }


        final Point tempPoint = new Point(myPlayerPosition);
        switch (theDirection) {
            case NORTH -> tempPoint.translate(-1, 0);
            case EAST -> tempPoint.translate(0, 1);
            case SOUTH -> tempPoint.translate(1, 0);
            case WEST -> tempPoint.translate(0, -1);
            default -> throw new IllegalArgumentException(
                    "Illegal enum passed: " + theDirection);
        }


        final Tile tile = myRoomData[(int) tempPoint.getX()][(int) tempPoint.getY()];
        if (tile.isTraversable()) {
            myPlayerPosition = tempPoint;
            tile.onInteract();
        }


    }

    /**
     * Moves player to a specified location in the room. This may be useful
     * for loading a player in from a specific direction when coming through a door.
     *
     * @param theXY The player location.
     */
    public void setPlayerLocation(final Point theXY) {

        // TODO: Needs bound checks
        myPlayerPosition = theXY == null ? null : new Point(theXY);
    }

    public void setPlayerLocation(final Directions.Cardinal theOriginalDirection) {
        if (theOriginalDirection == null) {
            setPlayerLocation((Point) null);
            return;
        }
        final Tile[][] tiles = this.getRoomTiles();

        switch (theOriginalDirection) {
            case NORTH -> { // come from door from south
                for (int i = 0; i < myRoomData[this.getRoomHeight() - 1].length; i++) {
                    if (myRoomData[this.getRoomHeight() - 1][i].getClass() == DoorTile.class) {
                        myPlayerPosition = new Point(this.getRoomHeight() - 1, i);
                        break;
                    }
                }
            }
            case SOUTH -> { // come from door from north
                for (int i = 0; i < myRoomData[0].length; i++) {
                    if (myRoomData[0][i].getClass() == DoorTile.class) {
                        myPlayerPosition = new Point(0, i);
                        break;
                    }
                }
            }
            case EAST -> { // come from door from west
                for (int i = 0; i < tiles.length; i++) {
                    if (tiles[i][getRoomWidth() - 1].getClass() == DoorTile.class) {
                        myPlayerPosition = new Point(i, this.getRoomWidth() - 1);
                        break;
                    }
                }
            }

            case WEST -> { // come from door from east
                for (int i = 0; i < tiles.length; i++) {
                    if (tiles[i][0].getClass() == DoorTile.class) {
                        myPlayerPosition = new Point(i, 0);
                        break;
                    }
                }

            }
        }
    }

    /**
     * Returns the location of the room within the dungeon.
     *
     * @return A Point representing the rooms location in the dungeon.
     */
    public Point getDungeonLocation() {
        return this.myDungeonLocation;
    }

    /**
     * Sets the location of the room within the dungeon.
     *
     * @param theXY The location of the room within the dungeon.
     */
    public void setDungeonLocation(final Point theXY) {
        this.myDungeonLocation = new Point(theXY);
    }

    public Room getAdjacentRoomByDirection(final Directions.Cardinal theDirection) {
        final Dungeon dungeon = DungeonAdventure.getInstance().getDungeon();
        final int x = (int) this.getDungeonLocation().getX();
        final int y = (int) this.getDungeonLocation().getY();


        final Room room = switch (theDirection) {
            case NORTH -> dungeon.getRoomAt(x - 1, y);
            case SOUTH -> dungeon.getRoomAt(x + 1, y);
            case EAST -> dungeon.getRoomAt(x, y - 1);
            case WEST -> dungeon.getRoomAt(x, y + 1);
        };


        return room;

    }

    /**
     * @return True if the room contains an Entrance tile (i),
     * false otherwise.
     */
    public boolean isEntranceRoom() {
        return this.myIsEntranceRoom;
    }

    /**
     * @return True if the room contains an Exit tile (O),
     * false otherwise.
     */
    public boolean isExitRoom() {
        return this.myIsExitRoom;
    }

    /**
     * @return The width of the room.
     */
    public int getRoomWidth() {
        return (int) this.myRoomDimensions.getWidth();
    }

    /**
     * @return The height of the room.
     */
    public int getRoomHeight() {
        return (int) this.myRoomDimensions.getHeight();
    }

    public void deepCopyRoomData(final Tile[][] theOriginalRoomData) {
        myRoomData = new Tile[theOriginalRoomData.length][];
        for (int i = 0; i < theOriginalRoomData.length; i++) {
            myRoomData[i] = Arrays.copyOf(theOriginalRoomData[i],
                    theOriginalRoomData[i].length);

            for (int j = 0; j < theOriginalRoomData[i].length; j++) {
                if (theOriginalRoomData[i][j] != null) {
                    final char displayChar = theOriginalRoomData[i][j].getDisplayChar();
                    final Tile newTile;
                    if (theOriginalRoomData[i][j] instanceof ItemTile) {
                        final Item item = ((ItemTile) theOriginalRoomData[i][j]).getItem();
                        newTile = new ItemTile(item);
                    } else {
                        final boolean isTraversable =
                                theOriginalRoomData[i][j].isTraversable();
                        newTile = new Tile(displayChar, isTraversable);
                    }
                    myRoomData[i][j] = newTile;
                } else {
                    myRoomData[i][j] = null;
                }
            }
        }
    }
    public RoomMemento createMemento() {
        return new RoomMemento(myRoomData, myPlayerPosition, myPillar);
    }

    public RoomMemento saveToMemento() {
        return new RoomMemento(myRoomData, myPlayerPosition, myPillar);
    }

    public void restoreFromMemento(final RoomMemento theMemento) {
        myRoomData = theMemento.getSavedRoomData();
        myPlayerPosition = theMemento.getSavedPlayerPosition();
        myPillar = theMemento.getSavedPillar();
    }


    /**
     * Returns the players current X position in the room. If the player
     * isn't present, returns null;
     *
     * @return The players current X position in the room, or null.
     */
    public Integer getPlayerXPosition() {
        return this.myPlayerPosition == null
                ? null
                : Double.valueOf(this.myPlayerPosition.getX()).intValue();
    }

    /**
     * Returns the players current Y position in the room. If the player
     * isn't present, returns null;
     *
     * @return The players current Y position in the room, or null.
     */
    public Integer getPlayerYPosition() {
        return this.myPlayerPosition == null
                ? null
                : Double.valueOf(this.myPlayerPosition.getY()).intValue();
    }


    /**
     * @return The pillar contained in the room, or null.
     */
    public Item getPillar() {
        return this.myPillar;
    }

    /**
     * @return The tiles in the room.
     */
    public Tile[][] getRoomTiles() {
        return this.myRoomData;
    }


    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < myRoomData.length; i++) {
            String prefix = "";
            for (int j = 0; j < myRoomData[i].length; j++) {
                if (new Point(i, j).equals(myPlayerPosition)) {
                    stringBuilder.append(prefix).append("/"); // TODO CHANGE TO PLAYER CHARACTER
                } else {
                    stringBuilder.append(prefix).append(myRoomData[i][j].getDisplayChar());
                }
                prefix = " ";
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }


}
