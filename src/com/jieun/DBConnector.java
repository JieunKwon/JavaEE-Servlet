package com.jieun;
import java.sql.*;

/**
 * --------------------------------------------- 
 * @author JIEUN KWON (991447941)
 *	
 * TASK : Assighment1
 * --------------------------------------------- 
 *
 * CLASS: DBConnector class
 * 		- to connect database "publiclibrary" 
 * 
 * created Date : Sept 20, 2018
 * updated Date : Sept 23, 2018
 * 
 */

public class DBConnector {
 
		private static final String url="jdbc:mysql://localhost:3306/publiclibrary?useSSL=false";
	    private static final String DriverClass="com.mysql.jdbc.Driver";
	    private static final String user="root";
	    private static final String password="mydb1234";

	    private DBConnector() {
			try {
				Class.forName(DriverClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	 
	    public static Connection getConnection() throws Exception{
	    	Connection connection = null;
			try {
				connection = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				System.out.println("ERROR: Connection Failed "+e.getMessage());
			}
			return connection;

	   }
	 

	    public static void closeConnection(Connection con, Statement st, ResultSet rs) throws Exception{
	            if(con!=null)
	                    con.close();
	            if(st!=null)
	                    st.close();
	            if(rs!=null)
	                    rs.close();
	    }
	}
 