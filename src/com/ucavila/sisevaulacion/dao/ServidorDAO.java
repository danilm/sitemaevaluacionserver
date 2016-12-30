package com.ucavila.sisevaulacion.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class ServidorDAO {
	private final String DB_DRIVER ="com.mysql.jdbc.Driver";
	private final String DB_CONNECTION ="jdbc:mysql://localhost/ucavila";
	private final String DB_USER = "ucavila";
	private final String DB_PASSWORD = "qwerty";

	
	
	public void insertarIP(String ip) throws SQLException{
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		String insertarIPQuery = "INSERT INTO CONEXIONES (IP) VALUES (?)";
		
		try {
			//Class.forName("com.mysql.jdbc.Driver").newInstance();
			//con = DriverManager.getConnection("jdbc:mysql://localhost/ucavila","ucavila","qwerty");
			dbConnection = getDBConnection();
			
			PreparedStatement ps = dbConnection.prepareStatement(insertarIPQuery);
			ps.setString(1, ip);
			
			ps.executeUpdate();
			
			System.out.println("IP insertada en la tabla de conexiones!");
			
		
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
	}
	
	private Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}
}
