package com.tcss.dungeonadventure.objects.heroes;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.skills.Skill;


/**
 * A child of {@link DungeonCharacter} to represent playable characters.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public abstract class Hero extends DungeonCharacter {


    /**
     * The chance to block an incoming attack.
     */
    private final double myBlockChance;

    /**
     * The skill of the hero.
     */
    private final Skill mySkill;

    /**
     * Constructs a new hero with the specified attributes.
     *
     * @param theName          The name of the hero.
     * @param theDisplayChar   The character used to display the hero.
     * @param theDefaultHealth The default health points of the hero.
     * @param theMinDamage     The minimum damage the hero can deal.
     * @param theMaxDamage     The maximum damage the hero can deal.
     * @param theAttackSpeed   The attack speed of the hero.
     * @param theAccuracy      The accuracy of the hero's attacks.
     * @param theBlockChance   The chance of blocking incoming attacks.
     * @param theSkill         The special skill of the hero.
     */
    public Hero(final String theName,
                final char theDisplayChar,
                final int theDefaultHealth,
                final int theMinDamage,
                final int theMaxDamage,
                final int theAttackSpeed,
                final double theAccuracy,
                final double theBlockChance,
                final Skill theSkill) {

        super(theName,
                theDisplayChar,
                theDefaultHealth,
                theMinDamage,
                theMaxDamage,
                theAttackSpeed,
                theAccuracy);

        this.myBlockChance = theBlockChance;
        this.mySkill = theSkill;
    }

    /**
     * Gets the block chance of the hero.
     *
     * @return The block chance as an integer.
     */
    public double getBlockChance() {
        return myBlockChance;
    }

    /**
     * Gets the special skill of the hero.
     *
     * @return The skill associated with the hero.
     */
    public Skill getSkill() {
        return mySkill;
    }

    /**
     * Activates the hero's special skill on a target.
     *
     * @param theTarget The target of the hero's skill.
     */
    public void useSkill(final DungeonCharacter theTarget) {
        mySkill.activateSkill(this, theTarget);
    }

    /**
     * Gets the tile color associated with the hero.
     *
     * @return The color of the hero's tile, represented as a string.
     */
    @Override
    public String getTileColor() {
        return "green";
    }

    /**
     * Gets the attack speed of the hero.
     *
     * @return The attack speed of the hero.
     */
    @Override
    public int getAttackSpeed() {
        return super.getAttackSpeed();
    }
}
