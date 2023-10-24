package objects.skills;

import objects.DungeonCharacter;

public class CrushingBlow extends Skill {

    private static final int DEFAULT_MIN_DAMAGE = 75;
    private static final int DEFAULT_MAX_DAMAGE = 175;
    private static final double DEFAULT_SUCCESS_CHANCE = 0.4;


    public CrushingBlow() {

    }

    @Override
    public boolean activateSkill(final DungeonCharacter theTarget) {
        return false;
    }


}
