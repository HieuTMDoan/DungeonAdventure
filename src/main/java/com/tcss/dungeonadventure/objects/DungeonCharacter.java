package com.tcss.dungeonadventure.objects;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.PCS;

import java.io.Serializable;


public abstract class DungeonCharacter implements VisualComponent, Serializable {

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

    public int attack(final DungeonCharacter theTarget) {
        final double randomAccuracy = Helper.getRandomDoubleBetween(0, 1);
        int damageDealth = 0;

        if (this.myAccuracy >= randomAccuracy) {
            final int damage = Helper.getRandomIntBetween(myMinDamage, myMaxDamage);
            damageDealth = damage;
            theTarget.changeHealth(-damage);
        }
        return damageDealth;
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
     * Inflicts damage to the character.
     *
     * @param theDamage The amount of damage to be inflicted.
     */
    public void takeDamage(final int theDamage) {
        // Subtract the damage from health
        this.setHealth(this.getHealth() - theDamage);

        // Check if the character is defeated
        if (this.isDefeated()) {
            System.out.println(this.getName() + " has been defeated!");
        }
    }

    /**
     * Calculates the damage that the character can deal in an attack.
     *
     * @return The calculated damage.
     */
    public int calculateDamage() {
        return Helper.getRandomIntBetween(this.getMinDamage() + 1, this.getMaxDamage());
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

