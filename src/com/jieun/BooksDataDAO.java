package com.jieun;

/**
 * --------------------------------------------- 
 * @author JIEUN KWON (991447941)
 *	
 * TASK : Assighment1
 * --------------------------------------------- 
 *
 * CLASS: BooksDataDAO class
 * 		- all tasks to access "Books" table
 * 
 * created Date : Sept 20, 2018
 * updated Date : Sept 23, 2018
 * 
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jieun.DBConnector;


public class BooksDataDAO {

	// connection 
	Connection con = null;
	PreparedStatement pst;
	PreparedStatement pst2;

	
	////////////////////////////////////////////////////////////////////////////
	// addRow
	//
	// INSERT : add new record in books
	public void addRow( String title, String author, double price, String type, String subject)throws Exception {

		// make a query
        String insertQuery = "Insert into books (title,author,price,type,subject) "
        		+ "values (?,?,?,?,?)";
        
        // db connect
        try{
        	
        	// get connection
		    con = DBConnector.getConnection();
		    pst = con.prepareStatement(insertQuery);
		      
			// set
			pst.setString(1,title);
			pst.setString(2,author); 
			pst.setDouble(3,price);
			pst.setString(4,type);
			pst.setString(5,subject);
			
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
	public void updateRow(int bookcode, String author, double price, String type, String subject) throws Exception{
		
		// make a query
		String updateQuery = "Update books "
							+ "set author =?,price =?,type =?,subject =? "
							+ "where bookcode = ?";
        
		// db connection
		try{
           
		    con = DBConnector.getConnection();
		    pst = con.prepareStatement(updateQuery);
		       
		    // set 
			pst.setString(1,author); 
			pst.setDouble(2,price);
			pst.setString(3,type);
			pst.setString(4,subject);
			pst.setInt(5,bookcode);
			
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
	public void selectAllBooks() throws Exception{
		
		// make a query
		String selectQuery = "select * from books order by bookcode";
        
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
			
			// print books all list
			try {
				
				//System.out.println("bookcode /title /author /price /type /subject");
				System.out.printf("%-4s %-25s %-20s %-7s %-20s %-20s \n",
						"code", "title", "author", "price", "type", "subject");	
				
				while (rs.next()) {
					
					 System.out.printf("%-4d %-25s %-20s $%-6.2f %-20s %-20s \n", 
							 rs.getInt("bookcode"),
							 rs.getString("title"),
							 rs.getString("author"),
							 rs.getDouble("price"),
							 rs.getString("type"), 
 							 rs.getString("subject")
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
	// searchBooks
	//
	// SEARCH with Keyword : search books data with key word 
	public void searchBooks(String srhType, String srhKeyword) throws Exception{
		
		// for extended keyword
		srhKeyword = "%" + srhKeyword + "%";
		
		// make a query	
		String selectQuery = "select * from books ";
		
		// decide field to search
		if (srhType == "title") {
			 selectQuery += "where title like ? order by bookcode";
		}
		else {
			selectQuery += "where author like ? order by bookcode";
		}
		
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
				
				System.out.printf("%-4s %-25s %-20s %-7s %-20s %-20s \n",
						"code", "title", "author", "price", "type", "subject");	
						
				while (rs.next()) {
					
					// bookcode /title /author /price /type /subject
					System.out.printf("%-4d %-25s %-20s $%-6.2f %-20s %-20s \n", 
							 rs.getInt("bookcode"),
							 rs.getString("title"),
							 rs.getString("author"),
							 rs.getDouble("price"),
							 rs.getString("type"), 
							 rs.getString("subject")
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
	// delete books data with bookcode
	//  ** Bookcode is FK for library-> delete from library table as well
	public void deleteRow(int bookCode) throws Exception {
		
		// First, delete borrowed-book from library table
		String sql = "delete from library where bookcode =?";
		
		try {
			con = DBConnector.getConnection();
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, bookCode);
			pst.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{ 
			DBConnector.closeConnection(con,pst,null);
		}
		
		// Next, delete books table 
		sql = "delete from books where bookcode =?";
				
		try {
			con = DBConnector.getConnection();
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, bookCode);
			pst.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{ 
			DBConnector.closeConnection(con,pst,null);
		}
			
				
	}
	
	////////////////////////////////////////////////////////////////////////////
	// searchBookTechnology
	//
	// SEARCH books in specific condition - count the available books of 'Technology' type in each branch
	public void searchBookTechnology() throws Exception{
		 
		// make a query for finding branch
		String selectQuery = "select branchcode, branchname from branch order by branchcode";
        
		// db connection
		try{
           
		    con = DBConnector.getConnection();
		    pst = con.prepareStatement(selectQuery);
		       
		    ResultSet rs = null;
		    ResultSet rs2 = null;
		    
			try {
				rs = pst.executeQuery();
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				System.out.println(pst.toString());
				e.printStackTrace();
			}
			
			// print all list
			try {
				 
				System.out.printf("%-6s %-25s %-4s\n",
						"code", "branchName", "books");	
				
				int tmpCode = 0;
				String tmpName = "";
				
				while (rs.next()) {
					
					tmpCode = rs.getInt("branchcode");
					tmpName = rs.getString("branchName");
					 
				//	String selectQuery = "select count(bookcode) from books where type = 'Technology' and "
				//			+ "bookcode not in (select bookcode from library where branchcode = ?)";
						
					 
					 	// make a query2	
						String selectQuery2 = "select count(bookcode) AS cntBooks from books where type = 'Technology'"
											+ " and bookcode not in "
											+ "(select bookcode from library where branchcode = ?)";
						
						 
						    pst2 = con.prepareStatement(selectQuery2);
						    
						    pst2.setInt(1, tmpCode);
						    
						    // search result
							try { 
								rs2 = pst2.executeQuery();
								
							} catch (SQLException e) {
								
								System.out.println(e.getMessage()); 
								
							}
							
							// print out the result
							try {				
								 
								// print info 
								while(rs2.next()) {
									 System.out.printf("%-6d %-25s %-4d \n", 
											 tmpCode, tmpName, rs2.getInt(1));
								}
								 
							} catch (SQLException e) {
								System.out.println(e.getMessage());
								e.printStackTrace();
							}
							
							try {
								rs2.close(); 
							} catch (SQLException e) { 
								e.printStackTrace();
							}
							
							 
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
	// searchBookBeaches
	//
	// SEARCH books in specific condition - list all books data which are not borrowed in ¡®Beaches¡¯ branch 
	public void searchBookBeaches() throws Exception{
		
		// make a query	
		String selectQuery = "select * from books where bookcode not in "
				+ "(select bookcode from library where branchcode = 6434)";
	 
		 
		// db connection
		try{
           
		    con = DBConnector.getConnection();
		    pst = con.prepareStatement(selectQuery);
		    
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
				
				System.out.printf("%-4s %-25s %-20s %-7s %-20s %-20s \n",
						"code", "title", "author", "price", "type", "subject");	
						
				while (rs.next()) {
					
					// bookcode /title /author /price /type /subject
					System.out.printf("%-4d %-25s %-20s $%-6.2f %-20s %-20s \n", 
							 rs.getInt("bookcode"),
							 rs.getString("title"),
							 rs.getString("author"),
							 rs.getDouble("price"),
							 rs.getString("type"), 
							 rs.getString("subject")
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
}
