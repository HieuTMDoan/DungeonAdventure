package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.objects.DungeonCharacter;


/**
 * The skill of the Thief class to gamble on dealing more damage.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class SurpriseAttack extends Skill {

    /**
     * The chance for a SurpriseAttack to be successful.
     */
    private static final double DEFAULT_SUCCESSFUL = 0.4;

    /**
     * The chance for a SurpriseAttack to be unsuccessful, but do nothing.
     */
    private static final double DEFAULT_NONE = 0.4;


    @Override
    public void activateSkill(final DungeonCharacter theSource,
                              final DungeonCharacter theTarget) {
        final int ranInt = Helper.getRandomIntBetween(0, 1);

        if (ranInt < DEFAULT_SUCCESSFUL) { // SUCCESSFUL
            PCS.firePropertyChanged(PCS.COMBAT_LOG,
                    "Surprise Attack was successful, doing 2 attacks.");
            for (int i = 0; i < 2; i++) {
                final int d = theSource.attack(theTarget);
                if (d > 0) {

                    DungeonAdventure.getInstance().getPlayer().
                            increaseStat(Player.Fields.DAMAGE_DEALT, d);

                    PCS.firePropertyChanged(PCS.COMBAT_LOG,
                            "Player attacked, dealing " + d + " damage.");
                } else {
                    DungeonAdventure.getInstance().getPlayer().
                            increaseStat(Player.Fields.MISSED_ATTACKS);

                    PCS.firePropertyChanged(PCS.COMBAT_LOG, "Player missed!");
                }

            }

        } else if (ranInt < DEFAULT_SUCCESSFUL + DEFAULT_NONE) { // NOTHING
            PCS.firePropertyChanged(PCS.COMBAT_LOG,
                    "Surprise Attack was somewhat successful, doing 1 attack.");
            final int d = theSource.attack(theTarget);
            if (d > 0) {
                DungeonAdventure.getInstance().getPlayer().
                        increaseStat(Player.Fields.DAMAGE_DEALT, d);
                PCS.firePropertyChanged(PCS.COMBAT_LOG,
                        "Player attacked, dealing " + d + " damage.");
            } else {
                DungeonAdventure.getInstance().getPlayer().
                        increaseStat(Player.Fields.MISSED_ATTACKS);
                PCS.firePropertyChanged(PCS.COMBAT_LOG, "Player missed!");
            }

        } else { // UNSUCCESSFUL
            PCS.firePropertyChanged(PCS.COMBAT_LOG, "Surprise Attack was unsuccessful!");
        }

    }

    @Override
    public String getDescription() {
        return "40% chance to do two attacks, 40% chance to do one attack,"
                + " 20% chance to have no attack at all!";
    }

}
