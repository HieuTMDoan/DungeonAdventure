package com.tcss.dungeonadventure.objects.heroes;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.skills.Heal;
import com.tcss.dungeonadventure.objects.skills.Skill;

public class Priestess extends Hero {
    private static final int DEFAULT_HIT_POINTS = 75;
    private static final int DEFAULT_ATTACK_SPEED = 5;
    private static final double DEFAULT_ACCURACY = 0.7;
    private static final int DEFAULT_MIN_DAMAGE = 25;
    private static final int DEFAULT_MAX_DAMAGE = 45;
    private static final double DEFAULT_BLOCK_CHANCE = 0.3;
    private static final Skill PRIESTESS_SKILL = new Heal();

    public Priestess(final String theName) {

        super(theName,
                DEFAULT_HIT_POINTS,
                DEFAULT_MIN_DAMAGE,
                DEFAULT_MAX_DAMAGE,
                DEFAULT_ATTACK_SPEED,
                DEFAULT_ACCURACY,
                DEFAULT_BLOCK_CHANCE,
                PRIESTESS_SKILL);
    }

    @Override
    public boolean useSkill(final DungeonCharacter theTarget) {
        return super.useSkill(this);
    }


}
