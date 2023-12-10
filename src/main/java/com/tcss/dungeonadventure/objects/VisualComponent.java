package com.tcss.dungeonadventure.objects;

/**
 * This interface represents all objects that will have an appearance
 * during the game, and will require a display character and a description.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version Fall 2023
 */
public abstract class VisualComponent {

    private final char myDisplayChar;
    private String myDescription;

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

    public String getTileColor() {
        return "white";
    }

}

