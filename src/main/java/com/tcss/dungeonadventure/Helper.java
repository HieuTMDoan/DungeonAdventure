package com.tcss.dungeonadventure;

import java.util.Random;


/**
 * This class contains static helper methods to use around the program.
 */
public class Helper {

    private static final Random RANDOM = new Random();

    private Helper() {

    }


    /**
     * Returns a random integer between the
     * specified minimum (inclusive) and specified max (exclusive).
     *
     * @param theMin The minimum (included)
     * @param theMax The maximum (excluded)
     * @return The random integer.
     */
    public static int getRandomIntBetween(final int theMin, final int theMax) {
        return RANDOM.nextInt(theMax - theMin) + theMin;
    }

    /**
     * Returns a random integer between the
     * specified minimum (inclusive) and specified max (exclusive).
     *
     * @param theMin The minimum (included)
     * @param theMax The maximum (excluded)
     * @return The random double.
     */
    public static double getRandomDoubleBetween(final double theMin, final double theMax) {
        return theMin + (theMax - theMin) * RANDOM.nextDouble();
    }

    public static Random getRandom() {
        return RANDOM;
    }





}
