package com.tcss.dungeonadventure.objects.monsters;


import com.tcss.dungeonadventure.objects.TileChars;

public class Skeleton extends Monster {
    private static final int DEFAULT_HIT_POINTS = 100;
    private static final int DEFAULT_ATTACK_SPEED = 3;
    private static final double DEFAULT_ACCURACY = 0.8;
    private static final int DEFAULT_MIN_DAMAGE = 30;
    private static final int DEFAULT_MAX_DAMAGE = 50;
    private static final double DEFAULT_HEAL_CHANCE = 0.3;
    private static final int DEFAULT_MIN_HEAL = 30;
    private static final int DEFAULT_MAX_HEAL = 50;


    public Skeleton() {
        super(Skeleton.class.getSimpleName(),
                TileChars.Monsters.SKELETON,
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
