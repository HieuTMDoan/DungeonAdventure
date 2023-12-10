package com.tcss.dungeonadventure.objects.heroes;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.skills.Skill;

public abstract class Hero extends DungeonCharacter {


    private final double myBlockChance;
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

    public boolean useSkill(final DungeonCharacter theTarget) {
        return mySkill.activateSkill(theTarget);
    }

    @Override
    public String getTileColor() {
        return "green";
    }
}
