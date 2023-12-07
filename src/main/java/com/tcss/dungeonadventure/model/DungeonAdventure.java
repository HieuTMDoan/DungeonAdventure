package com.tcss.dungeonadventure.model;


import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.items.Item;
import com.tcss.dungeonadventure.objects.monsters.Monster;
import com.tcss.dungeonadventure.objects.tiles.EntranceTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import com.tcss.dungeonadventure.view.GUIHandler;
import java.awt.Point;
import java.io.*;
import java.util.Arrays;
import java.util.stream.IntStream;
import javafx.application.Application;

public final class DungeonAdventure implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Singleton instance for DungeonAdventure.
     */
    private static DungeonAdventure INSTANCE;

    /**
     * The current player.
     */
    private Player myPlayer;

    /**
     * The current Dungeon.
     */
    private Dungeon myDungeon;

    /**
     * The current monster that the player is in combat with.
     */
    private Monster myCurrentlyFightingMonster;


    private DungeonAdventure() {
    }

    /**
     * Lazy singleton accessor.
     *
     * @return The instance of the DungeonAdventure.
     */
    public static DungeonAdventure getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DungeonAdventure();
        }
        return INSTANCE;
    }

    public void initialize() {
        /*
         * I have absolutely no clue why I couldn't throw this in the
         * constructor.
         * */
        Application.launch(GUIHandler.class);
    }


    /**
     * Starts a NEW game with the specified hero name and hero class.
     *
     * @param thePlayerName The name of the player.
     * @param theHero       The hero class.
     */
    public void startNewGame(final String thePlayerName, final Hero theHero) {
        this.myPlayer = new Player(thePlayerName, theHero);
        this.myDungeon = new Dungeon();
        System.out.println(myDungeon);

        final Room startingRoom = myDungeon.getStartingRoom();
        final Tile[][] roomTiles = startingRoom.getRoomTiles();

        // This locates the entrance tile in the entrance room.
        final Point entranceTileLocation =
                IntStream.range(0, roomTiles.length).
                        boxed().
                        flatMap(i -> IntStream.range(0, roomTiles[i].length).
                                filter(j -> roomTiles[i][j].getClass() == EntranceTile.class).
                                mapToObj(j -> new Point(i, j))).
                        findFirst().
                        orElse(null);

        if (entranceTileLocation == null) {
            throw new IllegalStateException("Could not find EntranceTile in starting room.");
        }

        this.myDungeon.loadPlayerTo(startingRoom, entranceTileLocation);

        PCS.firePropertyChanged(PCS.LOAD_ROOM, myDungeon.getStartingRoom());
    }

    public void movePlayer(final Directions.Cardinal theDirection) {
        this.myDungeon.getCurrentRoom().movePlayer(theDirection);
        PCS.firePropertyChanged(PCS.UPDATED_PLAYER_LOCATION, null);


        final Monster[] surroundingMonsters = myDungeon.getAnySurroundingMonsters();
        if (surroundingMonsters == null) { // There are no monsters surrounding
            return;
        }
        System.out.println(Arrays.toString(surroundingMonsters));

        // TODO: Start combat!!!
        startCombat(surroundingMonsters);
    }

    public void doCombatAction(final CombatActions theAction) {
        switch (theAction) {
            case ATTACK -> {
                final int damage = myPlayer.getPlayerHero().attack(myCurrentlyFightingMonster);

                if (damage > 0) {
                    PCS.firePropertyChanged(PCS.COMBAT_LOG, "Player attacked, dealing " + damage + " damage.");
                } else {
                    PCS.firePropertyChanged(PCS.COMBAT_LOG, "Player missed!");
                }
                PCS.firePropertyChanged(PCS.SYNC_COMBAT, myCurrentlyFightingMonster);
            }
            case USE_SKILL -> {
                myPlayer.getPlayerHero().useSkill(myCurrentlyFightingMonster);


            }
            case FLEE -> {


            }
            default -> throw new IllegalStateException("Unexpected value: " + theAction);
        }

        // Check for victory
        if (myCurrentlyFightingMonster.isDefeated()) {
            // Handle monster defeat
            handleMonsterDefeat(myCurrentlyFightingMonster);
            return;
        }


        final int damage = myCurrentlyFightingMonster.attack(myPlayer.getPlayerHero());

        if (damage > 0) {
            PCS.firePropertyChanged(PCS.COMBAT_LOG,
                    myCurrentlyFightingMonster.getName() + " attacked, dealing " + damage + " damage.");
        } else {
            PCS.firePropertyChanged(PCS.COMBAT_LOG, myCurrentlyFightingMonster.getName() + " missed!");
        }
        PCS.firePropertyChanged(PCS.SYNC_COMBAT, myCurrentlyFightingMonster);

        // Check for defeat
        if (myPlayer.getPlayerHero().isDefeated()) {
            // Handle player defeat
            handlePlayerDefeat();
        }
    }

    private void startCombat(final Monster[] theSurroundingMonsters) {
        this.myCurrentlyFightingMonster = theSurroundingMonsters[0];
        PCS.firePropertyChanged(PCS.BEGIN_COMBAT, theSurroundingMonsters[0]);
    }

    private void handlePlayerDefeat() {
        // Handle player defeat, --> display message and reset the game
        PCS.firePropertyChanged(PCS.GAME_END, false);

        PCS.firePropertyChanged(PCS.LOG, myPlayer.getPlayerHero().getName()
                + " " + DungeonAdventure.getInstance().getPlayer().getPlayerName()
                + " defeated! Game over.");
        //    resetGame(); TODO
    }

    private void handleMonsterDefeat(final Monster theDefeatedMonster) {
        // Handle monster defeat
        PCS.firePropertyChanged(PCS.END_COMBAT, null);
        PCS.firePropertyChanged(PCS.LOG, "Defeated " + theDefeatedMonster.getName() + "!");
        // rewards or move to next room?
    }

    public Dungeon getDungeon() {
        return this.myDungeon;
    }

    public void changeRoom(final Directions.Cardinal theDirection) {
        final Room room =
                this.myDungeon.getCurrentRoom().getAdjacentRoomByDirection(theDirection);

        this.myDungeon.loadPlayerTo(room, theDirection);
        System.out.println(this.myDungeon);
    }

    public Player getPlayer() {
        return this.myPlayer;
    }

    public void useItem(final Item theItem) {
        myPlayer.removeItemFromInventory(theItem);
        PCS.firePropertyChanged(PCS.LOG, "Used item: " + theItem.getClass().getSimpleName());

        theItem.useItem(myPlayer.getPlayerHero());
    }

    public DungeonAdventureMemento createMemento() {
        final String playerName = this.myPlayer.getPlayerName();
        final Hero hero = this.myPlayer.getPlayerHero();
        final Dungeon dungeon = this.myDungeon;

        final DungeonAdventureMemento memento;
        memento = new DungeonAdventureMemento(playerName, hero, dungeon);
        memento.addRoomMemento(myDungeon.getCurrentRoom().saveToMemento());

        return memento;
    }

    public void saveGameState() {
        // Create and save a memento
        GameStateManager.getInstance().createMemento();
    }


    public void loadGameState() {
        try {
            // Load the memento from the GameStateManager
            final DungeonAdventureMemento memento = GameStateManager.getInstance().getMemento();

            // Restore the game state from the loaded memento
            restoreFromMemento(memento);

            // Trigger necessary events to update the GUI
            PCS.firePropertyChanged(PCS.LOAD_ROOM, myDungeon.getCurrentRoom());
            PCS.firePropertyChanged(PCS.UPDATED_PLAYER_LOCATION, null);

            System.out.println("Game loaded successfully!");
        } catch (NullPointerException ex) {
            // Handle the case where the memento is not found
            System.out.println("No saved game state found!");
            ex.printStackTrace();
        }
    }



    // Restore the state from a Memento
    public void restoreFromMemento(final DungeonAdventureMemento theMemento) {
        this.myPlayer = new Player(theMemento.getSavedPlayerName(), theMemento.getSavedHero());
        this.myDungeon = theMemento.getSavedDungeon();

        // Restore the current room
        final RoomMemento roomMemento = theMemento.getRoomMementos().get(0);
        myDungeon.getCurrentRoom().restoreFromMemento(roomMemento);

        PCS.firePropertyChanged(PCS.LOAD_ROOM, myDungeon.getCurrentRoom());
    }

    /**
     * Enums for combat actions.
     */
    public enum CombatActions {
        /**
         * Enum for using normal attack.
         */
        ATTACK,

        /**
         * Enum for using skill.
         */
        USE_SKILL,

        /**
         * Enum for fleeing.
         */
        FLEE
    }
}