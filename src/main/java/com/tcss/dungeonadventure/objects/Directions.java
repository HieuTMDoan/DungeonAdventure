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
        NORTH(-1, 0),

        /**
         * Represents East.
         */
        EAST(0, 1),

        /**
         * Represents South.
         */
        SOUTH(1, 0),

        /**
         * Represents West.
         */
        WEST(0, -1);

        /**
         * The usual X offset.
         */
        private final int myXOffset;

        /**
         * The usual Y offset.
         */
        private final int myYOffset;

        Cardinal(final int theXOffset, final int theYOffset) {
            this.myXOffset = theXOffset;
            this.myYOffset = theYOffset;
        }

        /**
         * @return The X offset.
         */
        public int getXOffset() {
            return this.myXOffset;
        }

        /**
         * @return The Y offset.
         */
        public int getYOffset() {
            return this.myYOffset;
        }

        /**
         * Gets the direction opposite to a direction.
         * <p>
         * For example, if Cardinal.NORTH.getOpposite() is called,
         * it will return Cardinal.SOUTH.
         *
         * @return The opposite direction.
         */
        public Cardinal getOpposite() {
            int index = ordinal() + 2;
            if (index > 3) {
                index -= 4;
            }

            return values()[index];
        }
    }


}
