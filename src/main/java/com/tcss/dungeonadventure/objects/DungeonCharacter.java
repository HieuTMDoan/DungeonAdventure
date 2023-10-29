package com.tcss.dungeonadventure.objects;
import java.util.*;

public abstract class DungeonCharacter implements VisualComponent {

    private final char myDisplayChar;
    private final String myName;
    private final int myMaxHealthPoints;
    private final double myMinDamage;
    private final double myMaxDamage;
    private final int myAttackSpeed;
    private final int myHitRate;
    private static final int RAND_UPPERBOUND = 100;
    private static final Random myRand = new Random();
    private final double myAccuracy;
    private int myHealthPoints;
    private DungeonCharacter theTarget;

    public DungeonCharacter(final String theName,
                            final char theDisplayChar,
                            final int theDefaultHealth,
                            final double theMinDamage,
                            final double theMaxDamage,
                            final int theAttackSpeed,
                            final int theHitRate,
                            final double theAccuracy) {

        this.myName = theName;
        this.myDisplayChar = theDisplayChar;
        this.myHealthPoints = theDefaultHealth;
        this.myMaxHealthPoints = theDefaultHealth;
        this.myMinDamage = theMinDamage;
        this.myMaxDamage = theMaxDamage;
        this.myAttackSpeed = theAttackSpeed;
        this.myAccuracy = theAccuracy;
        this.myHitRate = theHitRate;
    }

    public void attack(final DungeonCharacter theTarget) {
        this.theTarget = theTarget;
        if ((myHitRate * 10) > myRand.nextInt(RAND_UPPERBOUND)) {
            double damage = myRand.nextInt((int) (((myMinDamage + 1)) - myMaxDamage)) + myMinDamage;
            theTarget.setHealth((int) (theTarget.getHealth() - damage));
            System.out.println(theTarget.getClass().getSimpleName() + " lost " + damage + " health");
        }
    }

    @Override
    public char getDisplayChar() {
        return myDisplayChar;
    }

    @Override
    public String getDescription() {
        return "Name: " + this.myName;
    }

    public int getHealth() {
        return this.myHealthPoints;
    }
    public int getMaxHealthPoints() { return this.myMaxHealthPoints;
    }
    public String getName() { return this.myName;
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
    public static int generateRandomInt() {
        return myRand.nextInt(RAND_UPPERBOUND + 1);
    }
    public double getMaxDamage() {
        return myMaxDamage;
    }

}
