package com.tcss.dungeonadventure.objects.monsters;

public class Gremlin extends Monster {
    public Gremlin(final String theName,
                   final char theDisplayChar,
                   final int theHealth,
                   final int theDamageMin,
                   final int theDamageMax,
                   final int theAttackSpeed,
                   final double theAccuracy,
                   final double theHealChance,
                   final int theHealMin,
                   final int theHealMax) {
        super(theName,
                theDisplayChar,
                theHealth,
                theDamageMin,
                theDamageMax,
                theAttackSpeed,
                theAccuracy,
                theHealChance,
                theHealMin,
                theHealMax);
    }
}
