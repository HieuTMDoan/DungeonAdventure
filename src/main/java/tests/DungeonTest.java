package tests;


import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.tiles.EmptyTile;
import com.tcss.dungeonadventure.objects.tiles.NPCTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import org.junit.Test;
class DungeonTest {

    @Test
    void getAnySurroundingMonsters() {
        final Tile[][] tiles = new Tile[3][3];

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new EmptyTile();
            }
        }



        final Room room = new Room(tiles);



    }

    @Test
    void getCurrentRoom() {
    }

    @Test
    void getStartingRoom() {
    }

    @Test
    void loadPlayerTo() {
    }

    @Test
    void testLoadPlayerTo() {
    }

    @Test
    void getRoomAt() {
    }

    @Test
    void testToString() {
    }
}