package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.items.Item;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;


/**
 * A Node to represent a single item type in the GUI inventory.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class ItemDisplayBox extends HBox {

    /**
     * Default spacing in-between items.
     */
    private static final int SPACING = 16;

    /**
     * Constructs a new ItemDisplayBox with the specified item and count.
     *
     * @param theItem  The item to display.
     * @param theCount The number of items the player holds.
     */
    ItemDisplayBox(final Item theItem, final int theCount) {
        super(SPACING);
        createUI(theItem, theCount);

    }

    /**
     * Helper method to build the UI of this Node.
     *
     * @param theItem  The item to display.
     * @param theCount The number of items the player holds.
     */
    private void createUI(final Item theItem, final int theCount) {
        this.setAlignment(Pos.CENTER_LEFT);
        final Text itemDisplayCharacter = new Text(String.valueOf(theItem.getDisplayChar()));
        itemDisplayCharacter.setBoundsType(TextBoundsType.VISUAL);
        itemDisplayCharacter.setStyle(
                "-fx-font-size: 24; -fx-fill: %s;".formatted(theItem.getTileColor()));

        this.getChildren().add(itemDisplayCharacter);

        final Label nameDisplayLabel = new Label();
        nameDisplayLabel.setWrapText(true);
        nameDisplayLabel.setText(
                Helper.camelToSpaced(theItem.getClass().getSimpleName()) + " x" + theCount);
        nameDisplayLabel.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        this.getChildren().add(nameDisplayLabel);
    }


}
