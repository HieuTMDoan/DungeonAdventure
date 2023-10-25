package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.objects.DungeonCharacter;

public class SurpriseAttack extends Skill {

    //    TODO: Decide how much to heal
    private static final double DEFAULT_SUCCESSFUL = 0.4;
    private static final double DEFAULT_NONE = 0.4;
    private static final double DEFAULT_UNSUCCESSFUL = 0.2;


    public SurpriseAttack() {

    }

    @Override
    public boolean activateSkill(final DungeonCharacter theTarget) {
        return false;
    }
}
