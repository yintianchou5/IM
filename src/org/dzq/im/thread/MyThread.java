package org.dzq.im.thread;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import org.dzq.im.client.CheckUser;


public class MyThread extends Thread{
	private Socket socket;
	public void setSocket(Socket socket){
		this.socket=socket;
	}
	private Map<String, Socket> userHashMap;
	public void setMap(Map<String, Socket> userMap){
		this.userHashMap=userMap;
	}
	public void run(){
		try {
            InputStream is=socket.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader br=new BufferedReader(isr);
            
            String str=br.readLine();
            System.out.println("客户端发送内容为：" + str);
            
            Thread.sleep(1000);
            if(str!=null&&str.length()>3){
            	String[] split=str.split("=");
            	OutputStream os=socket.getOutputStream();
            	OutputStreamWriter osw=new OutputStreamWriter(os);
            	//if(split[0].equals("aaa")&&split[1].equals("111")){
            	//os = socket.getOutputStream();
            	PrintWriter pw=new PrintWriter(osw,true);
            	String userName=split[0];
            	if(CheckUser.check(userName, split[1])){
            		userHashMap.put(userName, socket);
            		pw.println("ok");
            		
            		for(String us:userHashMap.keySet()){
            			if(!us.equals(userName)){
            			Socket sall=userHashMap.get(us);
            			OutputStream osAll=sall.getOutputStream();
            			OutputStreamWriter oswAll=new OutputStreamWriter(osAll);
            			PrintWriter pwAll=new PrintWriter(oswAll,true);
            			pwAll.println("add%"+userName);
            			}
            		}
            		
            		for(String tu:userHashMap.keySet()){
            			if(!tu.equals(userName)){
            				pw.println("add%"+tu);
            			}
            		}
            //向客户端发送反馈内容
            //os.write("ok".getBytes());
            		
            		while (true) {
            			Thread.sleep(1000);
						String msg=br.readLine();
						if(msg!=null){
							System.out.println(msg);
							String[] ms=msg.split("%");
							if(ms[0].equals("msg")){
								String[] um=ms[1].split(":");
								String to=um[0];
								String mess=um[1];
										Socket ts=userHashMap.get(to);
										OutputStream tos=ts.getOutputStream();
				            			OutputStreamWriter tosw=new OutputStreamWriter(tos);
				            			PrintWriter pw1=new PrintWriter(tosw,true);
				            			pw1.println("msg%"+userName+":"+mess);
							}else if (ms[0].equals("exit")) {
								userHashMap.remove(ms[1]);
								for(String us:userHashMap.keySet()){
									Socket s=userHashMap.get(us);
									OutputStream os1=s.getOutputStream();
			            			OutputStreamWriter osw1=new OutputStreamWriter(os1);
			            			PrintWriter pw1=new PrintWriter(osw1,true);
			            			pw1.println("exit%"+ms[1]);
								}
							}
						}
						
					}
            	}else{
            		pw.println("err username or password");
            	}
            }
   } catch (Exception e) {
            e.printStackTrace();
   }
	}

}

