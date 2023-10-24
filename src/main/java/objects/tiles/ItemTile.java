package objects.tiles;

import objects.TileChars;
import objects.items.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemTile extends Tile {
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




}
