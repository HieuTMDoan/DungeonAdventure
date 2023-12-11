package com.tcss.dungeonadventure.model.factories;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.SQLiteDB;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.heroes.Priestess;
import com.tcss.dungeonadventure.objects.heroes.Thief;
import com.tcss.dungeonadventure.objects.heroes.Warrior;
import java.util.Map;

public final class HeroFactory  {

    private HeroFactory() {

    }

    public static Hero createCharacter(final Helper.Characters theCharacter) {
        final Map<SQLiteDB.Keys, Object> dataMap = SQLiteDB.getCharacterByName(theCharacter);
        switch (theCharacter) {
            case WARRIOR -> {
                return new Warrior(
                        (String) dataMap.get(SQLiteDB.Keys.NAME),
                        (Character) dataMap.get(SQLiteDB.Keys.DISPLAY_CHAR),
                        (Integer) dataMap.get(SQLiteDB.Keys.HEALTH),
                        (Integer) dataMap.get(SQLiteDB.Keys.DAMAGE_MIN),
                        (Integer) dataMap.get(SQLiteDB.Keys.DAMAGE_MAX),
                        (Integer) dataMap.get(SQLiteDB.Keys.ATTACK_SPEED),
                        (Double) dataMap.get(SQLiteDB.Keys.ACCURACY),
                        (Double) dataMap.get(SQLiteDB.Keys.BLOCK_CHANCE));

            }
            case PRIESTESS -> {
                return new Priestess(
                        (String) dataMap.get(SQLiteDB.Keys.NAME),
                        (Character) dataMap.get(SQLiteDB.Keys.DISPLAY_CHAR),
                        (Integer) dataMap.get(SQLiteDB.Keys.HEALTH),
                        (Integer) dataMap.get(SQLiteDB.Keys.DAMAGE_MIN),
                        (Integer) dataMap.get(SQLiteDB.Keys.DAMAGE_MAX),
                        (Integer) dataMap.get(SQLiteDB.Keys.ATTACK_SPEED),
                        (Double) dataMap.get(SQLiteDB.Keys.ACCURACY),
                        (Double) dataMap.get(SQLiteDB.Keys.BLOCK_CHANCE));

            }
            case THIEF -> {
                return new Thief(
                        (String) dataMap.get(SQLiteDB.Keys.NAME),
                        (Character) dataMap.get(SQLiteDB.Keys.DISPLAY_CHAR),
                        (Integer) dataMap.get(SQLiteDB.Keys.HEALTH),
                        (Integer) dataMap.get(SQLiteDB.Keys.DAMAGE_MIN),
                        (Integer) dataMap.get(SQLiteDB.Keys.DAMAGE_MAX),
                        (Integer) dataMap.get(SQLiteDB.Keys.ATTACK_SPEED),
                        (Double) dataMap.get(SQLiteDB.Keys.ACCURACY),
                        (Double) dataMap.get(SQLiteDB.Keys.BLOCK_CHANCE));

            }
            default ->
                    throw new IllegalArgumentException(
                            "Cannot instantiate non-heroes with HeroFactory");
        }
    }
}