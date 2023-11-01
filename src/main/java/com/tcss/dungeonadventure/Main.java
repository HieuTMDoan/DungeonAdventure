package com.tcss.dungeonadventure;


import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.items.PillarOfPolymorphism;


public class Main {

    public static void main(final String[] theArgs) {
//        Application.launch(DungeonGUI.class);

        final boolean noGUI = true; // This should be specified via command line
//        new DungeonAdventure(noGUI);


        System.out.println(new Room(false, false, PillarOfPolymorphism.class));

        System.out.println(new Dungeon());

    }

}
