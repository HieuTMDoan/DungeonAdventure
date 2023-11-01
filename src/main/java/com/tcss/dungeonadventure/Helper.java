package com.tcss.dungeonadventure;

import com.tcss.dungeonadventure.objects.items.HealingPotion;
import com.tcss.dungeonadventure.objects.items.Item;
import com.tcss.dungeonadventure.objects.items.VisionPotion;
import com.tcss.dungeonadventure.objects.monsters.Gremlin;
import com.tcss.dungeonadventure.objects.monsters.Monster;
import com.tcss.dungeonadventure.objects.monsters.Ogre;
import com.tcss.dungeonadventure.objects.monsters.Skeleton;

import java.util.Random;


/**
 * This class contains static helper methods to use around the program.
 */
public class Helper {

    private static final Random RANDOM = new Random();

    private static final Item[] ITEM_POOL =
            new Item[]{new HealingPotion(), new VisionPotion()};

    private static final Monster[] MONSTER_POOL =
            new Monster[]{new Gremlin(), new Ogre(), new Skeleton()};


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
            return ITEM_POOL[Helper.getRandomIntBetween(0, ITEM_POOL.length)].
                    getClass().getDeclaredConstructor().newInstance();
        } catch (final Exception e) {
            return null;
        }
    }

    public static Monster getRandomMonster() {
        try {
            return MONSTER_POOL[Helper.getRandomIntBetween(0, MONSTER_POOL.length)].
                    getClass().getDeclaredConstructor().newInstance();
        } catch (final Exception e) {
            return null;
        }
    }




}
