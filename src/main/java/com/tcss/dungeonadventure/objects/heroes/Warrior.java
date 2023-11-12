package com.tcss.dungeonadventure.objects.heroes;

import com.tcss.dungeonadventure.objects.skills.CrushingBlow;
import com.tcss.dungeonadventure.objects.skills.Skill;

/**
 * Represents a Warrior character.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class Warrior extends Hero {
    /**
     * Innate skill of Warrior characters.
     */
    private static final Skill MY_SKILL = new CrushingBlow();

    /**
     * Constructor for Warrior characters.
     *
     * @param theName class name of Warrior characters
     * @param theDisplayChar display character of Warrior characters
     * @param theDefaultHealth starting health of Warrior characters
     * @param theMinDamage minimum damage dealt by Warrior characters
     * @param theMaxDamage maximum damage dealt by Warrior characters
     * @param theAttackSpeed attack speed of Warrior characters
     * @param theAccuracy attack accuracy of Warrior characters
     * @param theBlockChance chance to block of Warrior characters
     */
    public Warrior(final String theName,
                     final char theDisplayChar,
                     final int theDefaultHealth,
                     final int theMinDamage,
                     final int theMaxDamage,
                     final int theAttackSpeed,
                     final double theAccuracy,
                     final double theBlockChance) {
        super(theName,
                theDisplayChar,
                theDefaultHealth,
                theMinDamage,
                theMaxDamage,
                theAttackSpeed,
                  theAccuracy,
                theBlockChance,
                MY_SKILL);
    }

}
