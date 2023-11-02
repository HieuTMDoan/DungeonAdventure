package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.objects.items.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemTile extends Tile {

    /**
     * The item(s) occupying this tile.
     */
    private final List<Item> myStoredItems = new ArrayList<>();


    /**
     * Constructor for multiple items on the same tile, IF this
     * is implemented
     *
     * @param theItems Items stored in this tile
     */
    public ItemTile(final Item... theItems) {
        super(theItems.length == 1
                        ? theItems[0].getDisplayChar()
                        : TileChars.Items.MULTIPLE,
                true);

        this.myStoredItems.addAll(List.of(theItems));
    }

    public List<Item> getItems() {
        return this.myStoredItems;
    }

    @Override
    public String getDescription() {
        final StringBuilder desc = new StringBuilder("Items: ");
        for (final Item item : myStoredItems) {
            desc.append("\n").append(item.getClass().getSimpleName());
        }
        return desc.toString();


    }


}
