package com.tcss.dungeonadventure.model.factories;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.SQLiteDB;
import com.tcss.dungeonadventure.objects.monsters.Gremlin;
import com.tcss.dungeonadventure.objects.monsters.Monster;
import com.tcss.dungeonadventure.objects.monsters.Ogre;
import com.tcss.dungeonadventure.objects.monsters.Skeleton;
import java.util.Map;

/**
 * Represents the manufacturer of {@link Monster} characters.
 * This class works with {@link SQLiteDB} to retrieve
 * {@link Monster} stats during the production process.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public final class MonsterFactory {

    /**
     * Default empty constructor.
     * There should be no use to instantiate an object of this type.
     */
    private MonsterFactory() {

    }

    /**
     * Returns a new {@link Monster} character
     * based on the {@link Helper.Characters} enum
     * that represents the returned character.
     *
     * @param theCharacter the {@link Helper.Characters} enum
     *                     that represents the character to be created.
     * @return The new {@link Monster} character
     */
    public static Monster createCharacter(final Helper.Characters theCharacter) {
        final Map<SQLiteDB.Keys, Object> dataMap = SQLiteDB.getCharacterByName(theCharacter);
        switch (theCharacter) {
            case GREMLIN -> {
                return new Gremlin(
                        (String) dataMap.get(SQLiteDB.Keys.NAME),
                        (Character) dataMap.get(SQLiteDB.Keys.DISPLAY_CHAR),
                        (Integer) dataMap.get(SQLiteDB.Keys.HEALTH),
                        (Integer) dataMap.get(SQLiteDB.Keys.DAMAGE_MIN),
                        (Integer) dataMap.get(SQLiteDB.Keys.DAMAGE_MAX),
                        (Integer) dataMap.get(SQLiteDB.Keys.ATTACK_SPEED),
                        (Double) dataMap.get(SQLiteDB.Keys.ACCURACY),
                        (Double) dataMap.get(SQLiteDB.Keys.HEAL_CHANCE),
                        (Integer) dataMap.get(SQLiteDB.Keys.HEAL_MIN),
                        (Integer) dataMap.get(SQLiteDB.Keys.HEAL_MAX));

            }
            case SKELETON -> {
                return new Skeleton(
                        (String) dataMap.get(SQLiteDB.Keys.NAME),
                        (Character) dataMap.get(SQLiteDB.Keys.DISPLAY_CHAR),
                        (Integer) dataMap.get(SQLiteDB.Keys.HEALTH),
                        (Integer) dataMap.get(SQLiteDB.Keys.DAMAGE_MIN),
                        (Integer) dataMap.get(SQLiteDB.Keys.DAMAGE_MAX),
                        (Integer) dataMap.get(SQLiteDB.Keys.ATTACK_SPEED),
                        (Double) dataMap.get(SQLiteDB.Keys.ACCURACY),
                        (Double) dataMap.get(SQLiteDB.Keys.HEAL_CHANCE),
                        (Integer) dataMap.get(SQLiteDB.Keys.HEAL_MIN),
                        (Integer) dataMap.get(SQLiteDB.Keys.HEAL_MAX));

            }
            case OGRE -> {
                return new Ogre(
                        (String) dataMap.get(SQLiteDB.Keys.NAME),
                        (Character) dataMap.get(SQLiteDB.Keys.DISPLAY_CHAR),
                        (Integer) dataMap.get(SQLiteDB.Keys.HEALTH),
                        (Integer) dataMap.get(SQLiteDB.Keys.DAMAGE_MIN),
                        (Integer) dataMap.get(SQLiteDB.Keys.DAMAGE_MAX),
                        (Integer) dataMap.get(SQLiteDB.Keys.ATTACK_SPEED),
                        (Double) dataMap.get(SQLiteDB.Keys.ACCURACY),
                        (Double) dataMap.get(SQLiteDB.Keys.HEAL_CHANCE),
                        (Integer) dataMap.get(SQLiteDB.Keys.HEAL_MIN),
                        (Integer) dataMap.get(SQLiteDB.Keys.HEAL_MAX));
            }
            default ->
                    throw new IllegalArgumentException(
                            "Cannot instantiate non-monsters with MonsterFactory");
        }
    }
}
