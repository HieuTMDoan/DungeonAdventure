package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.heroes.Priestess;
import com.tcss.dungeonadventure.objects.heroes.Thief;
import com.tcss.dungeonadventure.objects.heroes.Warrior;

public class HeroFactory implements CharacterFactory {
    @Override
    public DungeonCharacter createCharacter() {
        // Define an array of hero classes to choose from.
        Class<?>[] heroClasses = new Class[]{Warrior.class, Priestess.class, Thief.class};

        // Use the Helper class to get a random index for the heroClasses array.
        int randomIndex = Helper.getRandomIntBetween(0, heroClasses.length);

        // Create an instance of the selected hero type with a name.
        try {
            Class<?> selectedHeroClass = heroClasses[randomIndex];
            String heroName = "Your Hero Name"; // Provide a name here
            return (DungeonCharacter) selectedHeroClass.getDeclaredConstructor(String.class).newInstance(heroName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Randomly generated hero threw an exception.");
        }
    }
}
