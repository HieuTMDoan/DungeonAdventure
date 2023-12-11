package com.tcss.dungeonadventure.objects.monsters;

import com.tcss.dungeonadventure.objects.TileChars;

public class Gremlin extends Monster {
    public Gremlin(final String theName,
                   final int theHealth,
                   final int theDamageMin,
                   final int theDamageMax,
                   final int theAttackSpeed,
                   final double theAccuracy,
                   final double theHealChance,
                   final int theHealMin,
                   final int theHealMax) {
        super(theName,
                TileChars.Monster.GREMLIN,
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
