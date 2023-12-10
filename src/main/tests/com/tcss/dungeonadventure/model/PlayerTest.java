package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.factories.HeroFactory;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.items.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void getPlayerName() {
        final Hero hero = HeroFactory.createCharacter(Helper.Characters.WARRIOR);

        final String name = "testPlayer";
        final Player player = new Player(name, hero);
        assertEquals(name, player.getPlayerName());
    }

    @Test
    public void getPlayerHero() {
        final Hero hero = HeroFactory.createCharacter(Helper.Characters.WARRIOR);

        final Player player = new Player("testPlayer", hero);
        assertSame(hero, player.getPlayerHero());
    }

    @Test
    public void getInventory() {
        final Hero hero = HeroFactory.createCharacter(Helper.Characters.WARRIOR);
        final Player player = new Player("testPlayer", hero);


        final Map<Item, Integer> inventory = player.getInventory();

        assertEquals(0, inventory.size());
        player.addItemToInventory(new VisionPotion());
        assertEquals(1, inventory.size());

    }

    @Test
    public void addItemToInventory() {
        final Hero hero = HeroFactory.createCharacter(Helper.Characters.WARRIOR);
        final Player player = new Player("testPlayer", hero);


        final Map<Item, Integer> inventory = player.getInventory();
        assertEquals(0, inventory.size());
        player.addItemToInventory(new VisionPotion());
        assertEquals(1, inventory.size());
    }

    @Test
    public void removeItemFromInventory() {
        final Hero hero = HeroFactory.createCharacter(Helper.Characters.WARRIOR);
        final Player player = new Player("testPlayer", hero);

        final Map<Item, Integer> inventory = player.getInventory();
        assertEquals(0, inventory.size());
        player.addItemToInventory(new VisionPotion());
        assertEquals(1, inventory.size());
        player.removeItemFromInventory(new VisionPotion());
        assertEquals(0, inventory.size());
    }

    @Test
    public void hasAllPillars() {
        final Hero hero = HeroFactory.createCharacter(Helper.Characters.WARRIOR);
        final Player player = new Player("testPlayer", hero);
        player.addItemToInventory(new PillarOfPolymorphism());
        player.addItemToInventory(new PillarOfEncapsulation());
        player.addItemToInventory(new PillarOfInheritance());
        player.addItemToInventory(new PillarOfAbstraction());

        assertTrue(player.hasAllPillars());

    }

    @Test
    public void hasAllPillarsWithoutPillars() {
        final Hero hero = HeroFactory.createCharacter(Helper.Characters.WARRIOR);
        final Player player = new Player("testPlayer", hero);
        assertFalse(player.hasAllPillars());
    }



}