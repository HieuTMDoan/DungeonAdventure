package com.tcss.dungeonadventure.objects.heroes;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.skills.Skill;

public class Priestess extends Hero {
    public Priestess(final String theName,
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

}
