package com.tcss.dungeonadventure.objects;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import java.io.Serializable;


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
        int damageDealth = 0;

        if (this.myAccuracy >= randomAccuracy) {
            if (this instanceof Hero && DungeonAdventure.getInstance().getPlayer().isInvincible()) {
                damageDealth = 1000;
            } else {
                damageDealth = Helper.getRandomIntBetween(myMinDamage, myMaxDamage);
            }


            if (theTarget instanceof final Hero hero) {
                if (Helper.getRandomDoubleBetween(0, 1) < hero.getBlockChance()) {
                    return null;
                }
            }

            theTarget.changeHealth(this, -damageDealth);
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

