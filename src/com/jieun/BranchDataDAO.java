package com.jieun;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jieun.DBConnector;

/**
 * --------------------------------------------- 
 * @author JIEUN KWON (991447941)
 *	
 * TASK : Assighment1
 *
 * created Date : Sept 23, 2018
 * updated Date : Sept 23, 2018
 * --------------------------------------------- 
 *
 * CLASS: BranchDataDAO class
 * 		- all tasks to access "Branch" table
 * 		- add new records
 * 		- select records
 * 		- search records with keyword
 * 		- select some data with specific conditions
 * 
 */

public class BranchDataDAO {

	
	Connection con = null;
	PreparedStatement pst;
	
	////////////////////////////////////////////////////////////////////////////
	// addRow
	//
	// INSERT : add new record in books
	/*
	 *  branchcode int NOT NULL AUTO_INCREMENT PRIMARY KEY,
		branchname varchar(50) NOT NULL,
		address varchar(50) NOT NULL,
		postalcode varchar(6) NOT NULL  
	 */
	public void addRow(String branchName, String address, String postalCode)throws Exception {

		// make a query
        String insertQuery = "Insert into branch (branchName,address,postalCode) "
        		+ "values (?,?,?)";
        
        // db connect
        try{
		    con = DBConnector.getConnection();
		    pst = con.prepareStatement(insertQuery);
		 
			// set
			pst.setString(1,branchName);
			pst.setString(2,address);  
			pst.setString(3,postalCode); 
			
			// execute
            pst.executeUpdate();
            
        }catch(Exception e){
                e.printStackTrace();
        }finally{
                DBConnector.closeConnection(con,pst,null);
        }
    }
		
	////////////////////////////////////////////////////////////////////////////
	// updateRow
	//	
	// UPDATE : update other fields with bookcode 
	public void updateRow(int branchCode, String branchName, String address, String postalCode) throws Exception{
		
		// make a query
		String updateQuery = "Update branch "
							+ "set branchname =?,address =?,postalCode =? "
							+ "where branchCode = ?";
        
		// db connection
		try{
           
		    con = DBConnector.getConnection();
		    pst = con.prepareStatement(updateQuery);
		       
		    // set 
			pst.setString(1,branchName);
			pst.setString(2,address); 
			pst.setString(3,postalCode); 
			pst.setInt(4,branchCode);
			
			// execute
            pst.executeUpdate();
            
        }catch(Exception e){
                e.printStackTrace();
        }finally{
                DBConnector.closeConnection(con,pst,null);
        }
    }
	
	////////////////////////////////////////////////////////////////////////////
	// selectAllBooks
	//
	// SELECT : show all books data 
	public void selectAllBranch() throws Exception{
		
		// make a query
		String selectQuery = "select * from branch order by branchcode";
        
		// db connection
		try{
           
		    con = DBConnector.getConnection();
		    pst = con.prepareStatement(selectQuery);
		       
		    ResultSet rs = null;
 
			try {
				rs = pst.executeQuery();
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				System.out.println(pst.toString());
				e.printStackTrace();
			}
			
			// print all list
			try {
				 
				System.out.printf("%-6s %-25s %-40s %-6s\n",
						"code", "branchName", "address", "postal code");	
				
				while (rs.next()) {
					
					 System.out.printf("%-6d %-25s %-40s %-6s\n", 
							 rs.getInt("branchcode"),
							 rs.getString("branchName"),
							 rs.getString("address"),
							 rs.getString("postalcode")
							 );
					  
					 
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			try {
				rs.close();
				 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		  
        }catch(Exception e){
                e.printStackTrace();
        }finally{
                DBConnector.closeConnection(con,pst,null);
        }
    }
	
	////////////////////////////////////////////////////////////////////////////
	// searchBranch
	//
	// SEARCH with Keyword : search branch data with keyword 
	public void searchBranch(String srhType, String srhKeyword) throws Exception{
		
		// for extended keyword
		srhKeyword = "%" + srhKeyword + "%";
		
		// make a query	
		String selectQuery = "select * from branch where " + srhType + " like ? order by branchcode";
  	
		// db connection
		try{
           
		    con = DBConnector.getConnection();
		    pst = con.prepareStatement(selectQuery);
		      
		    try { 
				pst.setString(1, srhKeyword);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		    
		    // search result
		    ResultSet rs = null;
		    
			try {
				 
				rs = pst.executeQuery();
				
			} catch (SQLException e) {
				
				System.out.println(e.getMessage());
				System.out.println(pst.toString());
				e.printStackTrace();
				
			}
			
			// print out the result
			try {				
				
				System.out.printf("%-6s %-25s %-40s %-6s\n",
						"code", "branch name", "address", "postal code");	
						
				while (rs.next()) {
					
					// code /title /author /price /type /subject
					 System.out.printf("%-6d %-25s %-40s %-6s\n", 
							 rs.getInt("branchcode"),
							 rs.getString("branchName"),
							 rs.getString("address"),
							 rs.getString("postalcode")
							 );
					 
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			try {
				rs.close();
				 
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		  
        }catch(Exception e){
                e.printStackTrace();
        }finally{
                DBConnector.closeConnection(con,pst,null);
        }
    }
	
	////////////////////////////////////////////////////////////////////////////
	// deleteRow
	//
	// delete data with branchcode
	public void deleteRow(int branchcode) throws Exception {
		
		String sql = "delete from branch where branchcode =?";
				
		try {
			con = DBConnector.getConnection();
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, branchcode);
			pst.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{ 
			DBConnector.closeConnection(con,pst,null);
		}
		 		
	}
	
	
}
