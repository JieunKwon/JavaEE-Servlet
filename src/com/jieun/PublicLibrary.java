package com.jieun;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * --------------------------------------------- 
 * @author JIEUN KWON (991447941)
 *	
 * TASK : Assighment1
 * --------------------------------------------- 
 *
 * CLASS: PublicLibrary class
 * 		- contain main()
 * 		- to get information for task using Scanner
 * 		- to select Main menu/Sub menu according to user selection
 * 		
 * 
 * created Date : Sept 20, 2018
 * updated Date : Sept 25, 2018
 * 
 */

public class PublicLibrary {
 
		// various  
		private static String quitChk = "n";
		private static int mainOption = 0;		// var for first menu option 
		private static int subOption = 0;		// var for second menu option
		
		// for books data
		private static int bookCode = 0;
		private static String title ="";
		private static String author="";
		private static double price=0.00;
		private static String type="";
		private static String subject="";
			
		// for branch data
		private static int branchCode = 0;
		private static String branchName = "";
		private static String address = "";
		private static String postalCode = "";
		
		// for library data
		
		public static void main(String[] args) {
			
			// Declare member vars
			
			try{
			
				// Object for DB tasks
				BooksDataDAO books = new BooksDataDAO();  
				BranchDataDAO branch = new BranchDataDAO();
				LibraryDataDAO library = new LibraryDataDAO();
				 
				 // get numbers from user
			    Scanner in;
				try {
					
					in = new Scanner(System.in);
					
					
					do{
						 
						// Option selection: Books / Branch / Library
						System.out.print("\nPlease input a number for the following options\n"
								+ "1 - Books \n"
								+ "2 - Branch \n"
								+ "3 - Library \n");
						mainOption = in.nextInt();
						in.nextLine();
						
//////////////////////////////////////////////// 
//	 	BOOKS menu
////////////////////////////////////////////////
					 
						while(mainOption == 1) {
							 
							// Detail Option selection: 
							System.out.print("\nPlease input a number for the following options\n"
									+ "1 - Add Books\n"
									+ "2 - Update Books\n"
									+ "3 - Delete Books\n" 
									+ "4 - List All Books\n"
									+ "5 - Search Books\n"
									+ "6 - Quit\n");
							 
							subOption = in.nextInt();
							in.nextLine();
								 
							 switch(subOption) {
							 
							 	// add data
								 case 1:
									
									// call method to get information
									getBooksNewInfo();
									
									// db - execute
									books.addRow(title, author, price, type, subject);
									
									System.out.println("A row successully completed");
									
									break;
								
								// update data
								 case 2:
									 
									 // show all books
									 books.selectAllBooks();
									 
									// Option selection: bookcode
									System.out.println("\nPlease input the bookcode to update.");
									bookCode = in.nextInt();
									in.nextLine();
									
									// call method to get information
									getBooksUpdateInfo();
									
									books.updateRow(bookCode, author, price, type, subject);
							         
									System.out.println("A row successully completed");
									
									break;
								
								 // delete data
								 case 3:
									 
									// show all books
									 books.selectAllBooks();
									 
									// Option selection: bookcode
									System.out.println("\nPlease select the book code to delete.");
									bookCode = in.nextInt();
									in.nextLine();
									
									// db execute
									books.deleteRow(bookCode);
							         
									System.out.println("A row successully completed");
									
									 break;
								 
							     // select all data
								 case 4:
									// show all books
									 books.selectAllBooks();
									 
									 break;
									 
								 // search data using title or author
								 case 5:
									 
									 String srhType = "";
									 String srhKeyword = "";
									 
									// get information for searching
									System.out.print("Please select the option you want to search\n"
											+ "1 - Title\n"
											+ "2 - Author\n");
									int srh = in.nextInt();
									in.nextLine();
									
									if (srh == 1) 
										srhType = "title";
									else
										srhType = "author";
									
									// get information for keyword
									System.out.print("Please input keyword for " + srhType+ "\n");
									srhKeyword = in.nextLine();
									
									// 
									books.searchBooks(srhType, srhKeyword);
									
									break;
								
								 // Quit program
								 case 6:
									 askQuit();
									  
									break;
									
								 default:
									 
								 
							 }
						}	 
////////////////////////////////////////////////
// BRANCH menu
////////////////////////////////////////////////
 
							while(mainOption == 2) {
								// Detail Option selection: 
								System.out.print("\nPlease input a number for the following options\n"
										+ "1 - Add Branch\n"
										+ "2 - Update Branch\n"
										+ "3 - List All Branches\n" 
										+ "4 - Search Branches\n"
										+ "5 - List books which are not borrowed in Beaches branch\n"
										+ "6 - Count the available technology books in each Branch\n"									
										+ "7 - Quit\n");
								 
								subOption = in.nextInt();
								in.nextLine();
									 
								 switch(subOption) {
								 
								 	// add data
									 case 1:
										
										// get new data info from user
										getBranchNewInfo();
										
										// db access
										branch.addRow(branchName, address, postalCode);
										
										System.out.println("A row successully completed");
										 
										break;
								     
									 case 2:
											
										// show all data to choose
										branch.selectAllBranch();
										 
										// Option selection
										System.out.println("\nPlease input the code to update.");
										branchCode = in.nextInt();
										in.nextLine();
										
										// call method to get information
										getBranchUpdateInfo();
										
										// update db
										branch.updateRow(branchCode, branchName, address, postalCode);
								         
										System.out.println("A row successully completed");
										
										break;
									 
									 // list all data     
									 case 3: 
										
										branch.selectAllBranch();
										 
										break;
										 
									 case 4:
										 
										 String srhType = "";
										 String srhKeyword = "";
										 
										// get information for searching
										System.out.print("Please select the option you want to search\n"
												+ "1 - Branch name\n"
												+ "2 - Porstal code\n");
										int srh = in.nextInt();
										in.nextLine();
										
										if (srh == 1) 
											srhType = "branchname";
										else
											srhType = "postalcode";
										
										// get information for keyword
										System.out.print("Please input keyword for " + srhType+ "\n");
										srhKeyword = in.nextLine();
										
										// db access
										branch.searchBranch(srhType, srhKeyword);
									    break;
	
									 // List books which are not borrowed in Beaches branch
									 case 5:
										 books.searchBookBeaches();
										 break;
									 
								     //	Count the available technology books in each Branch 
									 case 6:
										 books.searchBookTechnology();
										 break;
										 
									 // Quit 	 
									 case 7:
										 askQuit();
										 break;
										 
									 default:
								 }			 
							}
////////////////////////////////////////////////
//		LIBRARY menu
////////////////////////////////////////////////
					  
						while(mainOption == 3) {	
							// Detail Option selection: 
							System.out.print("\nPlease input a number for the following options\n"
									+ "1 - New Library Data (Borrow Books)\n"
									+ "2 - Update Return Date\n" 
									+ "3 - List All Borrowed books\n"
									+ "4 - List Total Fine Amount for each branch\n" 
									+ "5 - Quit\n");
							 
							subOption = in.nextInt();
							in.nextLine();
								 
							 switch(subOption) {
							 
							 	// add data - new borrow book
								 case 1:
								
									 // get new data info from user
									 getLibraryNewInfo();
										
									 library.addRow(bookCode, branchCode);
									 break;
									 
							     // update data - 'return date' 
								 case 2:
									
									// show all records to select 
									library.selectAllLibrary();
									 
									// get information
									getLibraryNewInfo();
									
									// RETURN DATE
									System.out.println("Please input RETURN DATE(yyyy-mm-dd) to update");
									String reDate = in.nextLine();
									  
									// DB access
									library.updateRow(branchCode, bookCode, reDate);
									break;
								 
								  
								 // List all data of library for showing book title and branch name
								 case 3:		
									
									 library.selectAllLibrary();
									 
									 break;
								
								// List the total fine amount for each Branch 
								 case 4:
									 
									 library.selectLibraryFine();
									 
									 break;
									 
								 // Quit
								 case 5:
									 askQuit();
									 break;
									 
								 default:
							 }			 
						 
						 }
					}while(quitChk.equalsIgnoreCase("n"));
					
					in.close();
					
					
				}catch(InputMismatchException ime) {
					 
					System.err.println("Please try again!");
					
					
				}
				
				
			}
			catch(Exception e)
			{
				e.getMessage();
			}
				
			
		}

		//////////////////////////////
		// method : getLibraryNewInfo
		//			get new branchcode and bookcode information to add new library record	
		private static void getLibraryNewInfo() {
			
			Scanner in;
			try {
				in = new Scanner(System.in);
				
				// get information for branch data	
				// branch code (int)
				System.out.print("Please input BRANCH CODE\n");							 
				branchCode = in.nextInt();
						in.nextLine();
				
				// book code (int)
				System.out.print("Please input BOOK CODE\n");							 
				bookCode = in.nextInt();
						in.nextLine();
			 
				
			}catch(Exception e) {
				e.getMessage();
				
			}
			
			
		}

		//////////////////////////////
		// method : askQuit
		//			ask to user 	
		private static void askQuit() {
			Scanner in;
			try {
				in = new Scanner(System.in);
				
				// user can choose exit or go main
				System.out.print("Do you want to quit? \n"
				 		+ "y - exit program\n"
				 		+ "n - go to main menu\n");							 
				 quitChk = in.nextLine();
				 
				 // to main page
				 mainOption = 0;
				 
				 
				 // message for quit
				 if(quitChk.equalsIgnoreCase("y")) {
					 System.out.println("Bye!");
				 }else {
					 mainOption = 0;
				 }
				 
			//	 in.close();
				 
			}catch(Exception e)
			{
				e.getMessage();
			}
			
			
		}
		
		//////////////////////////////
		// method : getBooksUpdateInfo
		//			get all new information to update record
		private static void getBranchUpdateInfo() {
			 
			Scanner in;
			try {
				in = new Scanner(System.in);
				
				////////////////
				// get information for branch data	
				String chk = "n";
				System.out.print("Do you want to update BRANCH NAME? (y/n) \n");							 
				chk = in.nextLine();
				
				chk = chk.toLowerCase();
				
				if(chk.equals("y")) {
					
					// get information for branch data							 
					System.out.print("Please input new branch information to add \n"
							+ "Please input BRANCH NAME\n");							 
					branchName = in.nextLine();
					 
				}
				
				System.out.print("Please input BRANCH ADDRESS\n");							 
				address = in.nextLine();
				
				System.out.print("Please input POSTAL CODE\n");							 
				postalCode = in.nextLine();
				
				
			}catch(Exception e)
			{
				e.getMessage();
			}
		}
				 
		//////////////////////////////
		// method : getBranchNewInfo
		//			get all new branch information to update record
		
		private static void getBranchNewInfo() {
			
			Scanner in;
			try {
				in = new Scanner(System.in);
				
				// get information for branch data							 
				System.out.print("Please input new branch information to add \n"
						+ "Please input BRANCH NAME\n");							 
				branchName = in.nextLine();
				
				System.out.print("Please input BRANCH ADDRESS\n");							 
				address = in.nextLine();
				
				System.out.print("Please input POSTAL CODE\n");							 
				postalCode = in.nextLine();
				
				
			}catch(Exception e) {
				e.getMessage();
				
			}
		}
		
		//////////////////////////////
		// method : getBooksUpdateInfo
		//			get all new information to update record
		private static void getBooksUpdateInfo() {
			 
			Scanner in;
			try {
				in = new Scanner(System.in);
				
				// get information for books data
				System.out.print("Please input AUTHOR to update\n");							 
				author = in.nextLine();
			 
				
				System.out.print("Please input PRICE to update\n");
				price = in.nextDouble();
				in.nextLine();
				
				
				System.out.print("Please input TYPE to update\n");
				type = in.nextLine();
				
			
				System.out.print("Please input SUBJECT to update\n");
				subject = in.nextLine();
			
				
			} catch(Exception e){
				e.getMessage();
			}
			
		}
		
		//////////////////////////////
		// method : getBookNewInfo
		//			get all new information to add record
		private static void getBooksNewInfo() {
			
			Scanner in;
			try {
				
				in = new Scanner(System.in);
				// get information for books data							 
				System.out.print("Please input new book information\n"
						+ "Please input TITLE\n");							 
				title = in.nextLine();
				 
				// get information for AUTHOR data
				System.out.print("Please input AUTHOR\n");							 
				author = in.nextLine();
			 
				// get information for PRICE data
				System.out.print("Please input PRICE\n");
				price = in.nextDouble();
				in.nextLine();
				
				// get information for TYPE data
				System.out.print("Please input TYPE\n");
				type = in.nextLine();
				
				// get information for SUBJECT data
				System.out.print("Please input SUBJECT\n");
				subject = in.nextLine();
				 
			   
			
			} catch(InputMismatchException ime) {
				
				System.err.println("Please type correctly!");
				
			}
		}
 

}
