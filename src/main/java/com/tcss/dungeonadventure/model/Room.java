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
import com.tcss.dungeonadventure.objects.tiles.EmptyTile;
import com.tcss.dungeonadventure.objects.tiles.WallTile;
import com.tcss.dungeonadventure.objects.tiles.ExitTile;
import com.tcss.dungeonadventure.objects.tiles.NPCTile;
import com.tcss.dungeonadventure.objects.tiles.EntranceTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import com.tcss.dungeonadventure.objects.tiles.ItemTile;


import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;


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
     * The tiles in the room.
     */
    private final Tile[][] myRoomData;


    /**
     * The pillar that this room contains. May be null.
     */
    private Item myPillar;


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

        loop:
        for (final Tile[] row : myRoomData) {
            for (final Tile tile : row) {
                if (tile.getClass() != ItemTile.class) {
                    continue;
                }
                final Item item = ((ItemTile) tile).getItem();
                if (item.getItemType() == Item.ItemTypes.PILLAR) {
                    myPillar = item;
                    break loop;
                }
            }
        }

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


    public void movePlayer(final Directions.Cardinal theDirection) {
        if (this.myPlayerPosition == null) {
            this.myPlayerPosition = new Point(1, 1); // TODO: Change this to where the player enters the room
        }


        switch (theDirection) {
            case NORTH -> myPlayerPosition.translate(0, 1); // TODO: Check for proper bounds before moving
            case EAST -> myPlayerPosition.translate(1, 0);
            case SOUTH -> myPlayerPosition.translate(0, -1);
            case WEST -> myPlayerPosition.translate(-1, 0);
            default -> throw new IllegalArgumentException(
                    "Illegal enum passed: " + theDirection);
        }

        PCS.firePropertyChanged(PCS.UPDATED_PLAYER_LOCATION, new Point(myPlayerPosition));
    }

    public void loadPlayerToEntrance() {
        if (!this.myIsEntranceRoom) {
            throw new IllegalArgumentException("Cannot load player to non-entrance room.");
        }

        for (int y = 0; y < myRoomData.length; y++) {
            final Tile[] row = myRoomData[y];
            for (int x = 0; x < row.length; x++) {
                if (row[x].getClass() != EntranceTile.class) {
                    myPlayerPosition = new Point(y, x);
                    System.out.println("Player is at: " + myPlayerPosition);
                    PCS.firePropertyChanged(PCS.LOAD_ROOM, this);
                    return;
                }

            }
        }
        throw new IllegalArgumentException("Player was not loaded into entrance.");


    }


    public boolean isEntranceRoom() {
        return this.myIsEntranceRoom;
    }

    public boolean isExitRoom() {
        return this.myIsExitRoom;
    }

    public int getRoomWidth() {
        return (int) this.myRoomDimensions.getWidth();
    }

    public int getRoomHeight() {
        return (int) this.myRoomDimensions.getHeight();
    }

    public Integer getPlayerXPosition() {
        return this.myPlayerPosition == null
                ? null
                : Double.valueOf(this.myPlayerPosition.getX()).intValue();
    }

    public Integer getPlayerYPosition() {
        return this.myPlayerPosition == null
                ? null
                : Double.valueOf(this.myPlayerPosition.getY()).intValue();
    }

    public Room[][] getSurroundingRooms() {
        return null;
    }

    public Item getPillar() {
        return this.myPillar;
    }

    public Tile[][] getRoomTiles() {
        return this.myRoomData;
    }


    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < myRoomData.length; i++) {
            final Tile[] row = myRoomData[i];
            String prefix = "";
            for (int j = 0; j < row.length; j++) {
                if (new Point(i, j).equals(myPlayerPosition)) {
                    stringBuilder.append(prefix).append("/");// TODO CHANGE TO PLAYER CHARACTER
                } else {
                    final Tile tile = row[j];
                    stringBuilder.append(prefix).append(tile.getDisplayChar());
                }
                prefix = " ";
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
