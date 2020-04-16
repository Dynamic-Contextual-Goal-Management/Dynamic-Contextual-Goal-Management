package ericsson;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
class Updater
{
	static Connection con=null;
	static Statement stmt=null;
	static ResultSet rs=null;
	public static ArrayList<goalContext>goalContextList;
	public void readCsv(String path)
	{
		goalContextList=new ArrayList<goalContext>();
		
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(path));
			String line=br.readLine();
			while(line!=null)
			{
				String goalname="none",context="none";
				StringTokenizer st=new StringTokenizer(line);
				while(st.hasMoreTokens())
				{
					String token=st.nextToken();
					if(token.equalsIgnoreCase("Goal:"))
					{
						goalname=st.nextToken();
					}
					if(token.equalsIgnoreCase("Context:"))
					{
						context=st.nextToken();
					}
				}
				if(goalname.equalsIgnoreCase("none")||context.equalsIgnoreCase("none"))
				{
					
				}
				else
				{
					goalContext gc=new goalContext();
					gc.setGoalname(goalname);
					gc.setContext(context);
					goalContextList.add(gc);
				}
				
				
				line=br.readLine();
			}
			for(goalContext gc:goalContextList)
			{
				System.out.println("Goal: "+gc.getGoalname()+" Context: "+gc.getContext());
			}
		}
		catch(Exception e)
		{
			System.err.println(e.toString());
		}
		DbUpdate(goalContextList);
	}
	
	public void DbUpdate(ArrayList<goalContext>goalcontextlist)
	{
		try
		{
			int firstrow=-1;
			String drivername="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(drivername);
			String db="jdbc:sqlserver://localhost:1433;user=sa;password=cmsa019;databaseName=EricssonResearch";
			con=DriverManager.getConnection(db);
			
	        stmt=con.createStatement();
	        rs=stmt.executeQuery("select MAX(nodeid) from tblGoalLattice");
	        while(rs.next())
	        {
	        	firstrow=rs.getInt(1);
	        	
	        }
	        System.out.println(firstrow);
	        firstrow++;
	        for(goalContext gc:goalcontextlist)
	        {
	        	stmt=con.createStatement();
	        	String goalname=gc.getGoalname();
	        	String context="none";
	        	 rs=stmt.executeQuery("select context from tblGoalLattice where goal='"+goalname+"' and nodeid="+firstrow);
	        	 while(rs.next())
	        	 {
	        		 context=rs.getString(1);
	        		 System.out.println(rs.getString(1));
	        	 }
	        	 if(context.equalsIgnoreCase("none"))
	        	 {
	        		 System.out.println("new goal");
	        		 stmt.executeUpdate("insert into tblGoalLattice(nodeid,goal,context)values("+firstrow+",'"+goalname+"','"+gc.getContext()+"')");
	        		 
	        	 }
	        	 else if(!context.equalsIgnoreCase("none"))
	        	 {
	        		 if(context.indexOf(gc.getContext())==-1)
		        	 {
	        			 context=context+","+gc.getContext();
	        			 //update
	        			 stmt.executeUpdate("update tblGoalLattice set context='"+context+"' where goal='"+goalname+"' and nodeid="+firstrow);
		        		 System.out.println("Context not present for goal "+gc.getGoalname());
		        	 }
		        	 else if(context.indexOf(gc.getContext())!=-1)
		        	 {
		        		 
		        		 System.out.println("Context present for goal "+gc.getGoalname());
		        	 }
	        		 
	        	 }
	        	
	        	 
	        }
	        
	       
	        con.close();
	        
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}

public class GoalLatticeUpdater {

	public static void main(String[] args) {
		Updater up=new Updater();
		File folder = new File("E:\\workzone\\EricssonResearch\\Dataset1");

		String[] files = folder.list();

		for (String file : files)
		{
			up.readCsv("E:\\workzone\\EricssonResearch\\Dataset1\\"+file);
		}
		//up.readCsv("E:\\workzone\\EricssonResearch\\Dataset1\\ds2.txt");
		

	}

}
