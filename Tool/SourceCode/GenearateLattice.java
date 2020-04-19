package ericsson;

import java.sql.*;
import java.util.*;

class ContextExtraction
{
	static Connection con=null;
	static Statement stmt=null;
	static ResultSet rs=null;
	public static ArrayList<Integer>nodeid;
	public ArrayList<String>contextList;
	public ArrayList<NodeToNodeConnection>latticeList;
	public static HashMap<Integer,ArrayList<String>>nodeContextMap;
	public static HashMap<Integer,ArrayList<String>>finalnodeContextMap;
	public void extractContext()
	{
		ArrayList<NodeContext>tempContext=new ArrayList<NodeContext>();
		ArrayList<NodeContext>nodeContextList=new ArrayList<NodeContext>();
		latticeList=new ArrayList<NodeToNodeConnection>();
		try
		{
			nodeid=new ArrayList<Integer>();
			contextList=new ArrayList<String>();
			nodeContextMap=new HashMap<Integer, ArrayList<String>>();
			finalnodeContextMap=new HashMap<Integer, ArrayList<String>>();
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
	        for(int i:nodeid)
	        {
	        	
	        	stmt=con.createStatement();
		        rs=stmt.executeQuery("select context from tblGoalLattice where nodeid="+i);
		        while(rs.next())
		        {
		        	NodeContext nc=new NodeContext();
		        	nc.setContext(rs.getString(1));
		        	nc.setNodeid(i);
		        	tempContext.add(nc);
		        }
	        }
	        for(NodeContext nc:tempContext)
	        {
	        	//System.out.println(nc.getNodeid()+" "+nc.getContext());
	        	String context=nc.getContext();
	        	int nodeid=nc.getNodeid();
	        	if(context.contains(","))
	        	{
	        		StringTokenizer st=new StringTokenizer(context,",");
	        		while(st.hasMoreTokens())
	        		{
	        			//System.out.println(nodeid+" "+st.nextToken());
	        			NodeContext nco=new NodeContext();
	        			nco.setContext(st.nextToken());
	        			nco.setNodeid(nodeid);
	        			nodeContextList.add(nco);
	        		}
	        	}
	        	else
	        	{
	        		NodeContext nco=new NodeContext();
        			nco.setContext(context);
        			nco.setNodeid(nodeid);
        			nodeContextList.add(nco);
	        	}
	        }
	        for(int i:nodeid)
	        {
	        	ArrayList<String>contextsList=new ArrayList<String>();
	        	for(NodeContext nc:nodeContextList)
	        	{
	        		if(nc.getNodeid()==i)
	        		{
	        			contextsList.add(nc.getContext());
	        		}
	        	}
	        	nodeContextMap.put(i,contextsList);
	        }
	        for (Map.Entry<Integer,ArrayList<String>> entry : nodeContextMap.entrySet())  
	        {
	        	int firstI=entry.getKey();
	        	//System.out.println("Contexts for "+entry.getKey()+" is given below:");
	        	ArrayList<String> cont=entry.getValue();
	        	cont=makeUnique(cont);
	        	System.out.println("Node "+entry.getKey()+" has size "+cont.size());
	        	finalnodeContextMap.put(firstI, cont);
	        }
	        for (Map.Entry<Integer,ArrayList<String>> entry : finalnodeContextMap.entrySet())  
	        {
	        	int firstI=entry.getKey();
	        	ArrayList<String> cont=entry.getValue();
	        	//System.out.println("Size of node "+firstI+" is "+cont.size());
	        	for (Map.Entry<Integer,ArrayList<String>> entry2 : finalnodeContextMap.entrySet())  
		        {
	        		
	        		if(firstI!=entry2.getKey())
	        		{
	        			ArrayList<String> othercontext=entry2.getValue();
	        			//System.out.println("Size of other node "+entry2.getKey()+" is "+othercontext.size());
	        			if(othercontext.size()==cont.size())
	        			{
	        				int size=cont.size();
	        				
	        				//System.out.println("FirstI is " +firstI+" Size is "+size+" Second node "+entry2.getKey());
	        				int match=0;
	        				for(String c:cont)
	        				{
	        					if(othercontext.contains(c))
	        					{
	        						match++;
	        					}
	        				}
	        				if(match==size)
	        				{
	        					System.out.println(firstI+" and "+entry2.getKey()+" are same set");
	        				}
	        			}
	        			else
	        			{
	        				int size=cont.size();
	        				int match=0;
	        				for(String c:cont)
	        				{
	        					if(othercontext.contains(c))
	        					{
	        						match++;
	        					}
	        				}
	        				if(size==match)
	        				{
	        					System.out.println(firstI+" is the subset of "+entry2.getKey());
	        					NodeToNodeConnection nnc=new NodeToNodeConnection();
	        					nnc.setNode1(firstI);
	        					nnc.setNode2(entry2.getKey());
	        					latticeList.add(nnc);
	        				}
	        			}
	        		}
		        }
	        	
	        }
		}
		catch(Exception e)
		{
			System.err.println(e.toString());
		}
		initLattice(finalnodeContextMap);
		updateLattice(latticeList);
	}
	private void initLattice(HashMap<Integer, ArrayList<String>> finalnodeContextMap2) {
		try
		{
			String drivername="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(drivername);
			String db="jdbc:sqlserver://localhost:1433;user=sa;password=cmsa019;databaseName=EricssonResearch";
			con=DriverManager.getConnection(db);
			
	        stmt=con.createStatement();
	        for (Map.Entry<Integer,ArrayList<String>> entry : finalnodeContextMap2.entrySet())  
	        {
	        	String node2=String.valueOf(entry.getKey());
	        	stmt.executeUpdate("insert into tblLattice(node1,node2)values('Phi','"+node2+"')");
	        }
	        stmt.close();
	        con.close();
	        
		}
		catch(Exception e)
		{
			System.out.println("Latitce Initialization already completed "+e.toString());
		}
		
	}
	private ArrayList<String> makeUnique(ArrayList<String> cont) {
		System.out.println("Making Unique......");
		ArrayList<String>uniqueList=new ArrayList<String>();
		for(String s:cont)
		{
			if(uniqueList.contains(s))
			{}
			else
			{
				uniqueList.add(s);
			}
		}
		for(String s:uniqueList)
		{
			System.out.println(s);
		}
		return uniqueList;
	}
	public void updateLattice(ArrayList<NodeToNodeConnection>nodeslist)
	{
		try
		{
			String drivername="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(drivername);
			String db="jdbc:sqlserver://localhost:1433;user=sa;password=cmsa019;databaseName=EricssonResearch";
			con=DriverManager.getConnection(db);
			
	        stmt=con.createStatement();
	        for(NodeToNodeConnection nnc:nodeslist)
	        {
	        	String node1=String.valueOf(nnc.getNode1());
	        	String node2=String.valueOf(nnc.getNode2());
	        	
	        	stmt.executeUpdate("insert into tblLattice(node1,node2)values('"+node1+"','"+node2+"')");
	        }
	        stmt.close();
	        con.close();
		}
		catch(Exception e)
		{
			System.out.println("lattice Connection already establishedo2: "+e.toString());
		}
	}
}
public class GenearateLattice {
	
	public static void main(String[] args) {
		
		ContextExtraction ce=new ContextExtraction();
		ce.extractContext();
	}

}
