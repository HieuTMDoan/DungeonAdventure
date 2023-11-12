package com.tcss.dungeonadventure;

import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.items.*;
import com.tcss.dungeonadventure.objects.monsters.Gremlin;
import com.tcss.dungeonadventure.objects.monsters.Monster;
import com.tcss.dungeonadventure.objects.monsters.Ogre;
import com.tcss.dungeonadventure.objects.monsters.Skeleton;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;


/**
 * This class contains static helper methods to use around the program.
 */
public class Helper {

    private static final Random RANDOM = new Random();

    /**
     * Contains all the class declaration of items that
     * can randomly generate in rooms.
     */
    private static final Class<?>[] ITEM_POOL =
            new Class[]{HealingPotion.class, VisionPotion.class};

    /**
     * Contains all the class declarations of the monsters
     * that can randomly generate in rooms.
     */
    private static final Class<?>[] MONSTER_POOL =
            new Class[]{Gremlin.class, Ogre.class, Skeleton.class};

    /**
     * Contains all the class declarations of all pillars.
     */
    private static final Class<?>[] PILLARS =
            new Class[]{
                PillarOfInheritance.class,
                PillarOfAbstraction.class,
                PillarOfPolymorphism.class,
                PillarOfEncapsulation.class};


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

    public static Item getRandomItem() {
        try {
            return (Item) ITEM_POOL[Helper.getRandomIntBetween(0, ITEM_POOL.length)].
                    getDeclaredConstructor().newInstance();
        } catch (final InstantiationException
                       | NoSuchMethodException
                       | IllegalAccessException
                       | InvocationTargetException e) {
            e.printStackTrace();


            throw new RuntimeException("Randomly generated item threw an exception.");
        }
    }

    /**
     * Returns a random door axis (horizontal or vertical).
     *
     * @return The random door axis.
     */
    public static Directions.Axis getRandomDoorAxis() {
        return getRandomBoolean() ? Directions.Axis.HORIZONTAL : Directions.Axis.VERTICAL;
    }

    /**
     * Returns a random boolean.
     *
     * @return The random boolean.
     */
    public static boolean getRandomBoolean() {
        return RANDOM.nextBoolean();
    }


    public static Monster getRandomMonster() {
        try {
            return (Monster) MONSTER_POOL[Helper.getRandomIntBetween(0, MONSTER_POOL.length)].
                    getDeclaredConstructor().newInstance();
        } catch (final InstantiationException
                       | NoSuchMethodException
                       | IllegalAccessException
                       | InvocationTargetException e) {
            e.printStackTrace();

            throw new RuntimeException("Randomly generated monster threw an exception.");
        }
    }

    public static Class<?>[] getPillarList() {
        return PILLARS;
    }


}
