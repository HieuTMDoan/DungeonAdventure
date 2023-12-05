package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.objects.TileChars;

public class PitTile extends Tile {

    /**
     * The damage dealt when the pit is stepped on.
     */
    private static final int DAMAGE = 15;


    public PitTile() {
        super(TileChars.Room.PIT, true);
    }

    @Override
    public void onInteract() {
        DungeonAdventure.getInstance().getPlayer().getPlayerHero().changeHealth(-DAMAGE);
        PCS.firePropertyChanged(PCS.LOG, "Stepped into a pit! Lost " + DAMAGE + " heath.");

    }


}
