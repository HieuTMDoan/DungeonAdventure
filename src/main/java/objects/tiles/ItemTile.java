package objects.tiles;

import objects.items.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemTile extends Tile {
    private static final char MULTIPLE_ITEMS_DISPLAY_CHAR = 'M';

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
                        : MULTIPLE_ITEMS_DISPLAY_CHAR,
                true);

        this.myStoredItems.addAll(List.of(theItems));

    }
}
