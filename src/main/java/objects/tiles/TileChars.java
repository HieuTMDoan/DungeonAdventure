package objects.tiles;

/**
 * Class to contain what character represents each visual object.
 */
public class TileChars {

    public static class Heroes {

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
    }

    public static class Items {
        public static final char HEALING_POTION = 'H';
        public static final char VISION_POTION = 'V';
        public static final char PILLAR_OF_ENCAPSULATION = 'E';
        public static final char PILLAR_OF_ABSTRACTION = 'A';
        public static final char PILLAR_OF_POLYMORPHISM = 'P';
        public static final char PILLAR_OF_INHERITANCE = 'I';
    }


}
