package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.items.Item;

public class ItemTile extends Tile {

    /**
     * The item occupying this tile.
     */
    private final Item myItem;


    /**
     * Constructor for an item tile with a specified item.
     *
     * @param theItem Item stored in this tile
     */
    public ItemTile(final Item theItem) {
        super(theItem.getDisplayChar(), true);

        this.myItem = theItem;
    }

    public Item getItem() {
        return this.myItem;
    }

    @Override
    public String getDescription() {
        return myItem.getDescription();
    }


}
