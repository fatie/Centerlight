package org.centerlight.attachment.changeFilelocation.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {

	public static boolean close(Connection connection){
		if(connection != null){
			try {
				connection.close();
				return true;
			} catch (SQLException e){
				e.printStackTrace();
				return false;
			}
		}
		else return false;
	}
	public static boolean close(Statement statement){
		if(statement != null){
			try {
				statement.close();
				return true;
			} catch (SQLException e){
				e.printStackTrace();
				return false;
			}
		}
		else return false;
	}
	public static boolean close(ResultSet resultSet){
		if(resultSet != null){
			try {
				resultSet.close();
				return true;
			} catch (SQLException e){
				e.printStackTrace();
				return false;
			}
		}
		else return false;
	}
}
