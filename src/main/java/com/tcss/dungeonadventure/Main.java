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

//        DungeonAdventure.getInstance().initialize();

//        SQLiteDB.getCharacters();

//        final Room room = new Room(false, false, null);
//        for (int i = 1; i < room.getRoomWidth() - 1; i++) {
//            room.getRoomTiles()[0][i] = new EmptyTile();
//        }
//        System.out.println(room.getRoomWidth() + " " + room.getRoomHeight());
//        System.out.println(room);
        System.out.println(new Dungeon());



    }

}
