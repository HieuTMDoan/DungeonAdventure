package objects.skills;

import objects.DungeonCharacter;

public abstract class Skill {


    public Skill() {

    }


    public boolean activateSkill(final DungeonCharacter target) {
        return false;
    }

}
