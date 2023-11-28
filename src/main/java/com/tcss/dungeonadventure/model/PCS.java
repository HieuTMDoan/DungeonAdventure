package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.objects.Directions;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;



/**
 * This class contains constants for the Property Chance Listener.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version Fall 2023
 */
public enum PCS {
    /**
     * The property to log a message in the GUI's logger.
     * Should be paired with a {@link String} as the message
     * to display.
     */
    LOG,

    /**
     * The property when the players inventory changes.
     * Should be paired with a Map<Item, Integer>, representing
     * the players current inventory.
     */
    ITEMS_CHANGED,

    /**
     * The property of player movement.
     * Should be paired with {@link Directions.Cardinal}
     */
    MOVE_PLAYER,

    /**
     * The property of player location change.
     * Not to be confused with MOVE_PLAYER.
     * <p>
     * Should be paired with nothing, as player location is
     * stored within the room.
     */
    UPDATED_PLAYER_LOCATION,

    /**
     * The property of loading a new room.
     * Should be paired with {@link Room}
     */
    LOAD_ROOM,


    /**
     * The property of loading an EXISTING game.
     * Should be paired with //TODO something. idk what that is. game data or sum
     */
    LOAD_EXISTING_GAME,

    /**
     * The property of loading a combat sequence between
     * the player and a monster.
     * Should be paired with a Monster that the player is fighting.
     */
    BEGIN_COMBAT,

    /**
     * The property of when actions are performed by either the
     * player or the monster, which synchronizes the health in the GUI.
     * Should he paired with an DungeonCharacter[] with:
     *  [0]: The player.
     *  [1]: The monster.
     */
    SYNC_COMBAT,

    /**
     * The property of when a combat sequence is over.
     * Should be paired with TODO: nothing? Return to the main screen?
     */
    END_COMBAT




    ;



    /**
     * The Property Change Listener for DungeonAdventure.
     */
    private static final PropertyChangeSupport PCS = new PropertyChangeSupport(PCS.class);


    public static void firePropertyChanged(final PCS theProperty,
                                           final Object theNewValue) {

        firePropertyChanged(theProperty, null, theNewValue);
    }

    public static void firePropertyChanged(final PCS theProperty,
                                           final Object theOldValue,
                                           final Object theNewValue) {

        PCS.firePropertyChange(theProperty.name(), theOldValue, theNewValue);
    }

    public static void addPropertyListener(final PropertyChangeListener theListener) {
        PCS.addPropertyChangeListener(theListener);
    }


}
