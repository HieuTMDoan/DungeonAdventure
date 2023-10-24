package objects.items;

import objects.DungeonCharacter;

public abstract class Item {

    private final char myDisplayChar;

    public Item(final char theDisplayChar) {
        this.myDisplayChar = theDisplayChar;
    }

    public char getDisplayChar() {
        return myDisplayChar;
    }

    public abstract void useItem(DungeonCharacter theTarget);

}
