package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.monsters.Gremlin;
import com.tcss.dungeonadventure.objects.monsters.Ogre;
import com.tcss.dungeonadventure.objects.monsters.Skeleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
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
                        DAMAGE_MIN REAL,\s
                        DAMAGE_MAX REAL,\s
                        ATTACK_SPEED INTEGER,\s
                        ACCURACY REAL
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
     * Stores a {@link DungeonCharacter}'s statistics in a new row in the database table.
     *
     * @param theCharacter the character to be stored in the database
     */
    public void insertCharacter(final DungeonCharacter theCharacter) {
        //TODO: figure out how to insert info of the Heroes and Monsters,
        // as this method currently only works for storing info of the abstract Dungeon Characters
        final String insertSQL = "INSERT INTO dungeonCharacters "
                                 + "(name, display_char, health, damage_min, damage_max, attack_speed, accuracy) "
                                 + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = myConn.prepareStatement(insertSQL)) {
            statement.setString(1, theCharacter.getName());
            statement.setString(2, String.valueOf(theCharacter.getDisplayChar()));
            statement.setInt(3, theCharacter.getHealth());
            statement.setInt(4, theCharacter.getMinDamage());
            statement.setInt(5, theCharacter.getMaxDamage());
            statement.setInt(6, theCharacter.getAttackSpeed());
            statement.setDouble(7, theCharacter.getAccuracy());
            statement.executeUpdate();

        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints a list of all {@link DungeonCharacter}'s initial statistics.
     */
    public void getCharacters() {
        //TODO: figure out how to printing out info of the Heroes and Monsters,
        // as this method currently only works for printing out info of the abstract Dungeon Characters
        final String querySearch = "SELECT * FROM dungeonCharacters";

        try (PreparedStatement stmt = myConn.prepareStatement(querySearch)) {
            final ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                final String name = rs.getString("NAME");
                final String displayChar = rs.getString("DISPLAY_CHAR");
                final String health = rs.getString("HEALTH");
                final String damageMin = rs.getString("DAMAGE_MIN");
                final String damageMax = rs.getString("DAMAGE_MAX");
                final String attackSpeed = rs.getString("ATTACK_SPEED");
                final String accuracy = rs.getString("ACCURACY");

                System.out.printf(
                        """
                        Result:\s
                        [NAME: %s |\s
                        DISPLAY_CHAR: %s |\s
                        HEALTH: %s |\s
                        DAMAGE_MIN: %s |\s
                        DAMAGE_MAX: %s |\s
                        ATTACK_SPEED: %s |\s
                        ACCURACY: %s]%n
                        """,
                        name, displayChar, health, damageMin, damageMax, attackSpeed, accuracy);
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
        DungeonCharacter character = null;

        try (PreparedStatement stmt = myConn.prepareStatement(querySearch)) {
            stmt.setString(1, theName);
            final ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
//                final String name = rs.getString("NAME");
//                final String displayChar = rs.getString("DISPLAY_CHAR");
//                final int health = rs.getInt("HEALTH");
//                final int damageMin = rs.getInt("DAMAGE_MIN");
//                final int damageMax = rs.getInt("DAMAGE_MAX");
//                final int attackSpeed = rs.getInt("ATTACK_SPEED");
//                final double accuracy = rs.getDouble("ACCURACY");
                character = createCharacterFromResultSet(rs);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return character;
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
        //TODO: figure out how to return info of the Heroes and Monsters,
        // as this method currently only works for returning info of the abstract Dungeon Characters
        DungeonCharacter character = null;

        try {
            if (theResultSet.next()) {
                String characterType = theResultSet.getString("DISPLAY_CHAR");
                if (Objects.equals(characterType, String.valueOf(TileChars.Player.PLAYER))) {
                    character = new Hero();
                } else if (Objects.equals(characterType, String.valueOf(TileChars.Monsters.OGRE))) {
                    character = new Ogre();
                } else if (Objects.equals(characterType, String.valueOf(TileChars.Monsters.GREMLIN))) {
                    character = new Gremlin();
                } else if (Objects.equals(characterType, String.valueOf(TileChars.Monsters.SKELETON))) {
                    character = new Skeleton();
                }
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return character;
    }
}
