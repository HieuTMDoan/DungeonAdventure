package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.objects.TileChars;

public class ExitTile extends Tile {

    public ExitTile() {
        super(TileChars.Room.EXIT, true);
    }

    @Override
    public void onInteract() {
        if (DungeonAdventure.getInstance().getPlayer().hasAllPillars()) {
            // victory
            PCS.firePropertyChanged(PCS.GAME_END, true);
            return;
        }
        PCS.firePropertyChanged(PCS.LOG, "Find the rest of the pillars before leaving!");
    }
}
