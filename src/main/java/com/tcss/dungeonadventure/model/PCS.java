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
    LOAD_EXISTING_GAME;


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
