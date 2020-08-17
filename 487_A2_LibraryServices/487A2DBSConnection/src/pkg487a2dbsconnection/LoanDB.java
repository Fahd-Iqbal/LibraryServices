/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg487a2dbsconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import theloancore.*;

/**
 *
 * @author fahd_
 */
public class LoanDB {
    
    
    private  static Connection conn = null; // changed from public static
    // private static BookRepository repo = null;

    private String user = "root";
    private String pass = "password";
    private String host = "jdbc:mysql://localhost:3306/sys?autoReconnect=true&useSSL=false";

    //****************************************************************************************************
    //****************************************************************************************************
    public LoanDB() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection(host, user, pass);
        
    }
    
    public List<Loan> getAllLoanInfo() throws SQLException{
        List<Loan> arr= new ArrayList();
        try(Statement stmt =conn.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM loan");
            while(rs.next()){
               int bookID  = rs.getInt("bookId");
                int memberID = rs.getInt("memberID");
                String takeOutDate= rs.getString("takeOutDate");
                String returnDate = rs.getString("returnDate");
                
                Loan myLoan = new Loan(bookID, memberID, takeOutDate, returnDate);
                arr.add(myLoan);
                //System.out.print(bookID+" "+ memberID+" "+ takeOutDate+" "+ returnDate+"::::: \n");
            }
            for(Loan l: arr){
                l.print();
            }
            return arr;
            
            }

        
        }
    
     public String createLoan(int id,int member,String takeoutDate,String returndate) throws SQLException{
        //if memeber exists in the member DB then let the person create a loan.
       List<Integer> arr = new ArrayList<Integer>();
         
        try(Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT memberID FROM members");
            while(rs.next()){
                int memberID=rs.getInt("memberID");
                arr.add(memberID);
            }
            if(arr.contains(member)){
                //"UPDATE book SET title=?, descriptions=?, isbn=?, author_first_name=?, author_last_name=?, publisher_company=?, publisher_address=? WHERE id=?"
                try(PreparedStatement tmt =conn.prepareStatement("INSERT INTO loan (bookId, memberID, takeOutDate, returnDate) Values(?,?,?,?)")){ // should be an insert 
                    tmt.setInt(1, id);
                    tmt.setInt(2, member);
                    tmt.setString(3, takeoutDate);
                    tmt.setString(4, returndate);
                    
                    
                    tmt.execute();
                }
                System.out.println("done");
             return "done";   
            }
            System.out.println("Member does not exist");
            return "Member does not exist";
        }
        catch(Exception e)
        {
            return "this book is already out";
        }
        
        
    }
        
        
    public Loan getLoanByBookID(int id) throws SQLException{
        Loan loan=null;
        try(PreparedStatement stmt =conn.prepareStatement("SELECT * FROM loan WHERE bookId=? ")){
           stmt.setInt(1, id); // this will put the id in the ? and then will generate 
          
// the query. The next step is to populate the obj with the data
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    int id2= rs.getInt("bookId");
                    int memberID= rs.getInt("memberID");
                    String takeOutDate= rs.getString("takeOutDate");
                    String returnDate=rs.getString("returnDate");
                   // System.out.println(rs.getInt("bookId") +" "+ rs.getInt("memberID")+" " + rs.getString("takeOutDate")+" "+ rs.getString("returnDate") );
                    loan = new Loan(id2,memberID,takeOutDate,returnDate);
                }
                return loan;
            }
        }
        
    }
    
   
    
    public boolean deleteLoan(int id) throws SQLException{
        
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM loan WHERE bookId=?")){
          stmt.setInt(1,id);
          stmt.executeUpdate();
          return true;
        }
        
    }
    
    public boolean deleteAll() throws SQLException{
        try(Statement stmt= conn.createStatement()){
            stmt.executeUpdate("DELETE FROM loan");
            return true;
        }
    }
    
    
    // "UPDATE book SET title=?, descriptions=?, isbn=?, author_first_name=?, author_last_name=?, publisher_company=?, publisher_address=? WHERE id=?"
    public boolean updateLoan(int id, String takeOutDate, String returnDate) throws SQLException{
        try(PreparedStatement stmt = conn.prepareStatement("UPDATE loan SET takeOutDate=?, returnDate=? WHERE bookId=?" )){
            stmt.setString(1, takeOutDate);
            stmt.setString(2, returnDate);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            return true;
        }
    }
    
    
    
    
        
    } 
      

