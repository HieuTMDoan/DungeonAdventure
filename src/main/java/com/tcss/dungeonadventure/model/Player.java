package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.items.Item;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents the player in the dungeon.
 * The player has the ability to pick up items in dungeon,
 * engage in combat with a monster, and use items from the inventory.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The name of the player.
     */
    private final String myPlayerName;

    /**
     * The Hero of the player.
     */
    private final Hero myPlayerHero;

    /**
     * The inventory of the player.
     */
    private final Map<Item, Integer> myInventory = new HashMap<>();

    /**
     * Creates a new player with the specified {@link Hero} type and name.
     *
     * @param thePlayerName the player's name
     * @param thePlayerHero the {@link Hero} type
     */
    public Player(final String thePlayerName, final Hero thePlayerHero) {
        this.myPlayerName = thePlayerName;
        this.myPlayerHero = thePlayerHero;
        Player.Stats.resetAll();
    }

    /**
     * Returns the current player's name.
     *
     * @return The current player's name.
     */
    public String getPlayerName() {
        return this.myPlayerName;
    }

    /**
     * Returns the current player's {@link Hero} type.
     *
     * @return The current player's {@link Hero} type.
     */
    public Hero getPlayerHero() {
        return this.myPlayerHero;
    }

    /**
     * Returns the current player's inventory.
     *
     * @return The current player's inventory.
     */
    public Map<Item, Integer> getInventory() {
        return this.myInventory;
    }

    /**
     * Adds an {@link Item} to the inventory.
     *
     * @param theItem the {@link Item} to be added
     */
    public void addItemToInventory(final Item theItem) {
        Stats.increaseCounter(Stats.ITEMS_COLLECTED);
        Integer itemCount = this.myInventory.get(theItem);
        if (itemCount == null) {
            itemCount = 0;
        }
        this.myInventory.put(theItem, itemCount + 1);
        PCS.firePropertyChanged(PCS.ITEMS_CHANGED, myInventory);
        PCS.firePropertyChanged(PCS.LOG, "Picked up " + theItem.getClass().getSimpleName());
    }


    public boolean containsItem(final Item theItem) {
        return myInventory.containsKey(theItem) && myInventory.get(theItem) > 0;
    }

    /**
     * Removes an {@link Item} to the inventory.
     *
     * @param theItem the {@link Item} to be removed
     */
    public void removeItemFromInventory(final Item theItem) {
        Stats.increaseCounter(Stats.ITEMS_USED);

        final Integer itemCount = this.myInventory.get(theItem);
        if (itemCount == null) {
            // Doesn't exist in inventory, do nothing
            return;
        }
        if (itemCount == 1) {
            // If there is only one item left, remove from inventory
            this.myInventory.remove(theItem);
        } else {
            this.myInventory.put(theItem, itemCount - 1);
        }
        PCS.firePropertyChanged(PCS.ITEMS_CHANGED, myInventory);

    }

    /**
     * Returns true if the player has 4 pillar {@link Item items}.
     *
     * @return True if the player has 4 pillar {@link Item items}.
     */
    public boolean hasAllPillars() {
        final Set<Item> pillarSet = new HashSet<>();
        for (final Item item : myInventory.keySet()) {
            if (item.getItemType() != Item.ItemTypes.PILLAR) {
                continue;
            }
            final Integer count = myInventory.get(item);
            if (count == null || count == 0) {
                continue;
            }

            pillarSet.add(item);
        }
        return pillarSet.size() == 4;
    }

    /**
     * Enumeration of stats that a player can have.
     */
    public enum Stats {

        /**
         * The number of steps the player has made.
         */
        MOVES,

        /**
         * The number of attacks missed during combat.
         */
        MISSED_ATTACKS,

        /**
         * The amount of damage dealt.
         */
        DAMAGE_DEALT,

        /**
         * The number of monsters encountered. //TODO
         */
        MONSTERS_ENCOUNTERED,

        /**
         * The number of slain monsters.
         */
        MONSTERS_DEFEATED,

        /**
         * The number of items used.
         */
        ITEMS_USED,

        /**
         * The number of items collected.
         */
        ITEMS_COLLECTED;

        /**
         * The counter of the {@link Stats Stats}.
         */
        private int myCounter;

        /**
         * Increases the counter of a {@link Stats Stats} by 1.
         *
         * @param theStat the {@link Stats Stats} to be counted
         */
        public static void increaseCounter(final Stats theStat) {
            theStat.myCounter++;
        }

        /**
         * Increases the counter of a {@link Stats Stats} by the set amount.
         *
         * @param theStat the {@link Stats Stats} to be counted
         * @param theAmount the amount to be added
         */
        public static void increaseCounter(final Stats theStat, final int theAmount) {
            theStat.myCounter += theAmount;
        }

        /**
         * Resets all {@link Stats Stats}'s counters.
         */
        static void resetAll() {
            for (final Stats s : values()) {
                s.myCounter = 0;
            }
        }

        /**
         * Returns the current counter of a {@link Stats Stats}.
         *
         * @return The current counter of a {@link Stats Stats}.
         */
        public int getCounter() {
            return this.myCounter;
        }
    }


}
