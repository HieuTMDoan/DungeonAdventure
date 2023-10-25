package com.tcss.dungeonadventure.objects.monsters;


import com.tcss.dungeonadventure.objects.TileChars;

public class Gremlin extends Monster {


    private static final int DEFAULT_HIT_POINTS = 70;
    private static final int DEFAULT_ATTACK_SPEED = 5;
    private static final double DEFAULT_ACCURACY = 0.8;
    private static final int DEFAULT_MIN_DAMAGE = 15;
    private static final int DEFAULT_MAX_DAMAGE = 30;
    private static final double DEFAULT_HEAL_CHANCE = 0.4;
    private static final int DEFAULT_MIN_HEAL = 20;
    private static final int DEFAULT_MAX_HEAL = 40;

    public Gremlin() {
        super(Gremlin.class.getName(),
                TileChars.Monsters.GREMLIN,
                DEFAULT_HIT_POINTS,
                DEFAULT_MIN_DAMAGE,
                DEFAULT_MAX_DAMAGE,
                DEFAULT_ATTACK_SPEED,
                DEFAULT_ACCURACY,
                DEFAULT_HEAL_CHANCE,
                DEFAULT_MIN_HEAL,
                DEFAULT_MAX_HEAL);

    }
}
