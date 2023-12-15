package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import java.io.Serializable;


/**
 * The parent of all skills.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public abstract class Skill implements Serializable {


    public abstract void activateSkill(DungeonCharacter theSource, DungeonCharacter theTarget);


    public abstract String getDescription();

}
