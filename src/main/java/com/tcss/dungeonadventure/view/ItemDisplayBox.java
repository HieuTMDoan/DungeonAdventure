package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.items.Item;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class ItemDisplayBox extends HBox {

    /**
     * Default spacing in-between items.
     */
    private static final int SPACING = 16;

    /**
     * The item to display.
     */
    private final Item myItem;

    /**
     * The name of the item.
     */
    private final Label myNameDisplayLabel;

    ItemDisplayBox(final Item theItem, final int theCount) {
        super(SPACING);
        this.myItem = theItem;

        this.setAlignment(Pos.CENTER_LEFT);
        final Text itemDisplayCharacter = new Text(String.valueOf(theItem.getDisplayChar()));
        itemDisplayCharacter.setBoundsType(TextBoundsType.VISUAL);
        itemDisplayCharacter.setStyle("-fx-font-size: 24; -fx-fill: %s;".formatted(theItem.getTileColor()));

        this.getChildren().add(itemDisplayCharacter);

        myNameDisplayLabel = new Label();
        myNameDisplayLabel.setWrapText(true);
        myNameDisplayLabel.setText(Helper.camelToSpaced(myItem.getClass().getSimpleName()) + " x" + theCount);
        myNameDisplayLabel.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        this.getChildren().add(myNameDisplayLabel);
    }


}
