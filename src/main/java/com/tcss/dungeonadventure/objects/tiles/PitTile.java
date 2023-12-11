package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.objects.heroes.Hero;

public class PitTile extends Tile {

    /**
     * The damage dealt when the pit is stepped on.
     */
    private static final int DAMAGE = 15;


    public PitTile() {
        super(TileChars.Room.PIT, true);
    }

    @Override
    public void onInteract(final Player thePlayer) {
        final Hero hero = thePlayer.getPlayerHero();
        hero.changeHealth(this, -DAMAGE);

        if (hero.isDefeated()) {
            DungeonAdventure.getInstance().handlePlayerDefeat();
        }

        PCS.firePropertyChanged(PCS.LOG, "Stepped into a pit! Lost " + DAMAGE + " heath.");

    }

    @Override
    public String getTileColor() {
        return "orange";
    }
}
