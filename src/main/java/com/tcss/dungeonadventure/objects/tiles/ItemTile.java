package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.objects.items.Item;

import java.io.Serial;

public class ItemTile extends Tile {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The item occupying this tile.
     */
    private Item myItem;


    /**
     * Constructor for an item tile with a specified item.
     *
     * @param theItem Item stored in this tile
     */
    public ItemTile(final Item theItem) {
        super(theItem.getDisplayChar(), true);

        this.myItem = theItem;
    }

    public ItemTile() {

    }

    @Override
    public void onInteract(final Player thePlayer) {
        if (myItem == null) {
            return;
        }

        thePlayer.addItemToInventory(myItem);
        myItem = null;
    }

    @Override
    public String getTileColor() {
        return myItem == null ? super.getTileColor() : myItem.getTileColor();
    }

    @Override
    public char getDisplayChar() {
        return myItem != null ? myItem.getDisplayChar() : TileChars.Room.EMPTY;
    }

    public Item getItem() {
        return this.myItem;
    }

    @Override
    public String getDescription() {
        return myItem == null ? super.getDescription() : myItem.getDescription();
    }


}
