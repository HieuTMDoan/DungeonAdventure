package com.tcss.dungeonadventure.objects.heroes;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.skills.Skill;

public abstract class Hero extends DungeonCharacter {


    /**
     * The chance to block an incoming attack.
     */
    private final double myBlockChance;

    /**
     * The skill of the hero.
     */
    private final Skill mySkill;

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

    public int getBlockChance() {
        return (int) myBlockChance;
    }

    public Integer useSkill(final DungeonCharacter theTarget) {
        return mySkill.activateSkill(theTarget);
    }

    public Skill getSkill() {
        return mySkill;
    }

    @Override
    public String getTileColor() {
        return "green";
    }
}
