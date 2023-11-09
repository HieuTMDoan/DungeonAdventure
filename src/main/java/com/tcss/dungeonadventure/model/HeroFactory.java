package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.heroes.Priestess;
import com.tcss.dungeonadventure.objects.heroes.Thief;
import com.tcss.dungeonadventure.objects.heroes.Warrior;

import java.lang.reflect.InvocationTargetException;

public class HeroFactory implements CharacterFactory {
    @Override
    public DungeonCharacter createCharacter() {
        // Define an array of hero classes to choose from.
        final Class<?>[] heroClasses = new Class[]{Warrior.class, Priestess.class, Thief.class};

        // Use the Helper class to get a random index for the heroClasses array.
        final int randomIndex = Helper.getRandomIntBetween(0, heroClasses.length);

        // Create an instance of the selected hero type with a name.
        try {
            final Class<?> selectedHeroClass = heroClasses[randomIndex];
            final String heroName = "Your Hero Name"; // Provide a name here
            return (DungeonCharacter) selectedHeroClass.getDeclaredConstructor(String.class).newInstance(heroName);
        } catch (final InstantiationException
                       | NoSuchMethodException
                       | IllegalAccessException
                       | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Randomly generated hero threw an exception.");
        }
    }
}
