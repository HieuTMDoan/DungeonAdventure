package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.items.Item;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String myPlayerName;

    private Hero myPlayerHero;

    private final Map<Item, Integer> myInventory = new HashMap<>();

    public Player(final String thePlayerName, final Hero thePlayerHero) {
        this.myPlayerName = thePlayerName;
        this.myPlayerHero = thePlayerHero;
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
        Integer itemCount = this.myInventory.get(theItem);
        if (itemCount == null) {
            itemCount = 0;
        }
        this.myInventory.put(theItem, itemCount + 1);
        PCS.firePropertyChanged(PCS.ITEMS_CHANGED, myInventory);
        PCS.firePropertyChanged(PCS.LOG, "Picked up " + theItem.getClass().getSimpleName());
    }

    public void removeItemFromInventory(final Item theItem) {
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

}
