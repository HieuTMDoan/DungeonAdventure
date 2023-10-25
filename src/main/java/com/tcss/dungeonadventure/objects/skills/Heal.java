package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.objects.DungeonCharacter;

public class Heal extends Skill {


//    TODO: Decide how much to heal
//    private static final int DEFAULT_MIN_HEAL = 75;
//    private static final int DEFAULT_MAX_HEAL = 175;

    public Heal() {

    }

    @Override
    public boolean activateSkill(final DungeonCharacter theTarget) {
        return false;
    }
}
