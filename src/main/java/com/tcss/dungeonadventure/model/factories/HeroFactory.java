package com.tcss.dungeonadventure.model.factories;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.SQLiteDB;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.heroes.Priestess;
import com.tcss.dungeonadventure.objects.heroes.Thief;
import com.tcss.dungeonadventure.objects.heroes.Warrior;
import java.util.Map;

/**
 * Represents the manufacturer of {@link Hero} characters.
 * This class works with {@link SQLiteDB} to retrieve
 * {@link Hero} stats during the production process.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public final class HeroFactory {

    /**
     * Default empty constructor.
     * There should be no use to instantiate an object of this type.
     */
    private HeroFactory() { }

    /**
     * Returns a new {@link Hero} character
     * based on the {@link Helper.Characters} enum
     * that represents the returned character.
     *
     * @param theCharacter the {@link Helper.Characters} enum
     *                     that represents the character to be created.
     * @return The new {@link Hero} character
     */
    public static Hero createCharacter(final Helper.Characters theCharacter) {
        final Map<SQLiteDB.Keys, Object> dataMap = SQLiteDB.getCharacterByName(theCharacter);
        switch (theCharacter) {
            case WARRIOR -> {
                return new Warrior(
                        (String) dataMap.get(SQLiteDB.Keys.NAME),
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