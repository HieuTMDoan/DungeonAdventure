package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.monsters.Gremlin;
import com.tcss.dungeonadventure.objects.monsters.Ogre;
import com.tcss.dungeonadventure.objects.monsters.Skeleton;

public class MonsterFactory implements CharacterFactory {
    @Override
    public DungeonCharacter createCharacter() {
        // Define an array of monster classes to choose from.
        final Class<?>[] monsterClasses = new Class[]{Gremlin.class, Ogre.class, Skeleton.class};

        // Use the Helper class to get a random index for the monsterClasses array.
        final int randomIndex = Helper.getRandomIntBetween(0, monsterClasses.length);

        // Create an instance of the selected monster type.
        try {
            final Class<?> selectedMonsterClass = monsterClasses[randomIndex];
            final DungeonCharacter monster = (DungeonCharacter) selectedMonsterClass.getDeclaredConstructor().newInstance();

            // Set initial health for the monster
            monster.setHealth(100);

            return monster;
        } catch (final ReflectiveOperationException e) {
            e.printStackTrace();
            throw new RuntimeException("Randomly generated monster threw an exception.", e);
        }
    }
}
