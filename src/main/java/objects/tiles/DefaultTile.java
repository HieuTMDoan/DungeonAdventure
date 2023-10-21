package objects.tiles;

import objects.DungeonCharacter;

public class DefaultTile implements Tile{

    protected DefaultTile() {

    }


    @Override
    public char getTileChar() {
        return ' ';
    }

    @Override
    public boolean isTraversable() {
        return true;
    }

    @Override
    public boolean isInteractable() {
        return false;
    }

    @Override
    public void onStepOver(final DungeonCharacter target) {

    }
}
