package com.example.application.dao;

import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class Database {

    public Jdbi jdbi;
    public Connection conn;
    private final String currentDatabase = "ux_iot";
    private String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=" + currentDatabase;
    private String user = "postgres";
    private String password = "postgre";

    public void startDatabse() throws SQLException {
        // Initialize current JDBI database URL, user and password
        jdbi = Jdbi.create(url, user, password);
        conn = jdbi.open().getConnection();
        conn.setAutoCommit(true);
    }

}
