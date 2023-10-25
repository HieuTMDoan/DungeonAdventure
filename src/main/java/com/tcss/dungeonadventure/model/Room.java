package com.tcss.dungeonadventure.model;



import com.tcss.dungeonadventure.objects.Directions;

import java.awt.Dimension;
import java.awt.Point;

public class Room {


    private final boolean myIsEntranceRoom;
    private final boolean myIsExitRoom;
    private final Dimension myRoomDimensions;
    private final Point myRoomLocation;

    private Point myPlayerPosition;

//    private final Tile[][] myRoomData;


    public Room(final int theRoomX,
                final int theRoomY,
                final int theRoomWidth,
                final int theRoomHeight,
                final boolean theIsEntranceRoom,
                final boolean theIsExitRoom) {

        this.myIsEntranceRoom = theIsEntranceRoom;
        this.myIsExitRoom = theIsExitRoom;
        this.myRoomDimensions = new Dimension(theRoomWidth, theRoomHeight);
        this.myRoomLocation = new Point(theRoomX, theRoomY);
        this.myPlayerPosition = null;


    }


    public void movePlayer(final Directions.Cardinal theDirection) {
        if (this.myPlayerPosition == null) {
            this.myPlayerPosition = new Point(0, 0); // TODO: Change this to where the player enters the room
        }


        switch (theDirection) {
            case NORTH -> myPlayerPosition.translate(0, 1); // TODO: Check for proper bounds before moving
            case EAST -> myPlayerPosition.translate(1, 0);
            case SOUTH -> myPlayerPosition.translate(0, -1);
            case WEST -> myPlayerPosition.translate(-1, 0);
            default -> throw new IllegalArgumentException(
                    "Illegal enum passed: " + theDirection);
        }
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

    public int getRoomXPosition() {
        return (int) this.myRoomLocation.getX();
    }

    public int getRoomYPosition() {
        return (int) this.myRoomLocation.getY();
    }

    public Room[][] getSurroundingRooms() {
        return null;
    }


}
