package com.tcss.dungeonadventure.objects;

import com.tcss.dungeonadventure.Helper;


public abstract class DungeonCharacter implements VisualComponent {

    private final char myDisplayChar;
    private final String myName;
    private final int myMaxHealthPoints;
    private final int myMinDamage;
    private final int myMaxDamage;
    private final int myAttackSpeed;
    private final double myAccuracy; // same as hit rate

    private int myHealthPoints;

    public DungeonCharacter(final String theName,
                            final char theDisplayChar,
                            final int theDefaultHealth,
                            final int theMinDamage,
                            final int theMaxDamage,
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

    public void attack(final DungeonCharacter theTarget) {
        double randomAccuracy = Helper.getRandomDoubleBetween(0, 1);

        if (this.myAccuracy > randomAccuracy) {
            int damage = Helper.getRandomIntBetween(myMinDamage + 1, myMaxDamage);
            theTarget.changeHealth(damage);
            System.out.println(theTarget.getName() + " lost " + damage + " health");
        }
    }


    public String getName() {
        return this.myName;
    }

    @Override
    public char getDisplayChar() {
        return myDisplayChar;
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
                          """, myName, myHealthPoints, myMaxHealthPoints, myMinDamage, myMaxDamage, myAttackSpeed, myAccuracy

        );
    }

    public int getHealth() {
        return this.myHealthPoints;
    }
    public int getMaxHealthPoints() {
        return this.myMaxHealthPoints;
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

    public double getMinDamage() {
        return myMinDamage;
    }

    public double getMaxDamage() {
        return myMaxDamage;
    }

}
