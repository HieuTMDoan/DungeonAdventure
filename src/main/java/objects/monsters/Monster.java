package objects.monsters;

import objects.DungeonCharacter;

public abstract class Monster extends DungeonCharacter {

    private final double myHealChance;
    private final int myHealAmount;

    public Monster(final String theName,
                   final char theDisplayChar,
                   final int theDefaultHealth,
                   final double theMinDamage,
                   final double theMaxDamage,
                   final double theAttackSpeed,
                   final double theAccuracy,
                   final double theHealChance,
                   final int theHealAmount) {
        super(theName,
                theDisplayChar,
                theDefaultHealth,
                theMinDamage,
                theMaxDamage,
                theAttackSpeed,
                theAccuracy);

        this.myHealChance = theHealChance;
        this.myHealAmount = theHealAmount;
    }

    @Override
    public void changeHealth(final int theChangeInHealth) {
        super.changeHealth(theChangeInHealth);

        if (this.getHealth() > 0) {
            heal();
        }

    }

    private void heal() {
        // TODO: implement heal
    }


}
