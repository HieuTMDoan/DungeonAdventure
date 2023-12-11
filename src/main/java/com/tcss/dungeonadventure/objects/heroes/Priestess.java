package com.tcss.dungeonadventure.objects.heroes;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.objects.skills.Heal;
import com.tcss.dungeonadventure.objects.skills.Skill;

/**
 * Represents a Priestess character.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class Priestess extends Hero {
    /**
     * Innate skill of Priestess characters.
     */
    private static final Skill MY_SKILL = new Heal();

    /**
     * Constructor for Priestess characters.
     *
     * @param theName class name of Priestess characters
     * @param theDefaultHealth starting health of Priestess characters
     * @param theMinDamage minimum damage dealt by Priestess characters
     * @param theMaxDamage maximum damage dealt by Priestess characters
     * @param theAttackSpeed attack speed of Priestess characters
     * @param theAccuracy attack accuracy of Priestess characters
     * @param theBlockChance chance to block of Priestess characters
     */
    public Priestess(final String theName,
                     final int theDefaultHealth,
                     final int theMinDamage,
                     final int theMaxDamage,
                     final int theAttackSpeed,
                     final double theAccuracy,
                     final double theBlockChance) {
        super(theName,
                TileChars.Player.PLAYER,
                theDefaultHealth,
                theMinDamage,
                theMaxDamage,
                theAttackSpeed,
                theAccuracy,
                theBlockChance,
                MY_SKILL);
    }


}
