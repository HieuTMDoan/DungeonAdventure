package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.objects.heroes.Hero;

import java.io.Serial;

public class PitTile extends Tile {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The damage dealt when the pit is stepped on.
     */
    private static final int DAMAGE = 15;


    public PitTile() {
        super(TileChars.Room.PIT, true);
    }

    @Override
    public void onInteract(final Player thePlayer) {
        if (thePlayer.isInvincible()) {
            return;
        }

        final Hero hero = thePlayer.getPlayerHero();
        hero.changeHealth(this, -DAMAGE);

        if (hero.isDefeated()) {
            DungeonAdventure.getInstance().endGame(false);
        }

        PCS.firePropertyChanged(PCS.LOG, "Stepped into a pit! Lost " + DAMAGE + " heath.");

    }

    @Override
    public String getTileColor() {
        return "orange";
    }
}
