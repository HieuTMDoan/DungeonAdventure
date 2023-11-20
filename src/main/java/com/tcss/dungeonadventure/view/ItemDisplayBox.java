package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.objects.items.Item;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ItemDisplayBox extends HBox {

    private static final int SPACING = 16;

    private final Item myItem;

    private final Label myNameDisplayLabel;

    ItemDisplayBox(final Item theItem, final int theCount) {
        super(SPACING);
        this.myItem = theItem;

        this.setAlignment(Pos.CENTER_LEFT);
        final Label itemDisplayCharacter = new Label(String.valueOf(theItem.getDisplayChar()));
        itemDisplayCharacter.setStyle("-fx-font-size: 18; -fx-text-fill: white;");

        this.getChildren().add(itemDisplayCharacter);

        myNameDisplayLabel = new Label();
        updateCount(theCount);
        myNameDisplayLabel.setStyle("-fx-font-size: 12; -fx-text-fill: white;");
        this.getChildren().add(myNameDisplayLabel);
    }

    void updateCount(final int theNewCount) {
        myNameDisplayLabel.setText(myItem.getClass().getSimpleName() + " x" + theNewCount);
    }


}
