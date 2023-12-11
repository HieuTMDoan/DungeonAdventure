package com.tcss.dungeonadventure.model;


import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.factories.HeroFactory;
import com.tcss.dungeonadventure.objects.items.VisionPotion;
import com.tcss.dungeonadventure.objects.tiles.ItemTile;
import com.tcss.dungeonadventure.objects.tiles.PitTile;
import com.tcss.dungeonadventure.objects.tiles.WallTile;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TileTests {

    @Test
    public void testPitDamage() {
        final Player player = new Player("testPlayer",
                HeroFactory.createCharacter(Helper.Characters.WARRIOR));

        final PitTile pit = new PitTile();

        final int beforeDamage = player.getPlayerHero().getHealth();
        pit.onInteract(player);

        final int afterDamage = player.getPlayerHero().getHealth();

        assertTrue(beforeDamage > afterDamage);
    }

    @Test
    public void testAddItemFromItemTile() {
        final Player player = new Player("testPlayer",
                HeroFactory.createCharacter(Helper.Characters.WARRIOR));

        final ItemTile tile = new ItemTile(new VisionPotion());

        final int beforeAdd = player.getInventory().size();
        tile.onInteract(player);

        final int afterAdd = player.getInventory().size();

        assertTrue(beforeAdd < afterAdd);
    }

    @Test
    public void testExceptionWhenPlayerInteractsWallTile() {
        final Player player = new Player("testPlayer",
                HeroFactory.createCharacter(Helper.Characters.WARRIOR));

        final WallTile tile = new WallTile();

        try {
            tile.onInteract(player);
        } catch (final RuntimeException e) {
            assertTrue(true);
            return;
        }
        fail();


    }

}
