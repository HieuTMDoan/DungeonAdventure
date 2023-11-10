package com.tcss.dungeonadventure;


import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.SQLiteDB;
import com.tcss.dungeonadventure.objects.heroes.Warrior;


public class Main {

    private Main() {
        throw new RuntimeException("Main should not be instantiated");
    }

    public static void main(final String[] theArgs) {

        final boolean noGUI = false; // This should be specified via command line
        new DungeonAdventure(noGUI);

//        SQLiteDB.getCharacters();




    }

}
