package com.tcss.dungeonadventure.objects;


/**
 * Class to contain enum constants about possible directions
 * e.g. door orientation, which can only be horizontal or vertical, or
 * e.g. player movement, which can be North, East, South, or West.
 *
 * @author Aaron Burnham
 * @author Hieu Doan
 * @author Sunny Ali
 * @version Fall 2023
 */
public class Directions {


    /**
     * Enum to represent different axes.
     */
    public enum Axis {
        /**
         * Represents the horizontal axis.
         */
        HORIZONTAL,

        /**
         * Represents the vertical axes.
         */
        VERTICAL
    }

    public enum Cardinal {
        /**
         * Represents North.
         */
        NORTH,

        /**
         * Represents East.
         */
        EAST,

        /**
         * Represents South.
         */
        SOUTH,

        /**
         * Represents West.
         */
        WEST
    }


}
