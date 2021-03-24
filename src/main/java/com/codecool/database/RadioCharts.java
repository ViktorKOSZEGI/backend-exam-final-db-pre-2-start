package com.codecool.database;

import java.sql.*;

public class RadioCharts {
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    public RadioCharts(String dbUrl, String dbUser, String dbPassword) {
        this.DB_URL = dbUrl;
        this.DB_USER = dbUser;
        this.DB_PASSWORD = dbPassword;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database not reachable.");
        }
        return connection;
    }

    public String getMostPlayedSong() {
        String winner = "";
        int sum = 0;
        String query = "SELECT song, sum(times_aired) FROM music_broadcast GROUP BY song ORDER BY sum(times_aired) DESC LIMIT 1;";
//        oaw:
//        String query = "SELECT competitor_name FROM climbing_cup GROUP BY competitor_name ORDER BY sum(points_earned) DESC LIMIT 1;";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
//                winner = resultSet.getString(1);
//                oaw:
                winner = resultSet.getString("song");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return winner;
    }

    public String getMostActiveArtist() {
        String mostActiveArtist = "";
        String sql = "SELECT DISTINCT artist, count(song) FROM music_broadcast GROUP BY artist ORDER BY count(song) DESC LIMIT 1;";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                mostActiveArtist = resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return mostActiveArtist;
    }
}
