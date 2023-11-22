package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.objects.items.Item;
import com.tcss.dungeonadventure.objects.monsters.Monster;
import com.tcss.dungeonadventure.objects.tiles.DoorTile;
import com.tcss.dungeonadventure.objects.tiles.EmptyTile;
import com.tcss.dungeonadventure.objects.tiles.EntranceTile;
import com.tcss.dungeonadventure.objects.tiles.ExitTile;
import com.tcss.dungeonadventure.objects.tiles.ItemTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import com.tcss.dungeonadventure.objects.tiles.WallTile;

import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


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
     * The number of doors in the room.
     */
    private int myDoorNumber;

    /**
     * The pillar that this room contains. May be null.
     */
    private Item myPillar;

    /**
     * The tiles in the room.
     */
    private Tile[][] myRoomTiles;

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
        this.myRoomTiles = theTiles;
        this.myIsEntranceRoom = contains(TileChars.Room.ENTRANCE);
        this.myIsExitRoom = contains(TileChars.Room.EXIT);
        this.myRoomDimensions = new Dimension(theTiles[0].length, theTiles.length);

        myPillar = Arrays.stream(myRoomTiles).
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

    public Room() {
        myIsEntranceRoom = false;
        myIsExitRoom = false;
        myRoomDimensions = null;
    }


    /**
     * Copy constructor for creating a deep copy of the Room.
     *
     * @param theOriginalRoom The Room to copy.
     */
    public Room(final Room theOriginalRoom) {
        myDoorNumber = theOriginalRoom.myDoorNumber;
        myIsEntranceRoom = theOriginalRoom.myIsEntranceRoom;
        myIsExitRoom = theOriginalRoom.myIsExitRoom;
        myDungeonLocation = theOriginalRoom.myDungeonLocation;
        myRoomDimensions = new Dimension(theOriginalRoom.myRoomDimensions);
        myPillar = (theOriginalRoom.myPillar != null) ? theOriginalRoom.myPillar.copy() : null;
        myPlayerPosition =
                (theOriginalRoom.myPlayerPosition != null)
                        ? new Point(theOriginalRoom.myPlayerPosition)
                        : null;
        myDungeonLocation =
                (theOriginalRoom.myDungeonLocation != null)
                        ? new Point(theOriginalRoom.myDungeonLocation)
                        : null;
        deepCopyRoomData(theOriginalRoom.myRoomTiles);
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

        if (thePillar != null && !Arrays.asList(Helper.getPillarList()).contains(thePillar)) {
            throw new IllegalArgumentException(
                    thePillar.getSimpleName() + " is not a pillar.");
        }


        final int roomWidth = Helper.getRandomIntBetween(
                (int) MIN_ROOM_DIMENSION.getWidth(),
                (int) MAX_ROOM_DIMENSION.getWidth() + 1);

        final int roomHeight = Helper.getRandomIntBetween(
                (int) MIN_ROOM_DIMENSION.getHeight(),
                (int) MAX_ROOM_DIMENSION.getHeight() + 1);


        final Tile[][] tiles = new Tile[roomWidth][roomHeight];

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                if (row % (roomWidth - 1) == 0 || col % (roomHeight - 1) == 0) {
                    tiles[row][col] = new WallTile();
                } else {
                    tiles[row][col] = new EmptyTile();
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
//            putTileAtValidLocation(new NPCTile(randomMonster), tiles);
        }

        return tiles;
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

        final Dimension size = new Dimension(theTiles.length, theTiles[0].length);

        while (true) {
            final int x = Helper.getRandomIntBetween(1, (int) (size.getWidth() - 1));
            final int y = Helper.getRandomIntBetween(1, (int) (size.getHeight() - 1));

            if (theTiles[x][y] != null && theTiles[x][y].getClass() != EmptyTile.class) {
                continue;
            }
            theTiles[x][y] = theTile;
            return;
        }
    }

    public void addDoorToWall(final Directions.Cardinal theWallLocation,
                              final Room theOtherRoom) {

        if (findDoorOnWall(theWallLocation) != null) {
            return; // don't add multiple doors on the same wall
        }

        final int x = Helper.getRandomIntBetween(1, getRoomHeight() - 1);
        final int y = Helper.getRandomIntBetween(1, getRoomWidth() - 1);


        switch (theWallLocation) {
            case NORTH -> // door is on top wall
                    this.myRoomTiles[0][y]
                            = new DoorTile(Directions.Cardinal.NORTH, theOtherRoom);
            case SOUTH -> // door is on bottom wall
                    this.myRoomTiles[getRoomHeight() - 1][y]
                            = new DoorTile(Directions.Cardinal.SOUTH, theOtherRoom);
            case EAST -> // door is on right wall
                    this.myRoomTiles[x][getRoomWidth() - 1]
                            = new DoorTile(Directions.Cardinal.EAST, theOtherRoom);
            case WEST -> // door is on left wall
                    this.myRoomTiles[x][0]
                            = new DoorTile(Directions.Cardinal.WEST, theOtherRoom);
            default -> {
            }
        }

    }


    /**
     * Checks if a specific character exists in the tile set.
     *
     * @param theChar The character to look for. Pull this from {@link TileChars}
     * @return True if the character is in the room, false otherwise.
     */
    public boolean contains(final char theChar) {
        for (final Tile[] row : myRoomTiles) {
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
            this.myPlayerPosition = new Point(1, 1);
            //TODO: Change this to where the player enters the room
        }

        final Point tempPoint = new Point(myPlayerPosition);
        switch (theDirection) {
            case NORTH -> tempPoint.translate(-1, 0);
            case SOUTH -> tempPoint.translate(1, 0);
            case EAST -> tempPoint.translate(0, 1);
            case WEST -> tempPoint.translate(0, -1);
            default -> throw new IllegalArgumentException(
                    "Illegal enum passed: " + theDirection);
        }

        final Tile tile = myRoomTiles[(int) tempPoint.getX()][(int) tempPoint.getY()];
        if (tile.isTraversable()) {
            myPlayerPosition = tempPoint;
            tile.onInteract();
        }
    }

    public Point findDoorOnWall(final Directions.Cardinal theDirection) {
        final Tile[][] tiles = this.getRoomTiles();

        Point returnPoint = null;


        switch (theDirection) {
            case NORTH -> { // find door on top wall
                for (int i = 0; i < myRoomTiles[0].length; i++) {
                    if (myRoomTiles[0][i].getClass() == DoorTile.class) {
                        returnPoint = new Point(0, i);
                    }
                }

            }
            case SOUTH -> { //find door on bottom wall
                for (int i = 0; i < myRoomTiles[this.getRoomHeight() - 1].length; i++) {
                    if (myRoomTiles[this.getRoomHeight() - 1][i].
                            getClass() == DoorTile.class) {

                        returnPoint = new Point(this.getRoomHeight() - 1, i);
                    }
                }
            }
            case EAST -> { // find door on right wall
                for (int i = 0; i < tiles.length; i++) {
                    if (tiles[i][getRoomWidth() - 1].getClass() == DoorTile.class) {
                        returnPoint = new Point(i, this.getRoomWidth() - 1);
                    }
                }

            }
            case WEST -> { // find door on right wall
                for (int i = 0; i < tiles.length; i++) {
                    if (tiles[i][0].getClass() == DoorTile.class) {
                        returnPoint = new Point(i, 0);
                    }
                }
            }
            default -> {
            }
        }
        return returnPoint;
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
        myPlayerPosition = findDoorOnWall(theOriginalDirection.getOpposite());
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

        return dungeon.getRoomAt(x + theDirection.getXOffset(), y + theDirection.getYOffset());

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
    public Integer getRoomWidth() {
        return this.myRoomDimensions == null ? null : (int) this.myRoomDimensions.getWidth();

    }

    /**
     * @return The height of the room.
     */
    public Integer getRoomHeight() {
        return this.myRoomDimensions == null ? null : (int) this.myRoomDimensions.getHeight();
    }


    public void deepCopyRoomData(final Tile[][] theOriginalRoomData) {
        myRoomTiles = new Tile[theOriginalRoomData.length][];
        for (int i = 0; i < theOriginalRoomData.length; i++) {
            myRoomTiles[i] = Arrays.copyOf(theOriginalRoomData[i],
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
                    myRoomTiles[i][j] = newTile;
                } else {
                    myRoomTiles[i][j] = null;
                }
            }
        }
    }

    public RoomMemento createMemento() {
        return new RoomMemento(myRoomTiles, myPlayerPosition, myPillar);
    }

    public RoomMemento saveToMemento() {
        return new RoomMemento(myRoomTiles, myPlayerPosition, myPillar);
    }

    public void restoreFromMemento(final RoomMemento theMemento) {
        myRoomTiles = theMemento.getSavedRoomData();
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
        return this.myRoomTiles;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < myRoomTiles.length; i++) {
            String prefix = "";
            for (int j = 0; j < myRoomTiles[i].length; j++) {
                if (new Point(i, j).equals(myPlayerPosition)) {
                    stringBuilder.append(prefix).append("/"); //TODO CHANGE TO PLAYER CHARACTER
                } else {
                    stringBuilder.append(prefix).append(myRoomTiles[i][j].getDisplayChar());
                }
                prefix = " ";
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
