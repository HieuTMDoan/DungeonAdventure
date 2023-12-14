package com.tcss.dungeonadventure.objects.heroes;

import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.objects.skills.Skill;
import com.tcss.dungeonadventure.objects.skills.SurpriseAttack;

/**
 * Represents a Thief character.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class Thief extends Hero {

    /**
     * Innate skill of Thief characters.
     */
    private static final Skill MY_SKILL = new SurpriseAttack();

    /**
     * Constructor for Thief characters.
     *
     * @param theName class name of Thief characters
     * @param theDefaultHealth starting health of Thief characters
     * @param theMinDamage minimum damage dealt by Thief characters
     * @param theMaxDamage maximum damage dealt by Thief characters
     * @param theAttackSpeed attack speed of Thief characters
     * @param theAccuracy attack accuracy of Thief characters
     * @param theBlockChance chance to block of Thief characters
     */
    public Thief(final String theName,
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
