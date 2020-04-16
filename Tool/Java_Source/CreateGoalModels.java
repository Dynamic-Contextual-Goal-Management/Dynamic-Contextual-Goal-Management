package ericsson;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;
class GoalModelCreation
{
	static Connection con=null;
	static Statement stmt=null;
	static ResultSet rs=null;
	public static ArrayList<Integer>nodeid;
	public static ArrayList<goalContext>GoalContextList;
	public void createModel()
	{
		try
		{
			nodeid=new ArrayList<Integer>();
			String drivername="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(drivername);
			String db="jdbc:sqlserver://localhost:1433;user=sa;password=cmsa019;databaseName=EricssonResearch";
			con=DriverManager.getConnection(db);
			
	        stmt=con.createStatement();
	        rs=stmt.executeQuery("select distinct nodeid from tblGoalLattice");
	        while(rs.next())
	        {
	        	nodeid.add(rs.getInt(1));
	        	
	        }
	        stmt.close();
	        rs.close();
	        String goalmodelname= JOptionPane.showInputDialog("What is the Name of Your Goal Model? ");
	        for(int i:nodeid)
	        {
	        	GoalContextList=new ArrayList<goalContext>();
	        	stmt=con.createStatement();
		        rs=stmt.executeQuery("select goal,context from tblGoalLattice where nodeid="+i);
		        while(rs.next())
		        {
		        	goalContext gc=new goalContext();
		        	gc.setContext(rs.getString(2));
		        	gc.setGoalname(rs.getString(1));
		        	GoalContextList.add(gc);
		        	System.out.println(gc.getGoalname()+" "+gc.getContext());
		        }
		        new File("C:\\"+goalmodelname).mkdir();
		        new File("C:\\goalmodelname").mkdir();
		        FileWriter ffw=new FileWriter("C:/goalmodelname/goalmodelname.txt");
		        ffw.write(goalmodelname);
		        ffw.close();
		        PrintStream ps=new PrintStream(new FileOutputStream("C:\\"+goalmodelname+"\\"+goalmodelname+i+".ndsl"));
		        ps.print("grl "+goalmodelname+i+"{ \r\n actor a{\r\n ");
		        for(goalContext gc:GoalContextList)
		        {
		        	ps.print("\r\n \tgoal "+gc.getGoalname()+"{\r\n \tcontext='"+gc.getContext()+"';\r\n\t}");
		        	
		        }
		        ps.print("\r\n\t}\r\n}");
		        ps.close();
		        rs.close();
		        stmt.close();
	        }
	       
	        
	        
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			
		}
	}
}

public class CreateGoalModels {

	public static void main(String[] args) {
		GoalModelCreation gmc=new GoalModelCreation();
		gmc.createModel();

	}

}
