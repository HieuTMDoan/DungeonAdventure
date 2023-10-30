package com.tcss.dungeonadventure;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDB {


    public static void main(final String[] theArgs) {
        SQLiteDataSource ds = null;

        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:questions.sqlite");

        } catch (final Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println("Opened database successfully");

        final String tableName = "questions";
        final String query = String.format(
                """
                CREATE TABLE IF NOT EXISTS %s
                (QUESTION TEXT NOT NULL, ANSWER TEXT NOT NULL)
                """, tableName);

        try (Connection conn = ds.getConnection(); Statement stmt = conn.createStatement()) {
            final int rv = stmt.executeUpdate(query);
            System.out.println("executeUpdate() returned " + rv);

        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }




        

        // Selecting
        final String querySearch = "SELECT * FROM questions";
        try (Connection conn = ds.getConnection(); Statement stmt = conn.createStatement()) {
            final ResultSet rs = stmt.executeQuery(querySearch);

            while (rs.next()) {
                final String question = rs.getString("QUESTION");
                final String answer = rs.getString("ANSWER");

                System.out.printf("Result: [Question: %s | Answer: %s]%n", question, answer);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }






    }






}
