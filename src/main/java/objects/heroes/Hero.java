package objects.heroes;

import objects.DungeonCharacter;
import objects.TileChars;
import objects.skills.Skill;

public abstract class Hero extends DungeonCharacter {

    private final double myBlockChance;

    private final Skill mySkill;


    public Hero(final String theName,
                final int theDefaultHealth,
                final double theMinDamage,
                final double theMaxDamage,
                final int theAttackSpeed,
                final double theAccuracy,
                final double theBlockChance,
                final Skill theSkill) {

        super(theName,
                TileChars.Player.PLAYER,
                theDefaultHealth,
                theMinDamage,
                theMaxDamage,
                theAttackSpeed,
                theAccuracy);

        this.myBlockChance = theBlockChance;
        this.mySkill = theSkill;
    }

    public boolean useSkill(final DungeonCharacter theTarget) {
        return mySkill.activateSkill(theTarget);
    }


}
