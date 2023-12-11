package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.items.Item;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    @Serial
    private void writeObject(final ObjectOutputStream theOut) throws IOException {
        theOut.defaultWriteObject();
        // Add additional code to handle non-serializable fields, if any
    }

    @Serial
    private void readObject(final ObjectInputStream theIn) throws IOException, ClassNotFoundException {
        theIn.defaultReadObject();
        // Add additional code to handle non-serializable fields, if any
    }


    public Player(final String thePlayerName, final Hero thePlayerHero) {
        this.myPlayerName = thePlayerName;
        this.myPlayerHero = thePlayerHero;
        Player.Stats.resetAll();
    }

    public String getPlayerName() {
        return this.myPlayerName;
    }

    public Hero getPlayerHero() {
        return this.myPlayerHero;
    }

    public Map<Item, Integer> getInventory() {
        return this.myInventory;
    }

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

    public enum Stats {
        MOVES,
        MISSED_ATTACKS,
        DAMAGE_DEALT,
        MONSTERS_ENCOUNTERED,
        MONSTERS_DEFEATED,
        ITEMS_USED,
        ITEMS_COLLECTED;

        int myCounter;

        public static void increaseCounter(final Stats theStat) {
            theStat.myCounter++;
        }

        public static void increaseCounter(final Stats theStat, final int theAmount) {
            theStat.myCounter += theAmount;
        }

        static void resetAll() {
            for (final Stats s : values()) {
                s.myCounter = 0;
            }
        }

        public int getCounter() {
            return this.myCounter;
        }
    }


}
