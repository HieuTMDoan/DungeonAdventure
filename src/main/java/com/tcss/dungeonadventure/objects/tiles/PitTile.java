package com.tcss.dungeonadventure.objects.tiles;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.objects.heroes.Hero;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serial;

public class PitTile extends Tile {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The damage dealt when the pit is stepped on.
     */
    private static final int DAMAGE = 15;

    /**
     * The path to the MP3 sound effect file.
     */
    //private static final String SOUND_EFFECT_PATH = "soundpath";

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

        // Play the sound effect
        //playSoundEffect();

        PCS.firePropertyChanged(PCS.LOG, "Stepped into a pit! Lost " + DAMAGE + " health.");
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
