/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg487a2dbsconnection;

import java.sql.SQLException;
import theloancore.*;
/**
 *
 * @author fahd_
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // TODO code application logic here
        
        LoanDB lb = new LoanDB();
        MemberDB md= new MemberDB();
       // lb.getAllLoanInfo();
        //lb.getByBookID(1);
        //lb.updateLoan(1,"cds", "dss");
        //System.out.print("dasda");
        //lb.deleteLoan(1);
       // lb.createLoan(2, 9, "DAda", "dada");
        //md.createMember("test","test", "test");
        md.AllMembers(); // this is my list 
        for( Member l : md.AllMembers()){
            System.out.print(l.print());
            
        }
        
        lb.createLoan(9, 2, "fsfs", "ddsaa");
    }
    
}
