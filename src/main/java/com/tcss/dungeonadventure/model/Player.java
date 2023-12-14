package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
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
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The Hero of the player.
     */
    private final Hero myPlayerHero;

    /**
     * The name of the player.
     */
    private final String myPlayerName;

    /**
     * If the hero is invincible or not.
     */
    private boolean myInvincible;

    /**
     * The inventory of the player.
     */
    private final Map<Item, Integer> myInventory = new HashMap<>();


    /**
     * The number of steps the player has made.
     */
    private int myMoves;

    /**
     * The number of attacks missed during combat.
     */
    private int myMissedAttacks;
    /**
     * The amount of damage dealt.
     */
    private int myDamageDealt;

    /**
     * The number of items used.
     */
    private int myItemsUsed;

    /**
     * The number of items collected.
     */
    private int myItemsCollected;

    /**
     * The number of slain monsters.
     */
    private int myMonstersDefeated;


    public enum Fields {

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
        ITEMS_COLLECTED
    }


    /**
     * Creates a new player with the specified {@link Hero} type and name.
     *
     * @param thePlayerName the player's name
     * @param thePlayerHero the {@link Hero} type
     */
    public Player(final String thePlayerName, final Hero thePlayerHero) {
        this.myPlayerName = thePlayerName;
        this.myPlayerHero = thePlayerHero;
        resetAllStats();
    }

    /**
     * Increments the stat fields by 1.
     *
     * @param theField The Field to increase.
     */
    public void increaseStat(final Fields theField) {
        increaseStat(theField, 1);
    }

    /**
     * Increase a stat field by a specified amount.
     *
     * @param theField The Field to increase.
     * @param theAmount The amount to increase it by.
     */
    public void increaseStat(final Fields theField, final int theAmount) {
        switch (theField) {
            case MOVES -> myMoves += theAmount;
            case MISSED_ATTACKS -> myMissedAttacks += theAmount;
            case DAMAGE_DEALT -> myDamageDealt += theAmount;
            case MONSTERS_DEFEATED -> myMonstersDefeated += theAmount;
            case ITEMS_USED -> myItemsUsed += theAmount;
            case ITEMS_COLLECTED -> myItemsCollected += theAmount;
            default -> throw new IllegalStateException("Unexpected value: " + theField);
        }
    }

    /**
     * Returns the counter of each stat.
     *
     * @param theField The field to get.
     * @return The counter of the stat.
     */
    public int getStat(final Fields theField) {
        return switch (theField) {
            case MOVES -> myMoves;
            case MISSED_ATTACKS -> myMissedAttacks;
            case DAMAGE_DEALT -> myDamageDealt;
            case MONSTERS_DEFEATED -> myMonstersDefeated;
            case ITEMS_USED -> myItemsUsed;
            case ITEMS_COLLECTED -> myItemsCollected;
        };
    }

    public void resetAllStats() {
        myMoves = 0;
        myMissedAttacks = 0;
        myDamageDealt = 0;
        myMonstersDefeated = 0;
        myItemsUsed = 0;
        myItemsCollected = 0;
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
     * Adds an {@link Item} to the inventory.
     *
     * @param theItem the {@link Item} to be added
     */
    public void addItemToInventory(final Item theItem) {
        increaseStat(Fields.ITEMS_COLLECTED);
        Integer itemCount = this.myInventory.get(theItem);
        if (itemCount == null) {
            itemCount = 0;
        }
        this.myInventory.put(theItem, itemCount + 1);
        PCS.firePropertyChanged(PCS.ITEMS_CHANGED, myInventory);
        PCS.firePropertyChanged(PCS.LOG, "Picked up a "
                + Helper.camelToSpaced(theItem.getClass().getSimpleName()));
    }

    /**
     * Removes an {@link Item} to the inventory.
     *
     * @param theItem the {@link Item} to be removed
     */
    public void removeItemFromInventory(final Item theItem) {
        increaseStat(Fields.ITEMS_USED);

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

    public boolean isInvincible() {
        return myInvincible;
    }

    public void setInvincible(final boolean theInvincible) {
        this.myInvincible = theInvincible;
    }

    /**
     * Returns the current player's inventory.
     *
     * @return The current player's inventory.
     */
    public Map<Item, Integer> getInventory() {
        return this.myInventory;
    }


    public boolean containsItem(final Item theItem) {
        return myInventory.containsKey(theItem) && myInventory.get(theItem) > 0;
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


}
