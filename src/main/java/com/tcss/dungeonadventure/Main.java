package com.tcss.dungeonadventure;


import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.tiles.EmptyTile;


public final class Main {

    private Main() {
        throw new RuntimeException("Main should not be instantiated");
    }

    public static void main(final String[] theArgs) {

        DungeonAdventure.getInstance().initialize();

        // NOTE: as of 11/21/2023 11:22 AM, the dungeon is traversable.
        // you can boot up the dungeon adventure and travel from start to end,
        // but you may be blocked by monsters.
    }

}
