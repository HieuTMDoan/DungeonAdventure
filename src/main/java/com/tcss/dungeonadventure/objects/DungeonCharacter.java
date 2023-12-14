package com.tcss.dungeonadventure.objects;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import java.io.Serializable;


/**
 * An abstract class to be the parent of all characters in the dungeon.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public abstract class DungeonCharacter extends VisualComponent implements Serializable {

    /**
     * The name of the character.
     */
    private final String myName;
    /**
     * The max health of the character.
     */
    private final int myMaxHealthPoints;
    /**
     * The minimum attack-damage of the character.
     */
    private final int myMinDamage;

    /**
     * The maximum attack-damage of the character.
     */
    private final int myMaxDamage;

    /**
     * The attack speed of the character.
     */
    private final int myAttackSpeed;

    /**
     * The accuracy of the character.
     */
    private final double myAccuracy;

    /**
     * The last object that damaged the character.
     */
    private Object myLastDamageSource;

    /**
     * The current health points of the character.
     */
    private int myHealthPoints;


    /**
     * Constructs a new DungeonCharacter based on the provided stats.
     *
     * @param theName The name of the DungeonCharacter.
     * @param theDisplayChar The display character of the DungeonCharacter.
     * @param theDefaultHealth The default max health of the DungeonCharacter.
     * @param theMinDamage The minimum damage of the DungeonCharacter.
     * @param theMaxDamage The maximum damage of the DungeonCharacter.
     * @param theAttackSpeed The attack speed of the DungeonCharacter.
     * @param theAccuracy The accuracy of the DungeonCharacter.
     */
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

    /**
     * Attacks the target, using the specified stats to calculate damage.
     * If the target is a hero, roll a chance to block the attack. If the attack is blocked,
     * this will return null. If the damage is 0, that means the attack missed.
     *
     * @param theTarget The target to attack.
     * @return Null if the attack was blocked, 0 if the attack missed, or the damage dealt.
     */
    public Integer attack(final DungeonCharacter theTarget) {
        final double randomAccuracy = Helper.getRandomDoubleBetween(0, 1);
        int damageDealt = 0;

        if (this.myAccuracy >= randomAccuracy) {
            if (this instanceof Hero
                    && DungeonAdventure.getInstance().getPlayer().isInvincible()) {

                damageDealt = 1000;
            } else {
                damageDealt = Helper.getRandomIntBetween(myMinDamage, myMaxDamage);
            }


            if (theTarget instanceof final Hero hero) {
                if (DungeonAdventure.getInstance().getPlayer().isInvincible()
                        || Helper.getRandomDoubleBetween(0, 1) < hero.getBlockChance()) {
                    return null;
                }
            }

            theTarget.changeHealth(this, -damageDealt);
        }
        return damageDealt;
    }


    /**
     * @return The name of the DungeonCharacter.
     */
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

    /**
     * @return The health of the DungeonCharacter.
     */
    public int getHealth() {
        return this.myHealthPoints;
    }

    /**
     * Sets the health of the DungeonCharacter. If the new health is above the max health,
     * set the health to the max health. If the health is below 0, set the health to 0.
     *
     * @param theNewHealth The health to set the DungeonCharacter to.
     */
    public void setHealth(final int theNewHealth) {
        if (theNewHealth > this.myMaxHealthPoints) {
            this.myHealthPoints = this.myMaxHealthPoints;
            return;
        }
        this.myHealthPoints = Math.max(theNewHealth, 0);
    }

    /**
     * @return The max health of the DungeonCharacter.
     */
    public int getMaxHealth() {
        return this.myMaxHealthPoints;
    }

    /**
     * Changes the DungeonCharacter's health by a set amount.
     *
     * @param theChangeInHealth The amount to change the health by.
     */
    public void changeHealth(final int theChangeInHealth) {
        this.setHealth(this.myHealthPoints + theChangeInHealth);
    }

    /**
     * Changes the DungeonCharacter's health by a set amount, as well as setting the last
     * damage source to this DungeonCharacter.
     *
     * @param theSource The source of the change in health.
     * @param theChangeInHealth The amount to change the health by.
     */
    public void changeHealth(final Object theSource, final int theChangeInHealth) {
        if (theSource != null) {
            myLastDamageSource = theSource;
        }

        changeHealth(theChangeInHealth);
    }

    /**
     * @return The last source of damage.
     */
    public Object getLastDamageSource() {
        return this.myLastDamageSource;
    }

    /**
     * @return The minimum damage on an attack.
     */
    public int getMinDamage() {
        return myMinDamage;
    }

    /**
     * @return The maximum damage on an attack.
     */
    public int getMaxDamage() {
        return myMaxDamage;
    }

    /**
     * @return The attack speed of the DungeonCharacter.
     */
    public int getAttackSpeed() {
        return myAttackSpeed;
    }

    /**
     * @return The accuracy of the DungeonCharacter.
     */
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

