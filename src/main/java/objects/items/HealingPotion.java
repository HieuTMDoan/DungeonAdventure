package objects.items;


import objects.DungeonCharacter;
import objects.TileChars;

public class HealingPotion extends Item {

    public HealingPotion() {
        super(TileChars.Items.HEALING_POTION);

    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // TODO: Heal target
    }




}
