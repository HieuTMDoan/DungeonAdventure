package com.tcss.dungeonadventure.model;

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
import org.sqlite.SQLiteDataSource;

/**
 * Utility class that uses JDBC API to store and manage the game's SQLite database.
 * The database stores all of {@link DungeonCharacter}'s initial statistics.
 *
 * @author Hieu, Aaron, Sunny
 * @version TCSS 360: Fall 2023
 */
public class SQLiteDB {
    /**
     * Represents the connection with the data source.
     */
    private Connection myConn;

    /**
     * Represents the {@link DungeonCharacter} object
     * with initial stats extracted from the database.
     */
    private DungeonCharacter myCharacter;

    /**
     * Initializes the data source and establishes a connection with it.
     * Auto-generates the data source if it doesn't already exist.
     */
    public SQLiteDB() {
        try {
            final SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:dungeonCharacters.sqlite");
            myConn = ds.getConnection();
            createTable();

        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println("Successfully opened database");
    }

    /**
     * Creates an empty table that will store {@link DungeonCharacter}'s statistics.
     */
    public void createTable() {
        final String tableName = "dungeonCharacters";
        final String query = String.format(
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

        try (PreparedStatement stmt = myConn.prepareStatement(query)) {
            final int rv = stmt.executeUpdate();
            System.out.println("executeUpdate() returned " + rv);

        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Prints a list of all {@link DungeonCharacter}'s initial statistics.
     * Only use this method for testing.
     */
    public void getCharacters() {
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
                    final String skill = rs.getString("SKILL");

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
                            SKILL: %s |\s
                            ]%n
                            """,
                            name, displayChar, health, damageMin, damageMax,
                            attackSpeed, accuracy, blockChance, skill);
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
     * Returns a {@link DungeonCharacter}'s initial statistics based on its name.
     *
     * @param theName the name of the {@link DungeonCharacter} to be printed
     * @return the queried {@link DungeonCharacter}'s initial statistics
     */
    public DungeonCharacter getCharacterByName(final String theName) {
        final String querySearch = "SELECT * FROM dungeonCharacters WHERE NAME = ?";

        try (PreparedStatement stmt = myConn.prepareStatement(querySearch)) {
            stmt.setString(1, theName);
            final ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                myCharacter = createCharacterFromResultSet(rs);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return myCharacter;
    }

    /**
     * Private factory method that takes the retrieved result set
     * and returns an instance of the appropriate child class of {@link DungeonCharacter}.
     *
     * @param theResultSet the retrieved result set containing the statistics
     *                     of the queried {@link DungeonCharacter}
     * @return the queried child class of {@link DungeonCharacter}
     */
    private DungeonCharacter createCharacterFromResultSet(final ResultSet theResultSet) {
        DungeonCharacter character = null;
        try {
            if (theResultSet.next()) {
                final String name = theResultSet.getString("NAME");
                switch (name) {
                    case "Priestess" -> character = new Priestess(name);
                    case "Thief" -> character = new Thief(name);
                    case "Warrior" -> character = new Warrior(name);
                    case "Ogre" -> character = new Ogre();
                    case "Gremlin" -> character = new Gremlin();
                    case "Skeleton" -> character = new Skeleton();
                    default -> { }
                }
            }

        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return character;
    }
}
