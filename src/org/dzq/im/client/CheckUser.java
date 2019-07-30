package org.dzq.im.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckUser {
	public static boolean check(String user,String pwd) throws Exception{
		boolean flag=false;
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://127.0.0.1:3306/im?characterEncoding=UTF-8";
		String us="root";
		String password="123456";
		Connection conn=DriverManager.getConnection(url,us,password);
		String sql="select * from users"+
					" where userName='"+user
					+"' and userPwd='"+pwd+"'";
		System.out.println(sql);
		Statement st=conn.createStatement();
		ResultSet rs=st.executeQuery(sql);
		if(rs.next()){
			flag=true;
		}
		rs.close();
		st.close();
		conn.close();
		
		return flag;
	}

}
