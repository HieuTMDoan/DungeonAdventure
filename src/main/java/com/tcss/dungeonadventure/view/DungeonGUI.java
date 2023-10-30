package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.heroes.Warrior;
import com.tcss.dungeonadventure.objects.monsters.Gremlin;
import com.tcss.dungeonadventure.objects.tiles.EmptyTile;
import com.tcss.dungeonadventure.objects.tiles.NPCTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import com.tcss.dungeonadventure.objects.tiles.WallTile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class DungeonGUI extends Application {

    private static final double WINDOW_WIDTH = 900;
    private static final double WINDOW_HEIGHT = 600;
    private static final String HOME_FXML_PATH =
            "./src/main/resources/com/tcss/dungeonadventure/fxml/dungeon-home-screen.fxml";
    private static final String ADVENTURE_FXML_PATH =
            "./src/main/resources/com/tcss/dungeonadventure/fxml/dungeon-adventure.fxml";
    private static final String WINDOW_TITLE = "Dungeon Adventure";
    private final Text[][] myRoomTextBoxes = new Text[10][10];

    private final Tile[][] myRoomTiles = new Tile[10][10];
    private Scene myScene;
    private GridPane myGridPane;
    private Label myTileInfoLabel;

    @Override
    public void start(final Stage theStage) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(
                new File(ADVENTURE_FXML_PATH).toURI().toURL());

        myScene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        theStage.setTitle(WINDOW_TITLE);
        theStage.setScene(myScene);
        theStage.show();

        locateNodes();
        createGUI();


        for (int row = 0; row < myGridPane.getRowCount(); row++) {
            for (int col = 0; col < myGridPane.getColumnCount(); col++) {
                if (row % 9 == 0 || col % 9 == 0) {
                    setTileAt(row, col, new WallTile());
                }
            }
        }

        setTileAt(5, 5, new NPCTile(new Warrior("Player")));

        setTileAt(2, 3, new NPCTile(new Gremlin()));


    }

    Node lookup(final String theNodeID) {
        return this.myScene.lookup(theNodeID.charAt(0) == '#' ? theNodeID : "#" + theNodeID);
    }

    private void locateNodes() {
        myGridPane = (GridPane) this.lookup("roomGrid");
        myTileInfoLabel = (Label) this.lookup("tileInfoLabel");
    }

    private void createGUI() {

        for (int row = 0; row < myGridPane.getRowCount(); row++) {
            for (int col = 0; col < myGridPane.getColumnCount(); col++) {
                final HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER);

                final Text text = new Text(" ");
                myRoomTextBoxes[row][col] = text;

                final Tile tile = new EmptyTile();
                myRoomTiles[row][col] = tile;

                text.setBoundsType(TextBoundsType.VISUAL);
                text.setStyle("-fx-font-size: 60; -fx-fill: rgb(255, 255, 255)");

                final int finalRow = row;
                final int finalCol = col;

                hbox.setOnMouseEntered(e -> {
                    onMouseOver(finalRow, finalCol);
                });
                hbox.getChildren().add(text);


                this.myGridPane.add(hbox, col, row);
            }

        }
    }

    private void setTileAt(final int theRowIndex, final int theColIndex, final Tile theTile) {
        if (theRowIndex > myGridPane.getRowCount()
                || theColIndex > myGridPane.getColumnCount()) {
            throw new IllegalArgumentException("Row or col must be within bounds "
                    + "row: " + myGridPane.getRowCount()
                    + " col: " + myGridPane.getColumnCount()
                    + "; " + theRowIndex + " " + theColIndex);
        }


        myRoomTextBoxes[theRowIndex][theColIndex].
                setText(String.valueOf(theTile.getDisplayChar()));

        myRoomTiles[theRowIndex][theColIndex] = theTile;
    }

    private void onMouseOver(final int theRowIndex, final int theColIndex) {
        final Tile t = myRoomTiles[theRowIndex][theColIndex];

        // load some sort of description
        myTileInfoLabel.setText(t.getDescription());

    }


}