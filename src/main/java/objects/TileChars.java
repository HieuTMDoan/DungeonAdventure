package objects;

/**
 * Class to contain what character represents each visual object.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 *
 * @version Fall 2023
 */
public final class TileChars {

    public static class Player {
        public static final char PLAYER = '+';

    }

    public static class Monsters {

    }

    public static class Room {
        public static final char EMPTY = ' ';
        public static final char WALL = '*';
        public static final char VERTICAL_DOOR = '-';
        public static final char HORIZONTAL_DOOR = '|';
        public static final char ENTRANCE = 'i';
        public static final char EXIT = 'O';
        public static final char PIT = 'X';
    }

    public static class Items {

        public static final char MULTIPLE = 'M';
        public static final char HEALING_POTION = 'H';
        public static final char VISION_POTION = 'V';
        public static final char PILLAR_OF_ENCAPSULATION = 'E';
        public static final char PILLAR_OF_ABSTRACTION = 'A';
        public static final char PILLAR_OF_POLYMORPHISM = 'P';
        public static final char PILLAR_OF_INHERITANCE = 'I';
    }


}
