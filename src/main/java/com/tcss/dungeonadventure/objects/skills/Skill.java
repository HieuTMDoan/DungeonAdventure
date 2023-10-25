package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.objects.DungeonCharacter;

public abstract class Skill {


    public Skill() {

    }

    public abstract boolean activateSkill(DungeonCharacter theTarget);

}
