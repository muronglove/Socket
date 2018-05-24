import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.Blob;

import net.sf.json.JSONObject;

public class ManipulateDatabase {
	  public static void sendmsg(Socket mSocket,JSONObject obj) {
		   System.out.println(obj.toString());
		    PrintWriter pout = null;
		    try {
		     pout = new PrintWriter(new BufferedWriter(
		       new OutputStreamWriter(mSocket.getOutputStream())),
		       true);
		     pout.println(obj.toString());
		    } catch (IOException e) {
		     e.printStackTrace();
		    }
		  }
		  
		  //添加用户信息
		  public static synchronized void addValueToDatabase(String username, String password)throws Exception{
			  Class.forName("com.mysql.jdbc.Driver");
			  String url = "jdbc:mysql://localhost:3306/androidstorage?useUnicode=true&characterEncoding=utf-8";
		      Connection conn = DriverManager.getConnection(url, "root", "jiqing");
		      Statement stat = conn.createStatement();
		    
		      ResultSet result = stat.executeQuery("select * from user");
		      
		      stat.executeUpdate("insert into user(username,password) values('"+username+"','"+password+"')");
		    //查询数据
		      result = stat.executeQuery("select * from user");
		      while (result.next())
		      {
		          System.out.println(result.getString("username")+ " " + result.getString("password"));
		      }

		      //关闭数据库
		      result.close();
		      stat.close();
		      conn.close();
		  }
		  
		  
		  //查询是否存在该用户
		  public static synchronized boolean checkIfContainsUser(String username, String password)throws Exception{
			  Class.forName("com.mysql.jdbc.Driver");
			  String url = "jdbc:mysql://localhost:3306/androidstorage?useUnicode=true&characterEncoding=utf-8";
		      Connection conn = DriverManager.getConnection(url, "root", "jiqing");
		      Statement stat = conn.createStatement();
		    
		      int id =0;
		      ResultSet result = stat.executeQuery("select * from user where username='"+username+"'and password='"+password+"'");
		      while(result.next()){
		    	  id++;
		      }
		      if(id!=0)
		    	  return true;
		      return false;
		  }
		  
		  public static synchronized List<JSONObject> searchDataItem(String sql)throws Exception{
			  Class.forName("com.mysql.jdbc.Driver");
			  String url = "jdbc:mysql://localhost:3306/androidstorage?useUnicode=true&characterEncoding=utf-8";
		      Connection conn = DriverManager.getConnection(url, "root", "jiqing");
		      Statement stat = conn.createStatement();
		     
		      
		      List <JSONObject> list = new ArrayList<>();
		      ResultSet result = stat.executeQuery(sql);
		      while(result.next()){
		    	  DataItem item = new DataItem();
		    	  
		    	  try{
		    		  if(result.findColumn("userid")>0){
		    			  item.setUserid(result.getInt("userid"));
		    		  }
		    	  }catch(SQLException e){
		    		  
		    	  }
		    	  
		    	  try{
		    		  if(result.findColumn("username")>0){
			    		  item.setUsername(result.getString("username"));
			    	  }
		    	  }catch(SQLException e){
		    		  
		    	  }
		    	  
		    	  try{
		    		  if(result.findColumn("password")>0){
			    		  item.setPassword(result.getString("password"));
			    	  }
		    	  }catch(SQLException e){
		    		  
		    	  }
		    	  
		    	  try{
		    		  if(result.findColumn("uuid")>0){
			    		  item.setUuid(result.getString("uuid"));
			    	  }
		    	  }catch(SQLException e){
		    		  
		    	  }
		    	  
		    	  try{
		    		  if(result.findColumn("caption")>0){
			    		  item.setCaption(result.getString("caption"));
			    	  }
		    	  }catch(SQLException e){
		    		  
		    	  }
		    	  
		    	  try{
		    		  if(result.findColumn("label")>0){
			    		  item.setLabel(result.getString("label"));
			    	  }
		    	  }catch(SQLException e){
		    		  
		    	  }
		    	  
		    	  try{
		    		  if(result.findColumn("number")>0){
			    		  item.setNumber(result.getInt("number"));
			    	  }
		    	  }catch(SQLException e){
		    		  
		    	  }
		    	  
		    	  try{
		    		  if(result.findColumn("color")>0){
			    		  item.setColor(result.getString("color"));
			    	  }
		    	  }catch(SQLException e){
		    		  
		    	  }
		    	  
		    	  try{
		    		  if(result.findColumn("position")>0){
			    		  item.setPosition(result.getString("position"));
			    	  }
		    	  }catch(SQLException e){
		    		  
		    	  }
		    	  
		    	  try{
		    		  if(result.findColumn("date")>0){
			    		  item.setDate(result.getString("date"));
			    	  }
		    	  }catch(SQLException e){
		    		  
		    	  }
		    	  
		    	  try{
		    		  if(result.findColumn("image")>0){
			    		  item.setImage(result.getString("image"));
			    		  
			    	  }
		    	  }catch(SQLException e){
		    		  
		    	  }
		    	  
		    	  try{
		    		  if(result.findColumn("imeinumber")>0){
			    		  item.setImeinumber(result.getString("imeinumber"));
			    	  }
		    	  }catch(SQLException e){
		    		  
		    	  }
		    	  
		    	  try{
		    		  if(result.findColumn("a")>0){
			    		  item.setA(result.getInt("a"));
			    	  }
		    	  }catch(SQLException e){
		    		  
		    	  }
		    	  
		    	  
		    	  
		    	  
		    	  
		    	  
		    	 JSONObject jsonObject = JSONObject.fromObject(item);
		    	 System.out.println("searchItem:"+item.toString());
		    	 list.add(jsonObject);
		      }
		      
			  return list;
		  }
		  
		  public static synchronized boolean updateDataItem(String sql)throws Exception{
			  Class.forName("com.mysql.jdbc.Driver");
			  String url = "jdbc:mysql://localhost:3306/androidstorage?useUnicode=true&characterEncoding=utf-8";
		      Connection conn = DriverManager.getConnection(url, "root", "jiqing");
		      Statement stat = conn.createStatement();
		      
		      stat.executeUpdate(sql);
		      
			  return true;
		  }
		  
		  public static synchronized boolean syncFromServer(String username,String password,String imeinumber,Socket socket)throws Exception{
			  List<JSONObject> list = null;
	    	  //查看data表是否存在该用户之前的记录
			  //获取这个用户的数据集
			  
	    	  list = ManipulateDatabase.searchDataItem("select * from user natural join data where username = '"+username+"' and password = '"+password+"'");
	    	  if(list.size()!=0){
	    		  //查看imei表中是否有该用户在该手机上同步的记录
	    		  int userid = list.get(0).getInt("userid");
	    		  list = ManipulateDatabase.searchDataItem("select * from imei natural join data where userid = '"+userid+"' and imeinumber = '"+imeinumber+"'"); 
	    		  //如果存在该账号在该手机上的同步记录，就发送相应数据到客户端
	    		  if(list.size()!=0){
	    			  for(int i=0;i<list.size();i++){
	    				  //System.out.println(list.toString());
	    				  JSONObject tempObject = list.get(i);
	    				  if(tempObject.getInt("a")<8){
	    					  //对imei表进行修改
	    					  String uuid = tempObject.getString("uuid");
	    					  if(tempObject.getInt("a")==1){
	    						  ManipulateDatabase.updateDataItem("update imei set a = 8 where imeinumber = '"+imeinumber+"' and uuid = '"+uuid+"'");
	    					  }else{
	    						  ManipulateDatabase.updateDataItem("update imei set a = 9 where imeinumber = '"+imeinumber+"' and uuid = '"+uuid+"'");
	    					  }
	    					  tempObject.put("cmd", "syncfromserver");
	    					  ManipulateDatabase.sendmsg(socket, tempObject);
	    					  //System.out.println(tempObject.toString());
	    				  }
	    			  }
	    		  }else{
	    			  //查看是否存在imei表项为main的记录
	    			  list = ManipulateDatabase.searchDataItem("select * from imei natural join data where userid = '"+userid+"' and imeinumber = 'main'"); 
	    			  if(list.size()!=0){
	    				  for(int i=0;i<list.size();i++){
	    					  //复制imei="main"的记录，并将imei更改为该手机对应的imei号，然后再次进行同步
	    					  String uuid = list.get(i).getString("uuid");
	        				  int a = list.get(i).getInt("a");
	        				  ManipulateDatabase.updateDataItem("insert into imei values('"+uuid+"','"+imeinumber+"',"+a+")");
	        				  //System.out.println("复制imei='main'的记录:"+list.get(i).getString("userid")+uuid);
	    				  }
	    				  syncFromServer(username,password,imeinumber,socket);
	    			  }//因为data表中一有更新，imei表中的imei="main"的表项就会同步更新，因此在这里不必考虑data表中有数据而imei表中imei="main"的项没有数据的情况
	    		  }
	    	  }
	    	  return true;
	    	  
		  }
		  
		  
		  public static void syncFromClient(JSONObject obj,String username,String password,String imeinumber)throws Exception{
			 String uuid;
			 try{ uuid = obj.getString("uuid"); }catch(Exception e){return;}
			

	    	  List<JSONObject> imeiList = ManipulateDatabase.searchDataItem("select distinct imeinumber from imei");
	    	  boolean flag = false;
	    	  //获取所有的imei
	    	  for(int i=0;i<imeiList.size();i++){
	    		  JSONObject tempobj = imeiList.get(i);
	    		  if(tempobj.getString("imeinumber")=="main")
	    			  flag = true;
	    		 
	    	  }
	    	  
	    	  //如果没有imei为main的记录,就添加一条，防止出现没有主记录的情况
	    	  if(!flag){
	    		  JSONObject tempobj = new JSONObject();
	    		  tempobj.put("imeinumber", "main");
	    		  imeiList.add(tempobj);
	    	  }
	    	  	String caption = obj.getString("caption");
	            String label = obj.getString("label");
	            int number = obj.getInt("number");
	            String color = obj.getString("color");
	            String position = obj.getString("position");
	            String date = obj.getString("date");
	            String image = obj.getString("image");
	     	  if(obj.getInt("a")==1){
	    		  //删除	  
	     		  for(int i=0;i<imeiList.size();i++){
	     			 String tempimeinumber = imeiList.get(i).getString("imeinumber");
	     			 ManipulateDatabase.updateDataItem("update imei set a = 1 where uuid = '"+uuid+"' and imeinumber = '"+tempimeinumber+"'");
	     		  }
	    		  ManipulateDatabase.updateDataItem("update imei set a = 8 where uuid = '"+uuid+"' and imeinumber = '"+imeinumber+"'");
	    	  }else if(obj.getInt("a")==2){
				  //增加
	    		  List<JSONObject> list = ManipulateDatabase.searchDataItem("select * from user where username = '"+username+"' and password = '"+password+"'");
	    		  System.out.println(username+password);
	    		  int userid = list.get(0).getInt("userid");
	    		  //String details = obj.getString("details");
	    		  //更新data表
	    		  ManipulateDatabase.updateDataItem("insert into data values("+userid+",'"+uuid+"','"+caption+"','"+label+"',"+number+",'"+color+"','"+position+"','"+date+"','"+image+"')");
	    		  //更新imei表
	    		  ManipulateDatabase.updateDataItem("insert into imei values('"+uuid+"','main',2)");
	    		  ManipulateDatabase.updateDataItem("insert into imei values('"+uuid+"','"+imeinumber+"',9)");
	    		  for(int i=0;i<imeiList.size();i++){
	      			 String tempimeinumber = imeiList.get(i).getString("imeinumber");
	      			 if(!tempimeinumber.equals("main")&&!tempimeinumber.equals(imeinumber)){
	      				ManipulateDatabase.updateDataItem("insert into imei values('"+uuid+"','"+tempimeinumber+"',2)");
	      			 }
	      		  }
	    		  ManipulateDatabase.updateDataItem("update imei set a = 9 where uuid = '"+uuid+"' and imeinumber = '"+imeinumber+"'");
	    		  
	    	  }else{
	    		  //修改
	    		  List<JSONObject> list = ManipulateDatabase.searchDataItem("select * from user where username = '"+username+"' and password = '"+password+"'");
	    		  int userid = list.get(0).getInt("userid");
	    		  uuid = obj.getString("uuid");
	    		  //String details = obj.getString("details");
	    		  //更新data表
	    		  ManipulateDatabase.updateDataItem("update data values("+userid+",'"+uuid+"','"+caption+"','"+label+"',"+number+",'"+color+"','"+position+"','"+date+"','"+image+"')");
	    		//更新imei表
	    		  for(int i=0;i<imeiList.size();i++){
	      			 String tempimeinumber = imeiList.get(i).getString("imeinumber");
	      			 ManipulateDatabase.updateDataItem("update imei set a = 3 where uuid = '"+uuid+"' and imeinumber = '"+tempimeinumber+"'");
	      		  }
	    		  ManipulateDatabase.updateDataItem("update imei set a = 9 where uuid = '"+uuid+"' and imeinumber = '"+imeinumber+"'");
	    		  
	    	  }
		  }
		  
		  
		  
		  
		  
		  
}
