package com.jieun;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat; 
import java.util.Date; 

/**
 * --------------------------------------------- 
 * @author JIEUN KWON (991447941)
 *	
 * TASK : Assighment1
 * 
 * created Date : Sept 23, 2018
 * updated Date : Sept 25, 2018
 * --------------------------------------------- 
 *
 * CLASS: LibraryDataDAO class
 * 		- to access "library" table 
 * 		- add new records
 * 		- select records
 * 		- update records for return date
 * 
 */

public class LibraryDataDAO {
	
	// DB connection 
	Connection con = null;
	PreparedStatement pst;
	PreparedStatement pst2;


	////////////////////////////////////////////////////////////////////////////
	// updateRow
	//
	// UPDATE : update return date and calculate fine amount 
	public void updateRow(int branchCode, int bookCode, String reDate)throws Exception {

		
		// make a query	to select data	
		String selectQuery = "select borrowdate, returndate "
				+ "from library "
				+ "where branchcode = ? and bookcode=?";
		
		// fineAmount to update
		double fineAmount = 0.00;
		
		// db connection
		try{
		
			con = DBConnector.getConnection();
			pst = con.prepareStatement(selectQuery);
			pst.setInt(1, branchCode);
			pst.setInt(2, bookCode);
			
			ResultSet rs = null;
			
			// execute query
			try {
				rs = pst.executeQuery();
				
			} catch (SQLException e) { 
				e.printStackTrace();
			}
			
			// print result
			try {
			 
				while (rs.next()) {
					// borrow date & return date
					Date borrowDate = rs.getDate("borrowdate");
					Date returnDate = null;
					 
					// change format of date for return date (String->Date)
				 	try
			        {
					 	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						returnDate = formatter.parse(reDate);
			        }
			        catch(ParseException e)
			        {
			            e.printStackTrace();
			        } 
 
				 	// convert to million seconds					
					long diffdays = ((returnDate.getTime() - borrowDate.getTime()) / (60*60*24*1000)); 
					
					// calculate fine when the delay is over 21 days  					
				    if (diffdays > 21)
					{
						fineAmount = (diffdays -21) * 0.5;
					}
				    
				    System.out.println(diffdays + "///" + fineAmount);
					 
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
					
		 	 	 	
			// make a query to update fine data 
	        String updateQuery = "update library set fineamount=?, returndate=DATE(?)"
	        		+ " where bookcode=? and branchcode=?";
	    
	        // db connect
	        try{
	        	 
			    pst = con.prepareStatement(updateQuery);
			      
				// set
			    pst.setDouble(1, fineAmount);
			    pst.setString(2, reDate);
				pst.setInt(3,bookCode);
				pst.setInt(4,branchCode);   
				  
				// execute
	            pst.executeUpdate();
	            
	        }catch(Exception e){
	            e.printStackTrace();
	        }
        
		}finally{
        	DBConnector.closeConnection(con,pst,null);
        }
    }
	
	
	////////////////////////////////////////////////////////////////////////////
	// addRow
	//
	// INSERT : add new record in library
	public void addRow(int bookcode, int branchcode)throws Exception {

		// make a query
        String insertQuery = "Insert into library (bookcode, branchcode, borrowdate, returndate, fineamount) "
        		+ "values (?,?, curdate(), adddate(curdate(), INTERVAL 21 DAY),0.00)";
        
        // db connect
        try{
        	
        	// get connection
		    con = DBConnector.getConnection();
		    pst = con.prepareStatement(insertQuery);
		     
		    
			// set
			pst.setInt(1,bookcode);
			pst.setInt(2,branchcode);   
			 
			
			// execute
            pst.executeUpdate();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        	DBConnector.closeConnection(con,pst,null);
        }
    }
	
	////////////////////////////////////////////////////////////////////////////
	// selectLibraryFine
	//
	// SELECT : show all total fine amount for each branch
	public void selectLibraryFine() throws Exception{
	
		// make a query		
		String selectQuery = "select branch.branchcode, branch.branchname, F.sumfine "
				+ "from branch LEFT OUTER JOIN "
				+ "(select branchcode,  Sum(fineamount) AS sumfine from library "
				+ "group by branchcode) F "
				+ "ON (branch.branchcode = F.branchcode)";
	
		// db connection
		try{
		
			con = DBConnector.getConnection();
			pst = con.prepareStatement(selectQuery);
			
			ResultSet rs = null;
	
			// execute query
			try {
				rs = pst.executeQuery();
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				System.out.println(pst.toString());
				e.printStackTrace();
			}
	
			// print result
			try {
			
				// branch info + fine amount
				System.out.printf("%-25s %-6s \n",
				"branch", "total fine");	
				
				while (rs.next()) {
				
					// branch info (code & name) + fine amount
					System.out.printf("%-4d %-20s $%-6.2f \n",  
					rs.getInt("branchcode"),
					rs.getString("branchname"), 
					rs.getDouble("sumfine")
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
	// selectAllLibrary
	//
	// SELECT : show all books information of library by ordering branch and borrowed date
	public void selectAllLibrary() throws Exception{
		
		// make a query		
		String selectQuery = "select library.branchcode, branch.branchname, "
				+ "library.bookcode, books.title, "
				+ "library.borrowdate, library.returndate, library.fineamount "
				+ "from library "
				+ "INNER JOIN books ON (library.bookcode = books.bookcode) "
				+ "LEFT OUTER JOIN branch ON (library.branchcode = branch.branchcode) "
				+ "order by library.branchcode asc, library.borrowdate desc";
		
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
			
			// print  
			try {
				
				// print out all data
				// branch info + book info + date info + fine
				System.out.printf("%-25s %-29s %-20s %-20s %-6s \n",
						"branch", "book", "borrow date", "return date", "fine");	
				
				while (rs.next()) {
					
					// print out data from db
					 System.out.printf("%-4d %-20s %-3d %-25s %-20s %-20s $%-6.2f \n",  
							 rs.getInt("branchcode"),
							 rs.getString("branchname"), 
							 rs.getInt("bookcode"),
							 rs.getString("title"), 
							 rs.getDate("borrowdate"),
							 rs.getDate("returndate"),
							 rs.getDouble("fineamount")
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
