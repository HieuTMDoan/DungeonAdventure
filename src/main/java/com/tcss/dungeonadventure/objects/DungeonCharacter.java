package com.tcss.dungeonadventure.objects;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.PCS;

import java.io.Serializable;


public abstract class DungeonCharacter extends VisualComponent implements Serializable {

    private final String myName;
    private final int myMaxHealthPoints;
    private final int myMinDamage;
    private final int myMaxDamage;
    private final int myAttackSpeed;
    private final double myAccuracy; // same as hit rate

    private Object myLastDamageSource;
    private int myHealthPoints;

    public DungeonCharacter(final String theName,
                            final char theDisplayChar,
                            final int theDefaultHealth,
                            final int theMinDamage,
                            final int theMaxDamage,
                            final int theAttackSpeed,
                            final double theAccuracy) {

        super(theDisplayChar);
        this.myName = theName;
        this.myHealthPoints = theDefaultHealth;
        this.myMaxHealthPoints = theDefaultHealth;
        this.myMinDamage = theMinDamage;
        this.myMaxDamage = theMaxDamage;
        this.myAttackSpeed = theAttackSpeed;
        this.myAccuracy = theAccuracy;
    }

    public int attack(final DungeonCharacter theTarget) {
        final double randomAccuracy = Helper.getRandomDoubleBetween(0, 1);
        int damageDealth = 0;

        if (this.myAccuracy >= randomAccuracy) {
            final int damage = Helper.getRandomIntBetween(myMinDamage, myMaxDamage);
            damageDealth = damage;
            theTarget.changeHealth(this, -damage);
        }
        return damageDealth;
    }


    public String getName() {
        return this.myName;
    }

    @Override
    public String getDescription() {
        return String.format(
                """
                        Name: %s
                        Health: %s/%s
                        Damage Range: %s - %s
                        Attack Speed: %s
                        Accuracy: %s
                          """,
                myName,
                myHealthPoints,
                myMaxHealthPoints,
                myMinDamage,
                myMaxDamage,
                myAttackSpeed,
                myAccuracy

        );
    }

    public int getHealth() {
        return this.myHealthPoints;
    }

    public void setHealth(final int theNewHealth) {
        if (theNewHealth > this.myMaxHealthPoints) {
            this.myHealthPoints = this.myMaxHealthPoints;
            return;
        }
        this.myHealthPoints = Math.max(theNewHealth, 0);
    }

    public int getMaxHealth() {
        return this.myMaxHealthPoints;
    }

    public void changeHealth(final int theChangeInHealth) {
        this.setHealth(this.myHealthPoints + theChangeInHealth);
    }

    public void changeHealth(final Object theSource, final int theChangeInHealth) {
        if (theSource != null) {
            myLastDamageSource = theSource;
        }

        changeHealth(theChangeInHealth);
    }

    public Object getLastDamageSource() {
        return this.myLastDamageSource;
    }

    public int getMinDamage() {
        return myMinDamage;
    }

    public int getMaxDamage() {
        return myMaxDamage;
    }

    public int getAttackSpeed() {
        return myAttackSpeed;
    }

    public double getAccuracy() {
        return myAccuracy;
    }

    /**
     * Checks if the character is defeated.
     *
     * @return True if the character is defeated, false otherwise.
     */
    public boolean isDefeated() {
        return this.getHealth() <= 0;
    }
}

