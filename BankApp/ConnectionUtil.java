package com.bank.sql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionUtil {
	static Logger logger;

	private ConnectionUtil() {
		
	}

	public static Connection getConnection() throws SQLException {

		String url = "jdbc:oracle:thin:@revychan.cqj8lbafzffy.us-east-2.rds.amazonaws.com:1521:ORCL";
		String user = "myusfdb";
		String pass = "pas$w0rd";

		return DriverManager.getConnection(url, user, pass);

	}


	public static void printRS(ResultSet rs) throws SQLException {
		logger.debug("Printing ResultSet");
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1)
					System.out.print(",  ");
				String columnValue = rs.getString(i);
				System.out.print(columnValue + "\t " + rsmd.getColumnName(i));
			}
			System.out.println("");
		}
	}
}
