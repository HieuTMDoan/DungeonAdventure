package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.DungeonCharacter;

public class SurpriseAttack extends Skill {

    /**
     * The chance for a SurpriseAttack to be successful.
     */
    private static final double DEFAULT_SUCCESSFUL = 0.4;

    /**
     * The chance for a SurpriseAttack to be unsuccessful, but do nothing.
     */
    private static final double DEFAULT_NONE = 0.4;


    public SurpriseAttack() {

    }

    @Override
    public Integer activateSkill(final DungeonCharacter theTarget) {
        final int ranInt = Helper.getRandomIntBetween(0, 1);

        if (ranInt < DEFAULT_SUCCESSFUL) { // SUCCESSFUL
            return 1;
        } else if (ranInt < DEFAULT_SUCCESSFUL + DEFAULT_NONE) { // NOTHING



            return 0;
        } else { // UNSUCCESSFUL
            return null;
        }

    }
}
