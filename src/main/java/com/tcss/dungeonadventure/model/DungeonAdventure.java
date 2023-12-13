package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.memento.DungeonAdventureMemento;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.items.Item;
import com.tcss.dungeonadventure.objects.items.SkillOrb;
import com.tcss.dungeonadventure.objects.monsters.Monster;
import com.tcss.dungeonadventure.objects.tiles.EntranceTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import com.tcss.dungeonadventure.view.GUIHandler;
import javafx.application.Application;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.stream.IntStream;


/**
 * Represents the game's logic and functionalities.
 * This object communicates with the listener classes via MVC pattern.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public final class DungeonAdventure implements Serializable {

    /**
     * The chance to flee in combat.
     */
    public static final double FLEE_CHANCE = 0.45;

    @Serial
    private static final long serialVersionUID = 1L;

    public static final String SAVE_NAME = "saved_game.ser";

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
     * The current discovered rooms in the dungeon.
     */
    private Room[][] myDiscoveredRooms;

    /**
     * The current monster that the player is in combat with.
     */
    private Monster myCurrentlyFightingMonster;


    /**
     * Default empty constructor.
     * Should only be used for one-time instantiations
     * to preserve the singularity of the class.
     */
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

    /**
     * Launches the front-end of this program.
     */
    public void initialize() {
        /*
         * I have absolutely no clue why I couldn't throw this in the
         * constructor. Something to do with threading. Regardless, this needs
         * to stay here.
         * */
        Application.launch(GUIHandler.class);
    }


    /**
     * Starts a NEW game with the specified hero name and hero class.
     *
     * @param thePlayerName The name of the player.
     * @param theHero       The hero class.`
     */
    public void startNewGame(final String thePlayerName, final Hero theHero) {
        // This is where ALL data needs to be reset, just in case the player
        // is restarting their game.
        if (myDiscoveredRooms != null) {
            setDiscoveredRooms(new Room[Dungeon.MAZE_SIZE.height][Dungeon.MAZE_SIZE.width]);
        }


        this.myPlayer = new Player(thePlayerName, theHero);

        this.myDungeon = new Dungeon();
        this.myDiscoveredRooms = new Room[Dungeon.MAZE_SIZE.height][Dungeon.MAZE_SIZE.width];
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

        //y is row index, x is column index in a 2D array
        final int row = startingRoom.getDungeonLocation().y;
        final int col = startingRoom.getDungeonLocation().x;
        this.myDiscoveredRooms[row][col] = startingRoom;

        PCS.firePropertyChanged(PCS.LOAD_ROOM, startingRoom);
        PCS.firePropertyChanged(PCS.ROOMS_DISCOVERED, myDiscoveredRooms);
        PCS.firePropertyChanged(PCS.CHEAT_CODE, myDungeon);

        this.myPlayer.addItemToInventory(new SkillOrb());

    }

    /**
     * Moves the player to a new tile
     * in the given {@link Directions.Cardinal}.
     *
     * @param theDirection the given {@link Directions.Cardinal}
     */
    public void movePlayer(final Directions.Cardinal theDirection) {
        myPlayer.increaseStat(Player.Fields.MOVES);
        this.myDungeon.getCurrentRoom().movePlayer(theDirection);
        PCS.firePropertyChanged(PCS.UPDATED_PLAYER_LOCATION, null);


        final Monster[] surroundingMonsters = myDungeon.getAnySurroundingMonsters();
        if (surroundingMonsters == null) { // There are no monsters surrounding
            return;
        }
        startCombat(surroundingMonsters);
    }

    public void doCombatAction(final CombatActions theAction) {
        final Integer[] damage = new Integer[]{0};
        final Hero hero = myPlayer.getPlayerHero();

        final TimedSequence s = new TimedSequence();

        s.afterDo(0, () -> { // player action phase
            switch (theAction) {
                case ATTACK -> {
                    damage[0] = hero.attack(myCurrentlyFightingMonster);

                    if (damage[0] > 0) {
                        myPlayer.increaseStat(Player.Fields.DAMAGE_DEALT, damage[0]);

                        PCS.firePropertyChanged(PCS.COMBAT_LOG,
                                "Player attacked, dealing " + damage[0] + " damage.");
                    } else {
                        myPlayer.increaseStat(Player.Fields.MISSED_ATTACKS);
                        PCS.firePropertyChanged(PCS.COMBAT_LOG, "Player missed!");
                    }
                }

                case USE_SKILL -> {
                    if (!myPlayer.containsItem(new SkillOrb())) {
                        PCS.firePropertyChanged(PCS.COMBAT_LOG,
                                "You need a Skill Orb to use your skill!");
                        return false;
                    }

                    myPlayer.removeItemFromInventory(new SkillOrb());
                    PCS.firePropertyChanged(PCS.COMBAT_LOG,
                            "Used a Skill Orb to activate "
                                    + Helper.camelToSpaced(hero.getSkill().getClass().getSimpleName()));

                    hero.useSkill(myCurrentlyFightingMonster);
                }

                case FLEE -> {
                    if (Helper.getRandomDoubleBetween(0, 1) > FLEE_CHANCE) {
                        PCS.firePropertyChanged(PCS.COMBAT_LOG, "Couldn't flee!");
                    } else {
                        PCS.firePropertyChanged(PCS.END_COMBAT, null);
                        PCS.firePropertyChanged(PCS.LOG, "Fled from combat!");
                        return false;
                    }
                }

                default -> throw new IllegalStateException(
                        "Unexpected value: " + theAction);
            }

            PCS.firePropertyChanged(PCS.SYNC_COMBAT, myCurrentlyFightingMonster);
            PCS.firePropertyChanged(PCS.TOGGLE_COMBAT_LOCK, false);


            // Check for victory
            if (myCurrentlyFightingMonster.isDefeated()) {
                myPlayer.increaseStat(Player.Fields.MONSTERS_DEFEATED);
                PCS.firePropertyChanged(PCS.END_COMBAT, null);
                PCS.firePropertyChanged(PCS.LOG,
                        "Defeated " + myCurrentlyFightingMonster.getName() + "!");
                myCurrentlyFightingMonster = null;
                return false;
            }

            PCS.firePropertyChanged(PCS.SYNC_COMBAT, myCurrentlyFightingMonster);
            PCS.firePropertyChanged(PCS.TOGGLE_COMBAT_LOCK, false);
            return true;
        }).afterDoIf(1, () -> damage[0] > 0, () -> { // Monster heal phase
            final int healAmount = myCurrentlyFightingMonster.heal();
            if (healAmount > 0) {
                PCS.firePropertyChanged(PCS.COMBAT_LOG,
                        "%s healed %s health!".
                                formatted(myCurrentlyFightingMonster.getName(), healAmount));
            } else {
                PCS.firePropertyChanged(PCS.COMBAT_LOG,
                        "%s tried to heal, but was unsuccessful!".
                                formatted(myCurrentlyFightingMonster.getName()));
            }
            PCS.firePropertyChanged(PCS.SYNC_COMBAT, myCurrentlyFightingMonster);
            return true;

        }).afterDo(1, () -> { // monster attack phase
            Integer damageToPlayer = myCurrentlyFightingMonster.attack(hero);
            if (myPlayer.isInvincible()) {
                damageToPlayer = 0;
            }

            if (damageToPlayer == null) {
                PCS.firePropertyChanged(PCS.COMBAT_LOG, "Player blocked the attack!");
            } else if (damageToPlayer > 0) {
                PCS.firePropertyChanged(PCS.COMBAT_LOG,
                        myCurrentlyFightingMonster.getName()
                                + " attacked, dealing " + damageToPlayer + " damage.");
            } else {
                PCS.firePropertyChanged(PCS.COMBAT_LOG,
                        myCurrentlyFightingMonster.getName() + " missed!");
            }
            PCS.firePropertyChanged(PCS.SYNC_COMBAT, myCurrentlyFightingMonster);

            if (hero.isDefeated()) {
                endGame(false);
                return false;
            }

            PCS.firePropertyChanged(PCS.SYNC_COMBAT, myCurrentlyFightingMonster);
            PCS.firePropertyChanged(PCS.TOGGLE_COMBAT_LOCK, true);
            return false;
        }).start();
    }


    /**
     * Starts engaging in combat with a monster
     * from the list of monsters.
     *
     * @param theSurroundingMonsters the list of monsters
     *                               to engage in combat with.
     */
    private void startCombat(final Monster[] theSurroundingMonsters) {
        this.myCurrentlyFightingMonster = theSurroundingMonsters[0];
        PCS.firePropertyChanged(PCS.BEGIN_COMBAT, theSurroundingMonsters[0]);
    }


    public void endGame(final boolean theVictory) {
        PCS.firePropertyChanged(PCS.GAME_END, theVictory);

        final File f = new File(SAVE_NAME);

        if (f.exists()) {
            f.delete();
        }
    }


    /**
     * @return The current dungeon.
     */
    public Dungeon getDungeon() {
        return this.myDungeon;
    }

    /**
     * @return The current discovered rooms
     * in the dungeon.
     */
    public Room[][] getDiscoveredRooms() {
        return this.myDiscoveredRooms;
    }

    /**
     * Sets the current discovered rooms
     * in the dungeon to the new discovered rooms.
     *
     * @param theNewDiscoveredRooms the new discovered rooms
     */
    public void setDiscoveredRooms(final Room[][] theNewDiscoveredRooms) {
        this.myDiscoveredRooms = theNewDiscoveredRooms;
        PCS.firePropertyChanged(PCS.ROOMS_DISCOVERED, myDiscoveredRooms);

    }

    /**
     * Moves the player's current location in the dungeon
     * in the given {@link Directions.Cardinal}.
     *
     * @param theDirection the given {@link Directions.Cardinal}
     */
    public void changeRoom(final Directions.Cardinal theDirection) {

        final Room room =
                this.myDungeon.getCurrentRoom().getAdjacentRoomByDirection(theDirection);


        final int row = room.getDungeonLocation().y;
        final int col = room.getDungeonLocation().x;

        this.myDungeon.loadPlayerTo(room, theDirection);
        this.myDiscoveredRooms[row][col] = room;

        System.out.println(this.myDungeon);
        PCS.firePropertyChanged(PCS.ROOMS_DISCOVERED, myDiscoveredRooms);
        PCS.firePropertyChanged(PCS.CHEAT_CODE, myDungeon);
    }

    /**
     * Returns the current {@link Player}.
     *
     * @return The current {@link Player}.
     */
    public Player getPlayer() {
        return this.myPlayer;
    }

    /**
     * Uses the chosen {@link Item} from the inventory
     * and updates the game's status based on the {@link Item}'s type.
     *
     * @param theItem the chosen {@link Item}
     */
    public void useItem(final Item theItem) {
        if (theItem.getItemType() == Item.ItemTypes.PILLAR || theItem instanceof SkillOrb) {
            return;
        }

        myPlayer.removeItemFromInventory(theItem);
        PCS.firePropertyChanged(PCS.LOG, "Used a "
                + Helper.camelToSpaced(theItem.getClass().getSimpleName()));
        theItem.useItem(myPlayer.getPlayerHero());
    }


    // Saving

    /**
     * Saves the game's current state to a file.
     * Throws an {@link IOException} error
     * if the game can't be saved.
     */
    public void saveGameState() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream("saved_game.ser"))) {

            oos.writeObject(new DungeonAdventureMemento(myPlayer, myDungeon, myDiscoveredRooms));
            System.out.println("Game saved successfully!");
        } catch (final IOException ex) {
            System.err.println("Error writing saved game state file: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    // Loading

    /**
     * Loads the game's current state from a file.
     * Throws an {@link IOException} or {@link ClassNotFoundException} error
     * if the game can't be loaded.
     */
    public boolean loadGameState() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream("saved_game.ser"))) {

            final DungeonAdventureMemento memento = (DungeonAdventureMemento) ois.readObject();

            this.myPlayer = memento.getSavedPlayer();
            this.myDungeon = memento.getSavedDungeon();
            setDiscoveredRooms(memento.getSavedDiscoveredRooms());

            for (final Room[] row : myDungeon.getMaze()) {
                for (final Room room : row) {
                    if (room.getPlayerXPosition() == null) {
                        continue;
                    }

                    myDungeon.loadPlayerTo(room, new Point(room.getPlayerXPosition(), room.getPlayerYPosition()));
                    break;
                }
            }


            System.out.println("Game loaded successfully!");

            return true;
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Activates the invincibility cheat code.
     */
    public void activateInvincibilityCheat() {
        if (myPlayer != null) {
            myPlayer.setInvincible(!myPlayer.isInvincible());

            PCS.firePropertyChanged(PCS.CHEAT_CODE, myDungeon);
            PCS.firePropertyChanged(PCS.LOG, "Invincibility cheat activated!");
        }
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