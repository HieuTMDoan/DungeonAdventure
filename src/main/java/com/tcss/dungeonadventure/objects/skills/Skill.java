package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import java.io.Serializable;

public abstract class Skill implements Serializable {


    public Skill() {

    }

    public abstract Integer activateSkill(DungeonCharacter theTarget);

}
