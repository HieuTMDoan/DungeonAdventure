package com.tcss.dungeonadventure.objects.items;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;


/**
 * An item that allows the player to use their skill during combat.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class SkillOrb extends Item {


    /**
     * Constructs a new Skill Orb.
     */
    public SkillOrb() {
        super(TileChars.Items.SKILL_ORB, ItemTypes.CONSUMABLE);
    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // Do nothing

    }

    @Override
    public String getTileColor() {
        return "blue";
    }

    @Override
    public String getDescription() {
        return "Allows heroes to use their skill during combat."
                + " Consumed when a skill is used.";
    }

}
