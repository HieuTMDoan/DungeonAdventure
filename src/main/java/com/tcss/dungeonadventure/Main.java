package com.tcss.dungeonadventure;


import com.tcss.dungeonadventure.model.DungeonAdventure;


public final class Main {

    private Main() {
        throw new RuntimeException("Main should not be instantiated");
    }

    public static void main(final String[] theArgs) {

        final boolean noGUI = true; // This should be specified via command line
        new DungeonAdventure(noGUI);

//        SQLiteDB.getCharacters();




    }

}
