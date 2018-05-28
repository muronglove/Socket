import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import java.sql.*;

public class Main {
private static final int PORT = 9999;// 端口监听
private List<Socket> mList = new ArrayList<Socket>();// 存放客户端socket
private ServerSocket server = null;
private ExecutorService mExecutorService = null;// 线程池

/**
  * @param args
  */
public static void main(String[] args) {
  // TODO Auto-generated method stub
  new Main();
}
public Main() {
  try {
   server = new ServerSocket(PORT);
   mExecutorService = Executors.newCachedThreadPool();// 创建一个线程池
   System.out.println("Server Start...");
   Socket client = null;
   while (true) {
    client = server.accept();
    mList.add(client);
    mExecutorService.execute(new Service(client));// 开启一个客户端线程.
   }
  } catch (Exception ex) {
   ex.printStackTrace();
  }
}
public class Service implements Runnable {
	
//线程中需要的变量，与其他线程是独立的，不共享
  private Socket socket;
  private BufferedReader in = null;
  private String msg = "";
  private JSONObject returnobj =null;//用来返回的JSONobject
  private String username = "";
  private String password = "";
  private String imeinumber = "";
  private JSONObject obj = null;//解析客户端传来的数据
  //构造函数
  public Service(Socket socket) {
   this.socket = socket;
   try {
    in = new BufferedReader(new InputStreamReader(socket
      .getInputStream()));
    msg = "user:" + this.socket.getInetAddress() + " come total:"
      + mList.size();
    returnobj = new JSONObject();
	   returnobj.put("cmd", msg);
	   ManipulateDatabase.sendmsg(socket,returnobj);
   } catch (IOException e) {
    e.printStackTrace();
   }
  }
  
  //线程的主要逻辑
  public void run() {
   // TODO Auto-generated method stub
   try {
    while (true) {
     if ((msg = in.readLine()) != null) {
    	 //将必要的数据先提取出来
    	 System.out.println(msg);
    	 obj = JSONObject.fromObject(msg);
    	 
    	 //对客户端的命令进行解析，然后返回数据
      if (obj.getString("cmd").equals("exit")) {
       System.out.println("sssssssssss");
       mList.remove(socket);
       in.close();
       msg = "user:" + socket.getInetAddress()
         + " exit total:" + mList.size();
       socket.close();
       returnobj = new JSONObject();
       returnobj.put("cmd", "loginout");
       ManipulateDatabase.sendmsg(socket,returnobj);
       break;
      } else if(obj.getString("cmd").equals("register")) {
    	  
       msg = socket.getInetAddress() + " : " + msg;
       if(ManipulateDatabase.checkIfCouldRegister(obj.getString("username"))){
    	   returnobj = new JSONObject();
    	   returnobj.put("cmd", "register failed");
    	   ManipulateDatabase.sendmsg(socket,returnobj);
       }else{
    	   ManipulateDatabase.addValueToDatabase(obj.getString("username"),obj.getString("password"));
    	   returnobj = new JSONObject();
    	   returnobj.put("cmd", "register succeeded");
    	   ManipulateDatabase.sendmsg(socket,returnobj);
       }
       
      }else if(obj.getString("cmd").equals("login")){
    	  if(ManipulateDatabase.checkIfContainsUser(obj.getString("username"),obj.getString("password"))){
    		  username = obj.getString("username");
    	    	 password = obj.getString("password");
    	    	 imeinumber = obj.getString("imei");
    		  returnobj = new JSONObject();
       	   	returnobj.put("cmd", "login succeeded");
       	 ManipulateDatabase.sendmsg(socket, returnobj);
    	  }else{
    		  returnobj = new JSONObject();
       	   		returnobj.put("cmd", "login failed");
       	   	ManipulateDatabase.sendmsg(socket, returnobj);
    	  }
      }
      //数据库同步部分代码
      //(1)服务器同步到客户端
      else if(obj.getString("cmd").equals("syncfromserver")){
//    	  try{
//    		  String str = obj.getString("image");
//    		  byte[] decode = ImgIOJsonOutputUtils.decode(str);
//
//              FileOutputStream fos = new FileOutputStream(
//                      "/Users/imacbook/Desktop/haha.jpg");
//
//              fos.write(decode);
//              fos.close();
//    	  }catch(Exception e){
//    		  e.printStackTrace();
//    	  }
    	  username = obj.getString("username");
    	  password = obj.getString("password");
    	  imeinumber = obj.getString("imeinumber");
    	  boolean flag = obj.getBoolean("flag");
    	  if(flag){
    		  ManipulateDatabase.updateDataItem("delete from imei where imeinumber = '"+imeinumber+"'");
        	  System.out.println("haha");
    	  }
    	  ManipulateDatabase.syncFromServer(username, password, imeinumber, socket);
    	  returnobj = new JSONObject();
    	  returnobj.put("cmd", "syncfromserverover");
    	  ManipulateDatabase.sendmsg(socket, returnobj);
      }
      //(2)从客户端同步到服务器
      else if(obj.getString("cmd").equals("syncfromclient")){
    	  ManipulateDatabase.syncFromClient(obj, username, password, imeinumber);	 
    	  returnobj = new JSONObject();
    	  returnobj.put("cmd", "syncfromclientover");
    	  ManipulateDatabase.sendmsg(socket, returnobj);
      }
 
//      else if(obj.getString("cmd").equals("delete")){
//    	  ManipulateDatabase.updateDataItem("delete from imei where imeinumber = '"+imeinumber+"'");
//		  
//      }
      
      
     }
    }
   } catch (Exception ex) {
    System.out.println("server 读取数据异常");
    ex.printStackTrace();
   }
  }



  
  
  
}
}