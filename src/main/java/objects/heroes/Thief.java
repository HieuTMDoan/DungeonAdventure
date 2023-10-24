package objects.heroes;

import objects.skills.Skill;
import objects.skills.SurpriseAttack;

public class Thief extends Hero {
    private static final int DEFAULT_HIT_POINTS = 75;

    private static final int DEFAULT_ATTACK_SPEED = 6;

    private static final double DEFAULT_ACCURACY = 0.8;

    private static final int DEFAULT_MIN_DAMAGE = 20;

    private static final int DEFAULT_MAX_DAMAGE = 40;

    private static final double DEFAULT_BLOCK_CHANCE = 0.4;

    private static final Skill THIEF_SKILL = new SurpriseAttack();



    public Thief(final String theName) {

        super(theName,
                DEFAULT_HIT_POINTS,
                DEFAULT_MIN_DAMAGE,
                DEFAULT_MAX_DAMAGE,
                DEFAULT_ATTACK_SPEED,
                DEFAULT_ACCURACY,
                DEFAULT_BLOCK_CHANCE,
                THIEF_SKILL);


    }










}
