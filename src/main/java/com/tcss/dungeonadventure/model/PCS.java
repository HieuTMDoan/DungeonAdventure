package com.tcss.dungeonadventure.model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import com.tcss.dungeonadventure.objects.Directions;

/**
 * This class contains constants for the Property Chance Listener
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version Fall 2023
 */
public enum PCS {

    /**
     * The property of player movement.
     * Should be paired with {@link Directions.Cardinal}
     */
    MOVE_PLAYER,

    /**
     * The property of player location change.
     * Not to be confused with MOVE_PLAYER.
     * Should be paired with a {@link java.awt.Point} representing
     * the players current position in the room.
     *
     */
    UPDATED_PLAYER_LOCATION,

    /**
     * The property of loading a new room.
     * Should be paired with {@link Room}
     */
    LOAD_ROOM,

    /**
     * The property of starting a BRAND-NEW game.
     * Should be paired with an Object[] with:
     *  0: The players name
     *  1: A Hero object, as the players class.
     */
    START_NEW_GAME,

    /**
     * The property of loading an EXISTING game.
     * Should be paired with //TODO something. idk what that is. game data or sum
     */
    LOAD_EXISTING_GAME,

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
