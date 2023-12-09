package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.items.Item;
import com.tcss.dungeonadventure.objects.tiles.EmptyTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 * Represents the GUI of the current room the player is in,
 * and of player's stats, tile info, inventory, and message box.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class AdventuringGUI implements PropertyChangeListener {

    /**
     * The CSS styling for the box size.
     */
    static final String BOX_SIZE_CSS = "-fx-font-size: 45; ";

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

    /**
     * The handler for the players visual inventory.
     */
    private InventoryPanelHandler myInventoryPaneHandler;

    /**
     * The parent scroll-pane for the scrollable console.
     * This should not be accessed.
     */
    private ScrollPane myMessageScrollPane;

    /**
     * The message box.
     */
    private VBox myMessageBox;

    /**
     * The player info label.
     */
    private Label myPlayerInfoLabel;


    public AdventuringGUI(final GUIHandler theGUI) {
        this.myGUI = theGUI;
        PCS.addPropertyListener(this);


        locateNodes();
        createGUI();

        myMessageBox.getChildren().clear();

        loadRoom(DungeonAdventure.getInstance().getDungeon().getCurrentRoom());

    }


    /**
     * Using a node ID, you can access nodes in the Adventuring screen's FXML by ID.
     *
     * @param theNodeID The ID of the node to access.
     * @return The looked-up node, or null if it isn't found.
     */
    Node lookup(final String theNodeID) {
        return this.myGUI.lookup(theNodeID);
    }

    /**
     * Helper method to organize the binding of nodes to variables.
     */
    private void locateNodes() {
        myGridPane = (GridPane) this.lookup("roomGrid");
        myTileInfoLabel = (Label) this.lookup("tileInfoLabel");

        myInventoryPaneHandler = new InventoryPanelHandler(this);
        myPlayerInfoLabel = (Label) lookup("playerInfoLabel");


        myMessageBox = (VBox) lookup("messageBox");
        myMessageScrollPane = (ScrollPane) lookup("messageScrollPane");
        myMessageBox.heightProperty().addListener(
                (ChangeListener) (observable, oldValue, newValue)
                        -> myMessageScrollPane.setVvalue((Double) newValue));

        // This is to fix a bug where things within a scroll-pane are blurry
        myMessageScrollPane.setCache(false);
        for (Node n : myMessageScrollPane.getChildrenUnmodifiable()) {
            n.setCache(false);
        }
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
                text.setStyle(BOX_SIZE_CSS + "-fx-fill: white;");

                // these are needed for the lambda statements
                final int finalRow = row;
                final int finalCol = col;

                hbox.setOnMouseEntered(e -> onMouseOver(finalRow, finalCol));
                hbox.getChildren().add(text);

                this.myGridPane.add(hbox, col, row);
            }
        }
    }


    /**
     * Changes the tile at a specified index based on a given tile.
     *
     * @param theRowIndex The row of the tile.
     * @param theColIndex The column of the tile.
     * @param theTile     The new tile.
     */
    private void setTileAt(final int theRowIndex, final int theColIndex, final Tile theTile) {
        setTileAt(theRowIndex, theColIndex, theTile.getDisplayChar());
    }

    /**
     * Changes the tile at a specified index based on a given tile character.
     *
     * @param theRowIndex The row of the tile.
     * @param theColIndex The column of the tile.
     * @param theChar     The new tile character.
     */
    private void setTileAt(final int theRowIndex, final int theColIndex, final char theChar) {
        if (theRowIndex > myGridPane.getRowCount()
                || theColIndex > myGridPane.getColumnCount()) {

            throw new IllegalArgumentException(
                    "Row or col must be within bounds row: %d col: %d; %d %d".
                            formatted(myGridPane.getRowCount(),
                                    myGridPane.getColumnCount(),
                                    theRowIndex, theColIndex));
        }

        myRoomTextBoxes[theRowIndex][theColIndex].setStyle(BOX_SIZE_CSS + "-fx-fill: "
                + switch (theChar) {
            case TileChars.Player.PLAYER -> "green;";

            case TileChars.Monster.OGRE,
                    TileChars.Monster.SKELETON,
                    TileChars.Monster.GREMLIN -> "red;";

            case TileChars.Items.PILLAR_OF_ABSTRACTION,
                    TileChars.Items.PILLAR_OF_INHERITANCE,
                    TileChars.Items.PILLAR_OF_ENCAPSULATION,
                    TileChars.Items.PILLAR_OF_POLYMORPHISM -> "gold;";

            case TileChars.Items.HEALING_POTION,
                    TileChars.Items.VISION_POTION -> "blue;";

            case TileChars.Room.HORIZONTAL_DOOR,
                    TileChars.Room.VERTICAL_DOOR -> "coral;";

            case TileChars.Room.ENTRANCE,
                    TileChars.Room.EXIT -> "purple;";

            case TileChars.Room.PIT -> "orange;";

            default -> "white;";
        });

        myRoomTextBoxes[theRowIndex][theColIndex].setText(String.valueOf(theChar));
    }


    private void loadRoom(final Room theRoom) {
        clearGrid();
        myCurrentRoom = theRoom;

        renderRoomWithPlayer();
        updatePlayerStats();  // Update PlayerStatsBox when loading a new room
    }

    private void updatePlayerStats() {
        if (myCurrentRoom != null && DungeonAdventure.getInstance().getPlayer() != null) {
            final Hero playerHero = DungeonAdventure.getInstance().getPlayer().getPlayerHero();

            if (playerHero != null) {
                myPlayerInfoLabel.setText(String.format("""
                                Name: %s
                                Health: %s/%s
                                Damage Range: %s-%s
                                Speed: %s
                                Accuracy: %s
                                """,
                        playerHero.getName(),
                        playerHero.getHealth(), playerHero.getMaxHealth(),
                        playerHero.getMinDamage(), playerHero.getMaxDamage(),
                        playerHero.getAttackSpeed(),
                        playerHero.getAccuracy()));
            }
        }
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
                        setTileAt(row, col, TileChars.Player.PLAYER);
                    } else {
                        setTileAt(row, col, myCurrentRoom.getRoomTiles()[row][col]);
                    }

                } catch (final ArrayIndexOutOfBoundsException e) {
                    setTileAt(row, col, new EmptyTile());
                }
            }
        }
    }

    /**
     * Displays a message in the message box, along with a time-stamp.
     *
     * @param theMessage The message to display.
     */
    private void log(final String theMessage) {
        final String time = new SimpleDateFormat("hh:mm:ss").
                format(Calendar.getInstance().getTime());

        final Label label = new Label(String.format("[%s] %s", time, theMessage));
        label.setWrapText(true);
        label.setStyle("-fx-font-fill: white; -fx-font-size: 12");
        myMessageBox.getChildren().add(label);
    }

    /**
     * Displays a description in the tile description box.
     *
     * @param theString The description to display.
     */
    void showDescription(final String theString) {
        myTileInfoLabel.setText(theString);
    }

    private void onMouseOver(final int theRowIndex, final int theColIndex) {
        try {
            final Tile t = myCurrentRoom.getRoomTiles()[theRowIndex][theColIndex];
            showDescription(String.format(
                    "(%s, %s)%n%s", theColIndex, theRowIndex, t.getDescription()));

        } catch (final ArrayIndexOutOfBoundsException e) {
            showDescription(" ");
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
            case UPDATED_PLAYER_LOCATION -> {
                renderRoomWithPlayer();
                updatePlayerStats();  // Update PlayerStatsBox when player location changes
            }
            case ITEMS_CHANGED -> {
                this.myInventoryPaneHandler.syncItems((Map<Item, Integer>) theEvent.getNewValue());
                updatePlayerStats();  // Update PlayerStatsBox when items change
            }
            case LOG -> log((String) theEvent.getNewValue());
            case END_COMBAT -> loadRoom(myCurrentRoom);
            default -> {
            }
        }
    }

    @Override
    public boolean equals(final Object theOther) {
        return theOther instanceof AdventuringGUI;
    }

}