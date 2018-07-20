package util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionFactory {
	private static String driver;
	private static String url;
	private static String user;
	private static String password;

	static {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("src/file/db.properties"));
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			user = properties.getProperty("user");
			password = properties.getProperty("password");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws Exception {
		// 1.注册驱动
		Class.forName(driver);
		// 2.建立数据库连接
		Connection connection = DriverManager.getConnection(url, user, password);

		return connection;

	}

	// 关闭资源 connection statement ps rs
	public static void close(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null)
				resultSet.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			if (statement != null)
				statement.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// 关闭资源 connection statement ps rs
	public static void close(Connection connection, Statement statement) {

		try {
			if (statement != null)
				statement.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

//			public static void main(String[] args) {
//				try {
//					Connection connection = getConnection();
//					System.out.println(connection);
//				} catch (Exception e) {
//					
//					e.printStackTrace();
//				}
//			}
}
