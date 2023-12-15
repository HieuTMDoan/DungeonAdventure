package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.objects.TileChars;
import java.io.Serial;


/**
 * A tile to represent the exit of the dungeon.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class ExitTile extends Tile {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ExitTile.
     */
    public ExitTile() {
        super(TileChars.Room.EXIT, true);
    }

    @Override
    public void onInteract(final Player thePlayer) {
        if (DungeonAdventure.getInstance().getPlayer().hasAllPillars()) {
            DungeonAdventure.getInstance().endGame(true);
            return;
        }
        PCS.firePropertyChanged(PCS.LOG, "Find the rest of the pillars before leaving!");
    }

    @Override
    public String getTileColor() {
        return "hotpink";
    }
}
