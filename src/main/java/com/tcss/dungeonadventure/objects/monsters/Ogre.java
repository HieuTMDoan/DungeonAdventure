package com.tcss.dungeonadventure.objects.monsters;


import com.tcss.dungeonadventure.objects.TileChars;

public class Ogre extends Monster {

    private static final int DEFAULT_HIT_POINTS = 200;
    private static final int DEFAULT_ATTACK_SPEED = 2;
    private static final double DEFAULT_ACCURACY = 0.6;
    private static final int DEFAULT_MIN_DAMAGE = 30;
    private static final int DEFAULT_MAX_DAMAGE = 60;
    private static final double DEFAULT_HEAL_CHANCE = 0.1;
    private static final int DEFAULT_MIN_HEAL = 30;
    private static final int DEFAULT_MAX_HEAL = 60;

    public Ogre() {
        super(Ogre.class.getSimpleName(),
                TileChars.Monsters.OGRE,
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
