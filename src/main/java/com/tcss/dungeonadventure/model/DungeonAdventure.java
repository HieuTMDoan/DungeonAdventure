package com.tcss.dungeonadventure.model;


import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.tiles.EntranceTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import com.tcss.dungeonadventure.view.ConsoleView;
import com.tcss.dungeonadventure.view.DungeonGUI;
import javafx.application.Application;

import java.awt.Point;


public class DungeonAdventure {

    /**
     * Singleton instance for DungeonAdventure.
     */
    private static DungeonAdventure INSTANCE;


    /**
     * The name of the player.
     */
    private String myPlayerName;

    /**
     * The class of the hero.
     */
    private Hero myHero;

    /**
     * The current Dungeon.
     */
    private Dungeon myDungeon;


    private DungeonAdventure() {


    }
    /**
     * Lazy singleton accessor.
     *
     * @return The instance of the DungeonAdventure.
     */
    public static DungeonAdventure getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DungeonAdventure();
        }
        return INSTANCE;
    }

    /**
     * Starts the view depending on if using GUI or not.
     * Should be called directly after constructor.
     *
     * @param theGUIActive If the GUI is active. If not, will print to console.
     */
    public void initialize(final boolean theGUIActive) {

    public void startNewGame(final String thePlayerName, final Hero theHero) {
        this.myPlayerName = thePlayerName;
        this.myHero = theHero;

        this.myDungeon = new Dungeon();

        final Room startingRoom = myDungeon.getStartingRoom();
        final Tile[][] roomTiles = startingRoom.getRoomTiles();


        // This locates the entrance tile in the entrance room.
        Point entranceTileLocation = null;
        loop:
        for (int i = 0; i < roomTiles.length; i++) {
            for (int j = 0; j < roomTiles[i].length; j++) {
                if (roomTiles[i][j].getClass() == EntranceTile.class) {
                    entranceTileLocation = new Point(i, j);
                    break loop;
                }
            }
        }
        if (entranceTileLocation == null) {
            throw new IllegalStateException("Could not find EntranceTile in starting room.");
        }

        this.myDungeon.loadPlayerTo(
                startingRoom.getDungeonLocation(),
                entranceTileLocation);

        PCS.firePropertyChanged(PCS.LOAD_ROOM, myDungeon.getStartingRoom());
    }

    public void movePlayer(final Directions.Cardinal theDirection) {
        this.myDungeon.getCurrentRoom().movePlayer(theDirection);
        PCS.firePropertyChanged(PCS.UPDATED_PLAYER_LOCATION, null);
    }

}
}
