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
public class MemberDB {
    
    private  static Connection conn = null; // changed from public static
    // private static BookRepository repo = null;

    private String user = "root";
    private String pass = "password";
    private String host = "jdbc:mysql://localhost:3306/sys?autoReconnect=true&useSSL=false";

    //****************************************************************************************************
    //****************************************************************************************************
    public MemberDB() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection(host, user, pass);
        
    }
    
    public List<Member> AllMembers() throws SQLException{
        
        // want to read the data from sql table and store them in an list of objects
       // i want to return these so i can display it after 
        
        List<Member> arr = new ArrayList();
        try(Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM members");
            while(rs.next()){
                int memberId= rs.getInt("memberID");
                String firstName=rs.getString("first_name");
                String lastName=rs.getString("last_name");
                String contacts = rs.getString("contacts");
                Member m= new Member(memberId,firstName,lastName,contacts);
                arr.add(m);
                
            }
            return arr;
        }
        
    }
      
    
    public Member getMember(int id) throws SQLException{
        Member m=null;
        try(PreparedStatement stmt =conn.prepareStatement("SELECT * FROM members WHERE memberID=?")){
            stmt.setInt(1, id);
            // above code 
            //first have to create query to get the information of memeber 
            // code coming up 
            // will be reading the code and placing it in an object to be returned. 
            try(ResultSet set = stmt.executeQuery();){
                while(set.next()){
                    int memid= set.getInt("memberID");
                    String fname= set.getString("first_name");
                    String lname= set.getString("last_name");
                    String cInfo= set.getString("contacts");
                    m=new Member(memid,fname,lname,cInfo);
                    
                }
                return m;
            }
            
        }
        
    }
    
    public boolean deleteMember(int memberId) throws SQLException{
        try(PreparedStatement st=conn.prepareStatement("DELETE FROM members WHERE memberID=?")){
            st.setInt(1, memberId);
            st.executeUpdate();
            return true;
        }
        
    }
    
    public boolean deleteAllMembers() throws SQLException{
        try(Statement st =conn.createStatement()){
            st.executeUpdate("DELETE FROM Members");
            return true;
            
        }
    }
 
    public boolean createMember (String fname, String lname, String con) throws SQLException{
        try(PreparedStatement st = conn.prepareStatement("INSERT INTO members (first_name,last_name,contacts) VALUES(?,?,?)")){
            st.setString(1, fname);
            st.setString(2,lname);
            st.setString(3, con);
            st.executeUpdate();
           return true; 
        }
    }

    public boolean updateMember (int id,Member m) throws SQLException{
        
         try(PreparedStatement st = conn.prepareStatement("UPDATE members SET first_name=?, last_name=?, contacts=? WHERE memberID=?")){
            st.setString(1, m.getFirst_name());
            st.setString(2, m.getLast_name());
            st.setString(3, m.getContact());
            st.setInt(4, id);
            st.executeUpdate();
            return true;
             
             
         }
        
    }
    
    
    
    
    
//    public Member updateTheMember(int id, Member m) throws SQLException{
//        try(PreparedStatement st = conn.prepareStatement("UPDATE members SET first_name=?, last_name=?, contacts=? WHERE id=?")){
//            st.setString(1, m.getFirst_name());
//            st.setString(2, m.getLast_name());
//            st.setString(3, m.getContact());
//            st.setInt(4, id);
//            st.executeUpdate();
//            
//        }
//    }

}


