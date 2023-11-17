package com.tcss.dungeonadventure.objects.skills;


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

    /**
     * The chance for a SurpriseAttack to be unsuccessful and harm the player.
     */
    private static final double DEFAULT_UNSUCCESSFUL = 0.2;


    public SurpriseAttack() {

    }

    @Override
    public boolean activateSkill(final DungeonCharacter theTarget) {
        return false;
    }
}
