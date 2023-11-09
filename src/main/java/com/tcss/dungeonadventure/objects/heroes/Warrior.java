package com.tcss.dungeonadventure.objects.heroes;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.skills.Skill;

public class Warrior extends Hero {
    public Warrior(final String theName,
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
                theAccuracy,
                theBlockChance,
                theSkill);
    }
    //TODO: Isn't this override redundant
    // since it's basically calling its parent method to do the work,
    // which is what it would do anyway even without overriding the parent method?
    // also, having this override makes the parent method always to be inverted.
    // Not sure what that meant, but it doesn't sound right.
//    @Override
//    public boolean useSkill(final DungeonCharacter theTarget) {
//        return super.useSkill(this);
//    }
}
