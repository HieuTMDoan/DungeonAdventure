package objects.tiles;

import objects.DungeonCharacter;

public interface Tile {
    Tile DEFAULT = new DefaultTile();

//    Tile WALL = new WallTile();


    char getTileChar();

    boolean isTraversable();

    boolean isInteractable();


    void onStepOver(DungeonCharacter target);
}
