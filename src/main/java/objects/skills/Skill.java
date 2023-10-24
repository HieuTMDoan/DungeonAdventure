package objects.skills;

import objects.DungeonCharacter;

public abstract class Skill {


    public Skill() {

    }


    public abstract boolean activateSkill(DungeonCharacter theTarget);

}
