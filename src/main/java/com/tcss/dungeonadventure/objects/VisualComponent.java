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
public interface VisualComponent {

    /**
     * @return The display character of the component.
     */
    char getDisplayChar();

    /**
     * @return The description of the component.
     */
    String getDescription();

}

