package com.tcss.dungeonadventure.objects.tiles;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import java.io.Serial;


/**
 * A tile that will damage the player upon stepping over the tile.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class PitTile extends Tile {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The damage dealt when the pit is stepped on.
     */
    private static final int DAMAGE = 15;


    /**
     * Constructs a new PitTile.
     */
    public PitTile() {
        super(TileChars.Room.PIT, true);
    }

    @Override
    public void onInteract(final Player thePlayer) {
        if (thePlayer.isInvincible()) {
            return;
        }

        // Play the sound when the player steps on the pit
        playSound(PIT_STEP_SOUND_FILE);

        final Hero hero = thePlayer.getPlayerHero();
        hero.changeHealth(this, -DAMAGE);

        if (hero.isDefeated()) {
            DungeonAdventure.getInstance().endGame(false);
        }

        PCS.firePropertyChanged(PCS.LOG, "Stepped into a pit! Lost " + DAMAGE + " heath.");

    }

//    private void playSoundEffect() {
//        try {
//            // Load the sound file
//            FileInputStream fileInputStream = new FileInputStream(SOUND_EFFECT_PATH);
//            AdvancedPlayer player = new AdvancedPlayer(fileInputStream);
//
//            // Set up a listener to handle events
//            player.setPlayBackListener(new PlaybackListener() {
//                @Override
//                public void playbackFinished(PlaybackEvent evt) {
//                    // Handle playback finished event if needed
//                }
//            });
//
//            // Start playback
//            player.play();
//        } catch (FileNotFoundException | JavaLayerException e) {
//            e.printStackTrace(); // Handle exceptions appropriately
//        }
//    }

    @Override
    public String getTileColor() {
        return "orange";
    }
}
