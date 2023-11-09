package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.tiles.EmptyTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class AdventuringGUI implements PropertyChangeListener {

    /**
     * The path for the FXML file for the adventuring screen.
     */
    private static final String ADVENTURE_FXML_PATH =
            "./src/main/resources/com/tcss/dungeonadventure/fxml/dungeon-adventure.fxml";

    /**
     * The title of the window.
     */
    private static final String WINDOW_TITLE = "Dungeon Adventure";


    /**
     * A 2D array of Text nodes, representing each character of the room grid.
     */
    private final Text[][] myRoomTextBoxes = new Text[10][10];

    /**
     * A 2D array of Tiles, which is what the current room looks like.
     */
    private Tile[][] myRoomTiles = new Tile[10][10];

    /**
     * The current scene.
     */
    private Scene myScene;

    /**
     * The room grid. 10x10.
     */
    private GridPane myGridPane;

    /**
     * The text box containing tile information on mouse-over.
     */
    private Label myTileInfoLabel;

    public AdventuringGUI(final Scene theScene) throws IOException {
        PCS.addPropertyListener(this);
        final FXMLLoader fxmlLoader = new FXMLLoader(new File(ADVENTURE_FXML_PATH).toURI().toURL());
        this.myScene = theScene;

        theScene.setRoot(fxmlLoader.load());

        locateNodes();
        createGUI();

        // This is the key event system
        myScene.setOnKeyPressed(this::handleKeyPress);

        final Room room = new Room(false, false, null);
        System.out.println(room);

        loadRoom(room);
    }

    /**
     * Using a node ID, you can access nodes in the FXML by ID.
     *
     * @param theNodeID The ID of the node to access.
     * @return The looked-up node, or null if it isn't found.
     */
    Node lookup(final String theNodeID) {
        return this.myScene.lookup(theNodeID.charAt(0) == '#' ? theNodeID : "#" + theNodeID);
    }

    private void handleKeyPress(final KeyEvent theEvent) {
        switch (theEvent.getCode()) {
            case UP, W -> PCS.firePropertyChanged(PCS.MOVE_PLAYER, Directions.Cardinal.NORTH);
            case DOWN, S -> PCS.firePropertyChanged(PCS.MOVE_PLAYER, Directions.Cardinal.SOUTH);
            case LEFT, A -> PCS.firePropertyChanged(PCS.MOVE_PLAYER, Directions.Cardinal.WEST);
            case RIGHT, D -> PCS.firePropertyChanged(PCS.MOVE_PLAYER, Directions.Cardinal.EAST);
            case PERIOD -> PCS.firePropertyChanged(PCS.LOAD_ROOM, new Room(false, false, null));
            default -> {
            }
        }
    }

    /**
     * Helper method to organize the binding of nodes to variables.
     */
    private void locateNodes() {
        myGridPane = (GridPane) this.lookup("roomGrid");
        myTileInfoLabel = (Label) this.lookup("tileInfoLabel");
    }

    /**
     * Helper method which populates the grid with text boxes.
     */
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

    /**
     * This is called when a movement command is executed.
     *
     * @param theDirection The direction the player moved in.
     */
    private void movePlayer(final Directions.Cardinal theDirection) {
        if (theDirection == null) {
            return;
        }



    }

    /**
     * Changes the tile at a specified index.
     *
     * @param theRowIndex The row of the tile.
     * @param theColIndex The column of the tile.
     * @param theTile     The new tile.
     */
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
                } catch (final ArrayIndexOutOfBoundsException e) {
                    setTileAt(row, col, new EmptyTile());
                }
            }
        }
    }

    private void onMouseOver(final int theRowIndex, final int theColIndex) {
        try {
            final Tile t = myRoomTiles[theRowIndex][theColIndex];
            myTileInfoLabel.setText(String.format("(%s, %s)%n%s", theColIndex, theRowIndex, t.getDescription()));

        } catch (final ArrayIndexOutOfBoundsException e) {
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
        switch (PCS.valueOf(theEvent.getPropertyName())) {
            case MOVE_PLAYER -> movePlayer((Directions.Cardinal) theEvent.getNewValue());
            case LOAD_ROOM -> {
                final Room room = (Room) theEvent.getNewValue();
                loadRoom(room);
                System.out.println(room);
            }
            default -> {
            }


        }

    }
}