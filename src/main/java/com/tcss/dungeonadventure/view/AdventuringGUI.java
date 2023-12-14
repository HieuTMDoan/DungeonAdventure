package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Player;
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
import java.util.Objects;
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
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class AdventuringGUI extends GUILayout implements PropertyChangeListener {

    /**
     * The CSS styling for the box size.
     */
    static final String BOX_SIZE_CSS = "-fx-font-size: 45; ";


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

    /**
     * Constructs a new AdventuringGUI, representing the graphical user interface
     * for adventuring in the game.
     * <p>
     * This GUI is responsible for displaying the current room, handling property changes,
     * and interacting with the player's actions in the game.
     *
     * @param theGUI The GUIHandler responsible for managing the graphical user interface.
     * @see GUIHandler
     * @see PropertyChangeListener
     */
    public AdventuringGUI(final GUIHandler theGUI) {
        super(theGUI);
        PCS.addPropertyListener(this);


        locateNodes();
        createGUI();

        myMessageBox.getChildren().clear();

        loadRoom(DungeonAdventure.getInstance().getDungeon().getCurrentRoom());

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
        myMessageBox.heightProperty().addListener((observableValue, oldValue, newValue) ->
                myMessageScrollPane.setVvalue((Double) newValue));


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
        setTileAt(theRowIndex, theColIndex, theTile.getDisplayChar(), theTile.getTileColor());
    }

    /**
     * Changes the tile at a specified index based on a given tile character.
     *
     * @param theRowIndex The row of the tile.
     * @param theColIndex The column of the tile.
     * @param theChar     The new tile character.
     */
    private void setTileAt(final int theRowIndex,
                           final int theColIndex,
                           final char theChar,
                           final String theColor) {

        if (theRowIndex > myGridPane.getRowCount()
                || theColIndex > myGridPane.getColumnCount()) {

            throw new IllegalArgumentException(
                    "Row or col must be within bounds row: %d col: %d; %d %d".
                            formatted(myGridPane.getRowCount(),
                                    myGridPane.getColumnCount(),
                                    theRowIndex, theColIndex));
        }

        final Text text = myRoomTextBoxes[theRowIndex][theColIndex];

        text.setStyle(BOX_SIZE_CSS + "-fx-fill: " + theColor + ";");
        text.setText(String.valueOf(theChar));
    }


    /**
     * Loads the room that the player is in.
     *
     * @param theRoom The room to render.
     */
    private void loadRoom(final Room theRoom) {
        clearGrid();
        myCurrentRoom = theRoom;

        renderRoomWithPlayer();
        updatePlayerStats();
    }

    /**
     * Updates the players stats.
     */
    private void updatePlayerStats() {
        final Player player = DungeonAdventure.getInstance().getPlayer();
        if (myCurrentRoom != null && player != null) {
            final Hero playerHero = player.getPlayerHero();

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
                        setTileAt(row, col, TileChars.Player.PLAYER, "green");

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
        label.setStyle("-fx-font-fill: white; -fx-font-size: 15");
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


    /**
     * Clears the entire grid of the GUI, setting each tile to an EmptyTile.
     */
    private void clearGrid() {
        for (int row = 0; row < myGridPane.getRowCount(); row++) {
            for (int col = 0; col < myGridPane.getColumnCount(); col++) {
                setTileAt(row, col, new EmptyTile());
            }
        }
    }

    /**
     * Handles the mouse-over event for a specific tile in the grid,
     * displaying tile information.
     *
     * @param theRowIndex The row index of the tile.
     * @param theColIndex The column index of the tile.
     */
    private void onMouseOver(final int theRowIndex, final int theColIndex) {
        try {
            final Tile t = myCurrentRoom.getRoomTiles()[theRowIndex][theColIndex];
            showDescription(String.format(
                    "(%s, %s)%n%s", theColIndex, theRowIndex, t.getDescription()));

        } catch (final ArrayIndexOutOfBoundsException e) {
            showDescription(" ");
        }

    }

    /**
     * Handles property changes in the game, updating the GUI accordingly.
     *
     * @param theEvent The property change event.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (PCS.valueOf(theEvent.getPropertyName())) {
            case LOAD_ROOM -> loadRoom((Room) theEvent.getNewValue());
            case UPDATED_PLAYER_LOCATION -> {
                renderRoomWithPlayer();
                updatePlayerStats();
            }
            case ITEMS_CHANGED -> {
                this.myInventoryPaneHandler.
                        syncItems((Map<Item, Integer>) theEvent.getNewValue());

                updatePlayerStats();
            }
            case LOG -> log((String) theEvent.getNewValue());
            case END_COMBAT -> loadRoom(myCurrentRoom);
            default -> {
            }
        }
    }

    /**
     * Checks if this AdventuringGUI instance is equal to another object.
     *
     * @param theOther The object to compare.
     * @return True if theOther is an instance of AdventuringGUI, false otherwise.
     */
    @Override
    public boolean equals(final Object theOther) {
        return theOther instanceof AdventuringGUI;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myInventoryPaneHandler);
    }

}