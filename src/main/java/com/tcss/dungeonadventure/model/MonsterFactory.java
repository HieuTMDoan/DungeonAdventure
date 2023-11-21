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
        Class<?>[] monsterClasses = new Class[]{Gremlin.class, Ogre.class, Skeleton.class};

        // Use the Helper class to get a random index for the monsterClasses array.
        int randomIndex = Helper.getRandomIntBetween(0, monsterClasses.length);

        // Create an instance of the selected monster type.
        try {
            Class<?> selectedMonsterClass = monsterClasses[randomIndex];
            return (DungeonCharacter) selectedMonsterClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            throw new RuntimeException("Randomly generated monster threw an exception.", e);
        }
    }
}