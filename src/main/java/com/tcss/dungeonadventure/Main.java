package com.tcss.dungeonadventure;


import com.tcss.dungeonadventure.model.*;
import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.items.PillarOfEncapsulation;
import com.tcss.dungeonadventure.objects.items.PillarOfPolymorphism;
import com.tcss.dungeonadventure.objects.tiles.Tile;




public class Main {

    public static void main(final String[] theArgs) {
//        Application.launch(DungeonGUI.class);

        final boolean noGUI = true; // This should be specified via command line
        new DungeonAdventure(noGUI);


    //    System.out.println(new Room(false, false, PillarOfEncapsulation.class));

//       System.out.println(new Dungeon());


    }

}
