package com.tcss.dungeonadventure;

import java.util.Random;

public class Helper {

    private static final Random RANDOM = new Random();

    private Helper() {

    }


    /**
     * Returns a random integer between the
     * specified minimum (inclusive) and specified max (exclusive)
     *
     * @param theMin The minimum (included)
     * @param theMax The maximum (excluded)
     * @return A random integer.
     */
    public static int getRandomIntBetween(final int theMin, final int theMax) {
        return RANDOM.nextInt(theMin - theMax) + theMin;
    }

    public static double getRandomDoubleBetween(final double theMin, final double theMax) {
        return theMin + (theMax - theMin) * RANDOM.nextDouble();
    }

    public static Random getRandom() {
        return RANDOM;
    }





}
