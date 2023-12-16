package com.tcss.dungeonadventure.objects;

import java.io.Serial;
import java.io.Serializable;

/**
 * This interface represents all objects that will have an appearance
 * during the game, and will require a display character and a description.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public abstract class VisualComponent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The character of the tile.
     */
    private final char myDisplayChar;

    public VisualComponent(final char theDisplayChar) {
        this.myDisplayChar = theDisplayChar;
    }

    /**
     * @return The display character of the component.
     */
    public char getDisplayChar() {
        return this.myDisplayChar;
    }

    /**
     * @return The description of the component.
     */
    public String getDescription() {
        return "NO_DESCRIPTION";
    }

    /**
     * @return The default color of the component.
     */
    public String getTileColor() {
        return "white";
    }

}

