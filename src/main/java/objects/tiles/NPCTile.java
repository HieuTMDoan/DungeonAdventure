package objects.tiles;

import objects.DungeonCharacter;

public class NPCTile extends Tile {


    private final DungeonCharacter myDungeonCharacter;

    public NPCTile(final DungeonCharacter theDungeonCharacter) {
        super(theDungeonCharacter.getDisplayChar(), false, false);
        this.myDungeonCharacter = theDungeonCharacter;
    }
}
