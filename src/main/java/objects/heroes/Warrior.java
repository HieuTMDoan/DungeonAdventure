package objects.heroes;

import objects.skills.CrushingBlow;
import objects.skills.Skill;

public class Warrior extends Hero {

    private static final int DEFAULT_HIT_POINTS = 125;

    private static final int DEFAULT_ATTACK_SPEED = 4;

    private static final double DEFAULT_ACCURACY = 0.8;

    private static final int DEFAULT_MIN_DAMAGE = 35;

    private static final int DEFAULT_MAX_DAMAGE = 60;

    private static final double DEFAULT_BLOCK_CHANCE = 0.2;

    private static final Skill WARRIOR_SKILL = new CrushingBlow();


    public Warrior(final String theName) {

        super(theName,
                DEFAULT_HIT_POINTS,
                DEFAULT_MIN_DAMAGE,
                DEFAULT_MAX_DAMAGE,
                DEFAULT_ATTACK_SPEED,
                DEFAULT_ACCURACY,
                DEFAULT_BLOCK_CHANCE,
                WARRIOR_SKILL);


    }






}
