package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.heroes.Priestess;
import com.tcss.dungeonadventure.objects.heroes.Thief;
import com.tcss.dungeonadventure.objects.heroes.Warrior;
import com.tcss.dungeonadventure.objects.monsters.Gremlin;
import com.tcss.dungeonadventure.objects.monsters.Ogre;
import com.tcss.dungeonadventure.objects.monsters.Skeleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.sqlite.SQLiteDataSource;

/**
 * Utility class that uses JDBC API to store and manage the game's SQLite database.
 * The database stores all of {@link DungeonCharacter}'s initial statistics.
 *
 * @author Hieu, Aaron, Sunny
 * @version TCSS 360: Fall 2023
 */
public final class SQLiteDB {
    /**
     * Represents the connection with the data source.
     */
    private static Connection myConn;

    /**
     *
     */
    private SQLiteDB() { }

    static {
        /*
         * Initializes the data source and establishes a connection with it.
         * Auto-generates the data source if it doesn't already exist.
         */
        try {
            final SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:dungeonCharacters.sqlite");
            myConn = ds.getConnection();

            //Inserts data into the data source if it's empty
            if (!hasData()) {
                createTable();
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Successfully opened database");
    }

    /**
     * Returns a {@link DungeonCharacter}'s initial statistics based on its name.
     *
     * @param theCharacter the enum of the {@link DungeonCharacter} to be printed
     * @return the queried {@link DungeonCharacter}'s initial statistics
     */
    public static DungeonCharacter getCharacterByName(final Helper.Characters theCharacter) {
        final String querySearch = "SELECT * FROM dungeonCharacters WHERE NAME = ?";

        try (PreparedStatement stmt = myConn.prepareStatement(querySearch)) {
            stmt.setString(1, theCharacter.toString());
            final ResultSet rs = stmt.executeQuery();

            //Retrieves all relevant stats of both Monster and Hero characters
            if (rs.next()) {
                final String name = rs.getString("NAME");
                final char displayChar = rs.getString("DISPLAY_CHAR").charAt(0);
                final int health = rs.getInt("HEALTH");
                final int damageMin = rs.getInt("DAMAGE_MIN");
                final int damageMax = rs.getInt("DAMAGE_MAX");
                final int attackSpeed = rs.getInt("ATTACK_SPEED");
                final double accuracy = rs.getDouble("ACCURACY");
                final double healChance = rs.getDouble("HEAL_CHANCE");
                final int healMin = rs.getInt("HEAL_MIN");
                final int healMax = rs.getInt("HEAL_MAX");
                final double blockChance = rs.getDouble("BLOCK_CHANCE");

                //Instantiates the appropriate DungeonCharacter object based on its name
                return switch (theCharacter) {
                    case WARRIOR -> new Warrior(name, displayChar, health,
                            damageMin, damageMax, attackSpeed, accuracy, blockChance);

                    case PRIESTESS -> new Priestess(name, displayChar, health,
                            damageMin, damageMax, attackSpeed, accuracy, blockChance);

                    case THIEF -> new Thief(name, displayChar, health,
                            damageMin, damageMax, attackSpeed, accuracy, blockChance);

                    case OGRE -> new Ogre(name, displayChar, health,
                            damageMin, damageMax, attackSpeed, accuracy,
                            healChance, healMin, healMax);

                    case GREMLIN -> new Gremlin(name, displayChar, health,
                            damageMin, damageMax, attackSpeed, accuracy,
                            healChance, healMin, healMax);

                    case SKELETON -> new Skeleton(name, displayChar, health,
                            damageMin, damageMax, attackSpeed, accuracy,
                            healChance, healMin, healMax);
                };
            }

        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return null;
    }

    /**
     * Prints a list of all {@link DungeonCharacter}'s initial statistics.
     * Only use this method for testing.
     */
    public static void getCharacters() {
        final String querySearch = "SELECT * FROM dungeonCharacters";

        try (PreparedStatement stmt = myConn.prepareStatement(querySearch)) {
            final ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //prints the common stats among all Dungeon Characters first
                final String name = rs.getString("NAME");
                final String displayChar = rs.getString("DISPLAY_CHAR");
                final int health = rs.getInt("HEALTH");
                final int damageMin = rs.getInt("DAMAGE_MIN");
                final int damageMax = rs.getInt("DAMAGE_MAX");
                final int attackSpeed = rs.getInt("ATTACK_SPEED");
                final double accuracy = rs.getDouble("ACCURACY");

                //if current row contains hero stats, prints relevant hero stats
                if ("+".equals(displayChar)) {
                    final double blockChance = rs.getDouble("BLOCK_CHANCE");

                    System.out.printf(
                            """
                                    Result:\s
                                    [
                                    NAME: %s |\s
                                    DISPLAY_CHAR: %s |\s
                                    HEALTH: %s |\s
                                    DAMAGE_MIN: %s |\s
                                    DAMAGE_MAX: %s |\s
                                    ATTACK_SPEED: %s |\s
                                    ACCURACY: %s |\s
                                    BLOCK_CHANCE: %s |\s
                                    ]%n
                                    """,
                            name, displayChar, health, damageMin, damageMax,
                            attackSpeed, accuracy, blockChance);
                } else { //prints relevant monster stats otherwise
                    final double healChance = rs.getDouble("HEAL_CHANCE");
                    final int healMin = rs.getInt("HEAL_MIN");
                    final int healMax = rs.getInt("HEAL_MAX");

                    System.out.printf(
                            """
                                    Result:\s
                                    [
                                    NAME: %s |\s
                                    DISPLAY_CHAR: %s |\s
                                    HEALTH: %s |\s
                                    DAMAGE_MIN: %s |\s
                                    DAMAGE_MAX: %s |\s
                                    ATTACK_SPEED: %s |\s
                                    ACCURACY: %s |\s
                                    HEAL_CHANCE: %s |\s
                                    HEAL_MIN: %s |\s
                                    HEAL_MAX: %s |\s
                                    ]%n
                                    """,
                            name, displayChar, health, damageMin, damageMax,
                            attackSpeed, accuracy, healChance, healMin, healMax);
                }
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Checks if the {@link SQLiteDataSource} already has data.
     *
     * @return true if data already exists in the {@link SQLiteDataSource}
     */
    private static boolean hasData() {
        final String queryCount = "SELECT COUNT(*) FROM dungeonCharacters";
        boolean hasData = false;

        try (PreparedStatement stmt = myConn.prepareStatement(queryCount)) {
            final ResultSet rs = stmt.executeQuery(queryCount);

            // Assuming the query returns a single value representing the count
            if (rs.next()) {
                final int count = rs.getInt(1);
                hasData = count > 0;
            }

        } catch (final SQLException e) {
            e.printStackTrace();
        }

        // In case of an exception or no data, return false
        return hasData;
    }

    /**
     * Creates a new table that stores all of
     * {@link DungeonCharacter}'s statistics.
     */
    private static void createTable() {
        final String tableName = "dungeonCharacters";
        final String queryCreate = String.format(
                """
                        CREATE TABLE IF NOT EXISTS %s
                        (NAME TEXT NOT NULL,\s
                        DISPLAY_CHAR TEXT NOT NULL,\s
                        HEALTH INTEGER NOT NULL,\s
                        DAMAGE_MIN REAL NOT NULL,\s
                        DAMAGE_MAX REAL NOT NULL,\s
                        ATTACK_SPEED INTEGER NOT NULL,\s
                        ACCURACY REAL NOT NULL,\s
                        HEAL_CHANCE REAL,\s
                        HEAL_MIN INTEGER,\s
                        HEAL_MAX INTEGER,\s
                        BLOCK_CHANCE REAL,\s
                        SKILL TEXT
                        )
                        """, tableName);

        try (PreparedStatement stmt = myConn.prepareStatement(queryCreate)) {
            stmt.executeUpdate();
            insertCharacters();
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Inserts all of {@link DungeonCharacter}'s stats into the database's table.
     */
    private static void insertCharacters() {
        final String insertPriestess = "INSERT INTO dungeonCharacters "
                + "(NAME, DISPLAY_CHAR, HEALTH, DAMAGE_MIN, DAMAGE_MAX, ATTACK_SPEED, ACCURACY, BLOCK_CHANCE) "
                + "VALUES (\"Priestess\", \"+\", 75, 25, 45, 5, 0.7, 0.3)";

        final String insertThief = "INSERT INTO dungeonCharacters "
                + "(NAME, DISPLAY_CHAR, HEALTH, DAMAGE_MIN, DAMAGE_MAX, ATTACK_SPEED, ACCURACY, BLOCK_CHANCE) "
                + "VALUES (\"Thief\", \"+\", 75, 20, 40, 6, 0.8, 0.4)";

        final String insertWarrior = "INSERT INTO dungeonCharacters "
                + "(NAME, DISPLAY_CHAR, HEALTH, DAMAGE_MIN, DAMAGE_MAX, ATTACK_SPEED, ACCURACY, BLOCK_CHANCE) "
                + "VALUES (\"Warrior\", \"+\", 125, 35, 60, 4, 0.8, 0.2)";

        final String insertGremlin = "INSERT INTO dungeonCharacters "
                + "(NAME, DISPLAY_CHAR, HEALTH, DAMAGE_MIN, DAMAGE_MAX, ATTACK_SPEED, ACCURACY, HEAL_CHANCE, HEAL_MIN, HEAL_MAX) "
                + "VALUES (\"Gremlin\", \"9\", 70, 15, 30, 5, 0.8, 0.4, 20, 40)";

        final String insertOgre = "INSERT INTO dungeonCharacters "
                + "(NAME, DISPLAY_CHAR, HEALTH, DAMAGE_MIN, DAMAGE_MAX, ATTACK_SPEED, ACCURACY, HEAL_CHANCE, HEAL_MIN, HEAL_MAX) "
                + "VALUES (\"Ogre\", \"7\", 200, 30, 60, 2, 0.6, 0.1, 30, 60)";

        final String insertSkeleton = "INSERT INTO dungeonCharacters "
                + "(NAME, DISPLAY_CHAR, HEALTH, DAMAGE_MIN, DAMAGE_MAX, ATTACK_SPEED, ACCURACY, HEAL_CHANCE, HEAL_MIN, HEAL_MAX) "
                + "VALUES (\"Skeleton\", \"8\", 100, 30, 50, 3, 0.8, 0.3, 30, 50)";

        //Groups all of SQLite's INSERT statements above and executes the statement group
        final String insertAll = insertPriestess + insertThief + insertWarrior
                + insertGremlin + insertOgre + insertSkeleton;

        try (Statement stmt = myConn.createStatement()) {
            stmt.execute(insertAll);

        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }
}
