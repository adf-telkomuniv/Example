/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author dee
 */
public class Database {

    private String server = "jdbc:mysql://localhost:3306/test";
    private String dbuser = "root";
    private String dbpasswd = "";
    private Statement statement = null;
    private Connection connection = null;

    public Database(String server, String dbuser, String dbpasswd) {
        this.server = server;
        this.dbuser = dbuser;
        this.dbpasswd = dbpasswd;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(server, dbuser, dbpasswd);
            statement = connection.createStatement();
        } catch (Exception e) {
            throw new AssertionError("Error Connection to Database");
        }
    }

}
