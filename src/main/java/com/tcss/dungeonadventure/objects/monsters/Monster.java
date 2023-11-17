package com.tcss.dungeonadventure.objects.monsters;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.DungeonCharacter;

public abstract class Monster extends DungeonCharacter {

    /**
     * The chance to heal.
     */
    private final double myHealChance;

    /**
     * The minimum heal amount.
     */
    private final int myMinHeal;

    /**
     * The maximum heal amount.
     */
    private final int myMaxHeal;

    public Monster(final String theName,
                   final char theDisplayChar,
                   final int theDefaultHealth,
                   final int theMinDamage,
                   final int theMaxDamage,
                   final int theAttackSpeed,
                   final double theAccuracy,
                   final double theHealChance,
                   final int theMinHeal,
                   final int theMaxHeal) {
        super(theName,
                theDisplayChar,
                theDefaultHealth,
                theMinDamage,
                theMaxDamage,
                theAttackSpeed,
                theAccuracy);

        this.myHealChance = theHealChance;
        this.myMinHeal = theMinHeal;
        this.myMaxHeal = theMaxHeal;
    }

    @Override
    public void changeHealth(final int theChangeInHealth) {
        super.changeHealth(theChangeInHealth);

        if (this.getHealth() > 0) {
            heal();
        }

    }

    private void heal() {
        if (Helper.getRandomDoubleBetween(0, 1) < this.myHealChance) {
            super.changeHealth(Helper.getRandomIntBetween(myMinHeal, myMaxHeal));
        }
    }


}
