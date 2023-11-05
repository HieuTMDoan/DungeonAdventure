package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.heroes.Hero;
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class DungeonGUI extends Application implements PropertyChangeListener {

    private static final double WINDOW_WIDTH = 900;
    private static final double WINDOW_HEIGHT = 600;
    private static final String HOME_FXML_PATH =
            "./src/main/resources/com/tcss/dungeonadventure/fxml/dungeon-home-screen.fxml";
    private static final String ADVENTURE_FXML_PATH =
            "./src/main/resources/com/tcss/dungeonadventure/fxml/dungeon-adventure.fxml";
    private static final String WINDOW_TITLE = "Dungeon Adventure";
    private final Text[][] myRoomTextBoxes = new Text[10][10];

    private Tile[][] myRoomTiles = new Tile[10][10];
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
        myScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP, W -> movePlayer(Directions.Cardinal.NORTH);
                case DOWN, S -> movePlayer(Directions.Cardinal.SOUTH);
                case LEFT, A -> movePlayer(Directions.Cardinal.WEST);
                case RIGHT, D -> movePlayer(Directions.Cardinal.EAST);
                case PERIOD -> {
                    final Room room = new Room(false, false, null);
                    System.out.println(room.toString());
                    loadRoom(room);
                }
                default -> {
                }
            }
        });

        final Room room = new Room(false, false, null);
        System.out.println(room);

        loadRoom(room);
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

                final int finalRow = row; // these are needed for the lambda statements
                final int finalCol = col;

                hbox.setOnMouseEntered(e -> onMouseOver(finalRow, finalCol));
                hbox.getChildren().add(text);


                this.myGridPane.add(hbox, col, row);
            }

        }


    }

    private void movePlayer(final Directions.Cardinal theDirection) {
        if (theDirection == null) {
            return;
        }

        // Ideally, this will fire some sort of property change event.
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

    }

    private void loadRoom(final Room theRoom) {
        clearGrid();
        myRoomTiles = theRoom.getRoomTiles();


        for (int row = 0; row < myGridPane.getRowCount(); row++) {
            for (int col = 0; col < myGridPane.getColumnCount(); col++) {
                try {
                    setTileAt(row, col, myRoomTiles[row][col]);
                } catch (Exception e) {
                    setTileAt(row, col, new EmptyTile());
                }
            }
        }
    }

    private void onMouseOver(final int theRowIndex, final int theColIndex) {
        try {
            final Tile t = myRoomTiles[theRowIndex][theColIndex];
            myTileInfoLabel.setText(String.format("(%s, %s)%n%s", theColIndex, theRowIndex, t.getDescription()));

        } catch (final Exception e) {
            myTileInfoLabel.setText(" ");
        }

    }

    private void clearGrid() {
        for (int row = 0; row < myGridPane.getRowCount(); row++) {
            for (int col = 0; col < myGridPane.getColumnCount(); col++) {
                setTileAt(row, col, new EmptyTile());
            }
        }
    }


    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

    }
}