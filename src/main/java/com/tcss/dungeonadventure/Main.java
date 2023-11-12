package com.tcss.dungeonadventure;



import com.tcss.dungeonadventure.model.*;
import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.items.PillarOfEncapsulation;
import com.tcss.dungeonadventure.objects.items.PillarOfPolymorphism;
import com.tcss.dungeonadventure.objects.tiles.Tile;



import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.DungeonAdventure;



public final class Main {

    private Main() {
        throw new RuntimeException("Main should not be instantiated");
    }

    public static void main(final String[] theArgs) {

        final boolean noGUI = true; // This should be specified via command line
        new DungeonAdventure(noGUI);


//        SQLiteDB.getCharacters();
//        System.out.println(new Dungeon());





//       System.out.println(new Dungeon());


    }

}
