package com.tcss.dungeonadventure.objects;

/**
 * This interface represents all objects that will have an appearance
 * during the game, and will require a display character and a description.
 */
public interface VisualComponent {

    char getDisplayChar();

    String getDescription();

}

