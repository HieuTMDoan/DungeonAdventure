package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.tiles.EmptyTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AdventuringGUI implements PropertyChangeListener {

    /**
     * The GUI handler.
     */
    private final GUIHandler myGUI;

    /**
     * A 2D array of Text nodes, representing each character of the room grid.
     */
    private final Text[][] myRoomTextBoxes = new Text[10][10];

    /**
     * A 2D array of Tiles, which is what the current room looks like.
     */
    private Room myCurrentRoom;


    /**
     * The room grid. 10x10.
     */
    private GridPane myGridPane;

    /**
     * The text box containing tile information on mouse-over.
     */
    private Label myTileInfoLabel;

    public AdventuringGUI(final GUIHandler theGUI) {
        this.myGUI = theGUI;
        PCS.addPropertyListener(this);

        locateNodes();
        createGUI();
    }

    /**
     * Using a node ID, you can access nodes in the FXML by ID.
     *
     * @param theNodeID The ID of the node to access.
     * @return The looked-up node, or null if it isn't found.
     */
    Node lookup(final String theNodeID) {
        return this.myGUI.lookup(theNodeID.charAt(0) == '#' ? theNodeID : "#" + theNodeID);
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
     * Changes the tile at a specified index.
     *
     * @param theRowIndex The row of the tile.
     * @param theColIndex The column of the tile.
     * @param theTile     The new tile.
     */
    private void setTileAt(final int theRowIndex, final int theColIndex, final Tile theTile) {
        setTileAt(theRowIndex, theColIndex, theTile.getDisplayChar());
    }

    private void setTileAt(final int theRowIndex, final int theColIndex, final char theChar) {
        if (theRowIndex > myGridPane.getRowCount()
                || theColIndex > myGridPane.getColumnCount()) {

            throw new IllegalArgumentException(
                    "Row or col must be within bounds row: %d col: %d; %d %d".
                            formatted(myGridPane.getRowCount(),
                                    myGridPane.getColumnCount(),
                                    theRowIndex, theColIndex));
        }

        myRoomTextBoxes[theRowIndex][theColIndex].setText(String.valueOf(theChar));
    }


    private void loadRoom(final Room theRoom) {
        clearGrid();
        myCurrentRoom = theRoom;

        renderRoomWithPlayer();

    }

    /**
     * This gets called to re-render the room, with the player on top of the tile.
     * Pretty inefficient, as it's an O(n^2) process each time a movement command is
     * fired, but for the current scale of 10x10, it still feels completely lag-less.
     */
    private void renderRoomWithPlayer() {
        for (int row = 0; row < myGridPane.getRowCount(); row++) {
            for (int col = 0; col < myGridPane.getColumnCount(); col++) {
                try {
                    if (myCurrentRoom.getPlayerXPosition() != null
                            && row == myCurrentRoom.getPlayerXPosition()
                            && col == myCurrentRoom.getPlayerYPosition()) {
                        setTileAt(row, col, '/');
                    } else {
                        setTileAt(row, col, myCurrentRoom.getRoomTiles()[row][col]);
                    }

                } catch (final ArrayIndexOutOfBoundsException e) {
                    setTileAt(row, col, new EmptyTile());
                }
            }
        }
    }

    private void onMouseOver(final int theRowIndex, final int theColIndex) {
        try {
            final Tile t = myCurrentRoom.getRoomTiles()[theRowIndex][theColIndex];
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
            case LOAD_ROOM -> loadRoom((Room) theEvent.getNewValue());
            case UPDATED_PLAYER_LOCATION -> renderRoomWithPlayer();
            default -> {
            }


        }

    }
}