package com.tcss.dungeonadventure.objects.tiles;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

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
     * The sound file for stepping on a pit.
     */
    private static final String PIT_STEP_SOUND_FILE = "Oof.wav";

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

        PCS.firePropertyChanged(PCS.LOG, "Stepped into a pit! Lost " + DAMAGE + " health.");
    }

    /**
     * Plays a sound file.
     *
     * @param soundFilePath The path to the sound file.
     */
    private void playSound(String soundFilePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTileColor() {
        return "orange";
    }
}
