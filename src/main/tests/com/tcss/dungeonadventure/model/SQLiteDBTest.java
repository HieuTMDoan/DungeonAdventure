package com.tcss.dungeonadventure.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.tcss.dungeonadventure.Helper.Characters;
import static com.tcss.dungeonadventure.model.SQLiteDB.Keys;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SQLiteDBTest {

    @Test
    void getCharacterByName() {
        for (Characters type : Characters.values()) {
            Map<Keys, Object> character = SQLiteDB.getCharacterByName(type);

            if (type.ordinal() == 0
                    || type.ordinal() == 1
                    || type.ordinal() == 2) {
                assertEquals(0.0, character.get(Keys.HEAL_CHANCE));
            } else {
                assertEquals(0.0, character.get(Keys.BLOCK_CHANCE));
            }
        }
    }
}