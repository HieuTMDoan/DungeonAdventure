package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.sqlite.SQLiteDataSource;

public class SQLiteDB {
    private Connection myConn;
    private SQLiteDataSource myDS;


    public SQLiteDB() {
        try {
            myDS = new SQLiteDataSource();
            myDS.setUrl("jdbc:sqlite:dungeonCharacters.sqlite");
            myConn = myDS.getConnection();
            createTable();

        } catch (final Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println("Successfully opened database");
    }

    public void createTable() {
        final String tableName = "dungeonCharacters";
        final String query = String.format(
                """
                        CREATE TABLE IF NOT EXISTS %s
                        (NAME TEXT NOT NULL,\s
                        HEALTH INTEGER NOT NULL,\s
                        DAMAGE_MIN REAL,\s
                        DAMAGE_MAX REAL,\s
                        ATTACK_SPEED INTEGER,\s
                        ACCURACY REAL
                        )
                        """, tableName);

        try (Statement stmt = myConn.createStatement()) {
            final int rv = stmt.executeUpdate(query);
            System.out.println("executeUpdate() returned " + rv);

        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void getCharacters() {
        final String querySearch = "SELECT * FROM dungeonCharacters";
        try (Connection conn = myDS.getConnection(); Statement stmt = conn.createStatement()) {
            final ResultSet rs = stmt.executeQuery(querySearch);

            while (rs.next()) {
                final String name = rs.getString("NAME");
                final String health = rs.getString("HEALTH");
                final String damageMin = rs.getString("DAMAGE_MIN");
                final String damageMax = rs.getString("DAMAGE_MAX");
                final String attackSpeed = rs.getString("ATTACK_SPEED");
                final String accuracy = rs.getString("ACCURACY");

                System.out.printf("Result: [NAME: %s | HEALTH: %s | DAMAGE_MIN: %s | DAMAGE_MAX: %s | ATTACK_SPEED: %s | ACCURACY: %s]%n",
                        name, health, damageMin, damageMax, attackSpeed, accuracy);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void insertCharacter(final DungeonCharacter theCharacter) {
        final String insertSQL = "INSERT INTO characters (name, level, health, damage_min, damage_max, attack_speed, accuracy) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = myConn.prepareStatement(insertSQL)) {
            statement.setString(1, theCharacter.getName());
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
}
