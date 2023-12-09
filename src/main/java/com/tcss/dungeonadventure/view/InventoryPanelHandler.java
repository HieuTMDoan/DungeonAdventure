package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.objects.items.Item;
import javafx.scene.layout.VBox;

import java.util.Map;

public class InventoryPanelHandler {

    private final AdventuringGUI myGUI;
    private final VBox myInventoryPanel;


    public InventoryPanelHandler(final AdventuringGUI theGUI) {
        this.myGUI = theGUI;
        myInventoryPanel = (VBox) theGUI.lookup("inventoryPanel");
        myInventoryPanel.getChildren().clear();
    }

    public void syncItems(final Map<Item, Integer> theItems) {
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
