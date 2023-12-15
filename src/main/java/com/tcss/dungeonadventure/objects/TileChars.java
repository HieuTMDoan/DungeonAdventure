package com.tcss.dungeonadventure.objects;

/**
 * Class to contain what character represents each visual object.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360: Fall 2023
 */
public final class TileChars {

    public static class Monster {

        /**
         * The display character of the Ogre.
         */
        public static final char OGRE = '⍡';

        /**
         * The display character of the Skeleton.
         */
        public static final char SKELETON = '⍥';

        /**
         * The display character of the Gremlin.
         */
        public static final char GREMLIN = '⍾';

    }

    public static class Player {

        /**
         * The display character of the Player.
         */
        public static final char PLAYER = '†';
    }

    public static class Room {

        /**
         * The display character of an EmptyTile.
         */
        public static final char EMPTY = ' ';

        /**
         * The display character of a WallTile.
         */
        public static final char WALL = '#';

        /**
         * The display character of a vertical DoorTile.
         */
        public static final char VERTICAL_DOOR = '—';

        /**
         * The display character of a horizontal DoorTile.
         */
        public static final char HORIZONTAL_DOOR = '|';

        /**
         * The display character of the EntranceTile.
         */
        public static final char ENTRANCE = 'i';

        /**
         * The display character of the ExitTile.
         */
        public static final char EXIT = 'O';

        /**
         * The display character of a PitTile.
         */
        public static final char PIT = 'X';
    }

    public static class Items {

        /**
         * The display character of a Healing Potion.
         */
        public static final char HEALING_POTION = 'H';

        /**
         * The display character of a Greater Healing Potion.
         */
        public static final char GREATER_HEALING_POTION = 'ᵿ';

        /**
         * The display character of a Vision Potion.
         */
        public static final char VISION_POTION = 'V';

        /**
         * The display character of the Pillar of Encapsulation.
         */
        public static final char PILLAR_OF_ENCAPSULATION = 'E';

        /**
         * The display character of the Pillar of Abstraction.
         */
        public static final char PILLAR_OF_ABSTRACTION = 'A';

        /**
         * The display character of the Pillar of Polymorphism.
         */
        public static final char PILLAR_OF_POLYMORPHISM = 'P';

        /**
         * The display character of the Pillar of Inheritance.
         */
        public static final char PILLAR_OF_INHERITANCE = 'I';

        /**
         * The display character of a Skill Orb.
         */
        public static final char SKILL_ORB = '¤';


    }


}
