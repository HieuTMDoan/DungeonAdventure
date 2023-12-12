package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.objects.DungeonCharacter;

public class CrushingBlow extends Skill {

    /**
     * The minimum damage.
     */
    private static final int DEFAULT_MIN_DAMAGE = 75;

    /**
     * The maximum damage.
     */
    private static final int DEFAULT_MAX_DAMAGE = 175;


    public CrushingBlow() {

    }

    @Override
    public Integer activateSkill(final DungeonCharacter theSource,
                                 final DungeonCharacter theTarget) {


        final int damage =
                Helper.getRandomIntBetween(DEFAULT_MIN_DAMAGE, DEFAULT_MAX_DAMAGE);

        theTarget.changeHealth(-damage);

        DungeonAdventure.getInstance().getPlayer().increaseStat(Player.Fields.DAMAGE_DEALT, damage);
        PCS.firePropertyChanged(PCS.COMBAT_LOG, "Crushing Blow dealt " + damage + " damage.");

        return damage;
    }

    @Override
    public String getDescription() {
        return "Damages the enemy for %s - %s damage.".formatted(DEFAULT_MIN_DAMAGE, DEFAULT_MAX_DAMAGE);
    }
}



