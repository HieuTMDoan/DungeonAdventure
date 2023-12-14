package com.tcss.dungeonadventure;


import com.tcss.dungeonadventure.model.DungeonAdventure;


/**
 * The entry point of DungeonAdventure.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public final class Main {

    private Main() {
        throw new RuntimeException("Main should not be instantiated");
    }

    public static void main(final String[] theArgs) {
        DungeonAdventure.getInstance().initialize();

    }

}
