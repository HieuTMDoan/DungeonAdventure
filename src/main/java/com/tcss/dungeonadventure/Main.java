package com.tcss.dungeonadventure;


import com.tcss.dungeonadventure.model.DungeonAdventure;


public final class Main {

    private Main() {
        throw new RuntimeException("Main should not be instantiated");
    }

    public static void main(final String[] theArgs) {

        DungeonAdventure.getInstance().initialize();

//        SQLiteDB.getCharacters();
//        System.out.println(new Dungeon());


    }

}
