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
import com.tcss.dungeonadventure.objects.tiles.NPCTile;
import com.tcss.dungeonadventure.objects.tiles.PitTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import com.tcss.dungeonadventure.objects.tiles.WallTile;
import java.awt.Dimension;
import java.awt.Point;
import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Represents a room in the dungeon.
 * A room is made up of tiles of multiple types
 * and contains monsters and items to pick and use.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class Room implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The maximum size of a room.
     */
    private static final Dimension MAX_ROOM_DIMENSION = new Dimension(10, 10);

    /**
     * The minimum size of a room.
     */
    private static final Dimension MIN_ROOM_DIMENSION = new Dimension(5, 5);

    /**
     * The chance for a room to contain one item.
     */
    private static final double ONE_ITEM_CHANCE = 0.15;

    /**
     * The chance for a room to contain two items.
     */
    private static final double TWO_ITEM_CHANCE = 0.05;

    /**
     * Chance for a room to have one pit.
     */
    private static final double ONE_PIT_CHANCE = 0.4;

    /**
     * Chance for a room to have two pits.
     */
    private static final double TWO_PIT_CHANCE = 0.2;

    /**
     * The chance for a room to contain one monster.
     */
    private static final double ONE_MONSTER_CHANCE = 0.35;

    /**
     * The chance for a room to contain two monsters.
     */
    private static final double TWO_MONSTER_CHANCE = 0.15;


    /**
     * The max amount of attempts to place a tile before abandoning.
     */
    private static final int MAX_TILE_PLACEMENT_ATTEMPTS = 50;

    /**
     * The percent of tiles that are transformed into walls.
     */
    private static final double EXTRA_WALL_RATIO = 0.10;

    /**
     * Boolean if the room is the exit room.
     */
    private final boolean myIsExitRoom;

    /**
     * Boolean if the room is the entrance room.
     */
    private final boolean myIsEntranceRoom;

    /**
     * The dimensions of the room.
     */
    private final Dimension myRoomDimensions;

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

    /**
     * Empty constructor to creating a non-essential room.
     */
    public Room() {
        myIsEntranceRoom = false;
        myIsExitRoom = false;
        myRoomDimensions = null;
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
                tiles[row][col]
                        = (row % (roomWidth - 1) == 0 || col % (roomHeight - 1) == 0)
                        ? new WallTile()
                        : new EmptyTile();
            }
        }

        // if the room is an exit or entrance, it shouldn't contain anything else.
        if (theIsEntrance || theIsExit) {
            putTileAtValidLocation(theIsEntrance ? new EntranceTile()
                    : new ExitTile(), tiles, false);
            return tiles;
        }

        if (thePillar != null) {
            try {
                final Item pillar = (Item) thePillar.getConstructor().newInstance();
                putTileAtValidLocation(new ItemTile(pillar), tiles, false);

                // Increase the chance of having monsters in rooms with pillars
                final double monsterRandom = Helper.getRandomDoubleBetween(0, 1);
                final int monsterNum = (monsterRandom < TWO_MONSTER_CHANCE)
                        ? 4  // Increased number of monsters around pillars
                        : (monsterRandom < ONE_MONSTER_CHANCE)
                        ? 3
                        : 2;
                for (int i = 0; i < monsterNum; i++) {
                    final Monster randomMonster = Helper.getRandomMonster();
                    putTileAtValidLocation(new NPCTile(randomMonster), tiles, true);
                }

            } catch (final InstantiationException
                           | NoSuchMethodException
                           | IllegalAccessException
                           | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if (theIsExit) {
            // Increase the chance of having more monsters in exit rooms
            final double monsterRandom = Helper.getRandomDoubleBetween(0, 1);
            final int monsterNum = (monsterRandom < TWO_MONSTER_CHANCE)
                    ? 3  // Adjust the number of monsters as needed
                    : (monsterRandom < ONE_MONSTER_CHANCE)
                    ? 2
                    : 1;
            for (int i = 0; i < monsterNum; i++) {
                final Monster randomMonster = Helper.getRandomMonster();
                putTileAtValidLocation(new NPCTile(randomMonster), tiles, true);
            }


            return tiles;
        }

        final double itemRandom = Helper.getRandomDoubleBetween(0, 1);
        final int itemNum = (itemRandom < TWO_ITEM_CHANCE)
                ? 2
                : (itemRandom < ONE_ITEM_CHANCE)
                ? 1
                : 0;
        for (int i = 0; i < itemNum; i++) {
            final Item randomItem = Helper.getRandomItem();
            putTileAtValidLocation(new ItemTile(randomItem), tiles, false);
        }

        final double monsterRandom = Helper.getRandomDoubleBetween(0, 1);
        final int monsterNum = (monsterRandom < TWO_MONSTER_CHANCE)
                ? 2
                : (monsterRandom < ONE_MONSTER_CHANCE)
                ? 1
                : 0;
        for (int i = 0; i < monsterNum; i++) {
            final Monster randomMonster = Helper.getRandomMonster();
            putTileAtValidLocation(new NPCTile(randomMonster), tiles, true);
        }
//        // Increase the overall number of monsters in the dungeon
//        final double totalMonsterRandom = Helper.getRandomDoubleBetween(0, 1);
//        final int totalMonsterNum = (totalMonsterRandom < TWO_MONSTER_CHANCE)
//                ? 5  // Adjust the total number of monsters as needed
//                : (totalMonsterRandom < ONE_MONSTER_CHANCE)
//                ? 4
//                : 3;
//        for (int i = 0; i < totalMonsterNum; i++) {
//            final Monster randomMonster = Helper.getRandomMonster();
//            putTileAtValidLocation(new NPCTile(randomMonster), tiles, true);
//        }

        final double pitRandom = Helper.getRandomDoubleBetween(0, 1);
        final int pitNum = (pitRandom < TWO_PIT_CHANCE)
                ? 2
                : (monsterRandom < ONE_PIT_CHANCE)
                ? 1
                : 0;
        for (int i = 0; i < pitNum; i++) {
            putTileAtValidLocation(new PitTile(), tiles, false);
        }


        return tiles;
    }


    /**
     * Helper method to use while generating a new Room.
     * Will ensure no overlap between tiles.
     *
     * @param theTile        The tile to add to the tile set.
     * @param theTiles       The current tile set.
     * @param theNextToDoors If the tile can exist next to a door.
     */
    private static void putTileAtValidLocation(final Tile theTile,
                                               final Tile[][] theTiles,
                                               final boolean theNextToDoors) {
        final Dimension size = new Dimension(theTiles.length, theTiles[0].length);

        int attempts = 0;
        while (true) {
            if (attempts > MAX_TILE_PLACEMENT_ATTEMPTS) {
                break;
            }

            attempts++;
            final int x = Helper.getRandomIntBetween(1, (int) (size.getWidth() - 1));
            final int y = Helper.getRandomIntBetween(1, (int) (size.getHeight() - 1));

            if (theTiles[x][y] != null && !(theTiles[x][y] instanceof EmptyTile)) {
                continue;
            }

            boolean foundMonster = false;
            if (theTile instanceof NPCTile) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        final Tile t = theTiles[x + i][y + j];
                        if (t instanceof NPCTile) {
                            foundMonster = true;
                            break;
                        }
                    }
                }
            }

            boolean foundDoor = false;
            if (!theNextToDoors) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        final Tile t = theTiles[x + i][y + j];
                        if (t instanceof DoorTile) {
                            foundDoor = true;
                            break;
                        }
                    }
                }
            }

            if (foundDoor || foundMonster) {
                continue;
            }

            theTiles[x][y] = theTile;
            break;
        }
    }

    /**
     * Adds extra randomized walls to a room.
     *
     * @param theRoom the room to be added with extra walls
     */
    public static void addExtraWalls(final Room theRoom) {

        final int extraWalls = (int) ((theRoom.getRoomWidth() - 1)
                * (theRoom.getRoomHeight() - 1) * EXTRA_WALL_RATIO);
        for (int i = 0; i < extraWalls; i++) {
            putTileAtValidLocation(new WallTile(), theRoom.getRoomTiles(), false);
        }
    }

    /**
     * Adds a door to the wall in a room
     * in the given {@link Directions.Cardinal Cardinal}.
     *
     * @param theWallLocation the direction for door to be added
     * @param theOtherRoom    the room whose wall is to be added with
     */
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
        Point tempPoint = new Point(myPlayerPosition);
        switch (theDirection) {
            case NORTH -> tempPoint.translate(-1, 0);
            case SOUTH -> tempPoint.translate(1, 0);
            case EAST -> tempPoint.translate(0, 1);
            case WEST -> tempPoint.translate(0, -1);
            default -> throw new IllegalArgumentException(
                    "Illegal enum passed: " + theDirection);
        }

        Tile tile;
        try {
            tile = myRoomTiles[(int) tempPoint.getX()][(int) tempPoint.getY()];
        } catch (final ArrayIndexOutOfBoundsException e) {
            // If this is reached, that means the player is in a doorway and attempted to move
            // in the direction they came in. Doesn't change their location,
            // just interacts with the tile directly under them again.

            tempPoint = new Point(myPlayerPosition);
            tile = myRoomTiles[(int) tempPoint.getX()][(int) tempPoint.getY()];
        }

        if (tile.isTraversable()) {
            myPlayerPosition = tempPoint;
            tile.onInteract(DungeonAdventure.getInstance().getPlayer());
        }
    }

    /**
     * Returns the location of a door on the wall in the given
     * {@link Directions.Cardinal Cardinal}.
     *
     * @param theDirection the {@link Directions.Cardinal Cardinal}
     *                     of the door to look for
     * @return The location of the door on the wall in the given
     * {@link Directions.Cardinal Cardinal}.
     */
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
     * Sets the player's location based on the specified original direction.
     * If the original direction is null, the player's location is set to null.
     * Otherwise, the player's position is determined by finding the door on the opposite wall
     * of the specified original direction.
     *
     * @param theOriginalDirection The original direction from which the player is moving.
     *                             If null, the player's location is set to null.
     */
    public void setPlayerLocation(final Directions.Cardinal theOriginalDirection) {
        if (theOriginalDirection == null) {
            setPlayerLocation((Point) null);
            return;
        }
        myPlayerPosition = findDoorOnWall(theOriginalDirection.getOpposite());

        if (myPlayerPosition == null) {
            System.out.println("WARNING: Could not find door on wall: "
                    + theOriginalDirection.getOpposite());

            System.out.println(this);
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

    /**
     * Returns the adjacent room by the given {@link Directions.Cardinal}.
     *
     * @param theDirection the given {@link Directions.Cardinal}.
     * @return The adjacent room by the given {@link Directions.Cardinal}.
     */
    public Room getAdjacentRoomByDirection(final Directions.Cardinal theDirection) {
        final Dungeon dungeon = DungeonAdventure.getInstance().getDungeon();
        final int x = (int) this.getDungeonLocation().getX();
        final int y = (int) this.getDungeonLocation().getY();

        return dungeon.getRoomAt(x + theDirection.getXOffset(), y + theDirection.getYOffset());
    }

    /**
     * Returns the adjacent room by the given {@link Directions.Diagonal}.
     *
     * @param theDirection the given {@link Directions.Cardinal}.
     * @return The adjacent room by the given {@link Directions.Diagonal}.
     */
    public Room getAdjacentRoomByDirection(final Directions.Diagonal theDirection) {
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
     * Returns a string representation of the current state of the room,
     * including the player's position.
     * <p>
     * The room is represented as a grid of tiles, and the player's position is
     * marked with a specific character.
     *
     * @return A string representation of the room, with the player's position marked.
     */
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
