package com.tcss.dungeonadventure.objects;

public abstract class DungeonCharacter {

    private final char myDisplayChar;
    private final String myName;
    private final int myMaxHealthPoints;
    private final double myMinDamage;
    private final double myMaxDamage;
    private final int myAttackSpeed;
    private final double myAccuracy;

    private int myHealthPoints;

    public DungeonCharacter(final String theName,
                            final char theDisplayChar,
                            final int theDefaultHealth,
                            final double theMinDamage,
                            final double theMaxDamage,
                            final int theAttackSpeed,
                            final double theAccuracy) {

        this.myName = theName;
        this.myDisplayChar = theDisplayChar;
        this.myHealthPoints = theDefaultHealth;
        this.myMaxHealthPoints = theDefaultHealth;
        this.myMinDamage = theMinDamage;
        this.myMaxDamage = theMaxDamage;
        this.myAttackSpeed = theAttackSpeed;
        this.myAccuracy = theAccuracy;
    }

    public boolean attack(final DungeonCharacter theTarget) {
        return false;
    }


    public char getDisplayChar() {
        return myDisplayChar;
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

    public void changeHealth(final int theChangeInHealth) {
        this.setHealth(this.myHealthPoints + theChangeInHealth);
    }


}
