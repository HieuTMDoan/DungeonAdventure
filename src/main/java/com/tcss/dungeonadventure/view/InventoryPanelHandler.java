package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.objects.items.Item;
import java.util.Map;
import javafx.scene.layout.VBox;


/**
 * A class to manage the GUI representation of the inventory.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class InventoryPanelHandler {

    /**
     * The parent GUI.
     */
    private final AdventuringGUI myGUI;

    /**
     * The VBox of the inventory.
     */
    private final VBox myInventoryPanel;


    /**
     * Constructs a new InventoryPanelHandler.
     *
     * @param theGUI The AdventuringGUI.
     */
    InventoryPanelHandler(final AdventuringGUI theGUI) {
        this.myGUI = theGUI;
        myInventoryPanel = (VBox) theGUI.lookup("inventoryPanel");
        myInventoryPanel.getChildren().clear();
        syncItems(DungeonAdventure.getInstance().getPlayer().getInventory());
    }

    void syncItems(final Map<Item, Integer> theItems) {
        myInventoryPanel.getChildren().clear();

        for (final Item item : theItems.keySet()) {
            final Integer count = theItems.get(item);
            final ItemDisplayBox box = new ItemDisplayBox(item, count);
            box.setOnMouseEntered(e -> this.myGUI.showDescription(item.getDescription()));
            box.setOnMouseClicked(e -> DungeonAdventure.getInstance().useItem(item));

            myInventoryPanel.getChildren().add(box);
        }
    }



}
