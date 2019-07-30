package org.dzq.im.client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.dzq.im.util.FileUtils;

public class IMMessage extends JFrame implements ActionListener,Runnable{
	private JTextField txtMsg;
	private JButton btnSend;
	private JTextArea txtArea;
	private JComboBox cmbUserList;
	private Socket socket;
	private String userName;
	public void setSocket(Socket socket){
		this.socket=socket;
		//创建线程对象
		Thread t=new Thread(this);
		t.start();
	}
	public void addUser(String user){
		this.userName=user;
		//cmbUserList.addItem(user);
		this.setTitle(user+"正在聊天");
	}
	public IMMessage() {
		this.setTitle("用户聊天窗口");
		this.setSize(500, 450);
		txtMsg=new JTextField();
		btnSend=new JButton("发送");
		cmbUserList=new JComboBox();
		//小面板
		JPanel px=new JPanel();
		px.setLayout(new GridLayout(1, 2));
		px.add(cmbUserList);
		px.add(btnSend);
		//大面板
		JPanel pd=new JPanel();
		pd.setLayout(new GridLayout(2, 1));
		pd.add(txtMsg);
		pd.add(px);//将小面板放到大面板
		
		txtArea=new JTextArea();
		txtArea.setFont(new Font("楷书", 0, 20));
		JScrollPane span=new JScrollPane(txtArea);

		//添加监听
		btnSend.addActionListener(this);
		
		this.setLayout(new BorderLayout());
		this.add(pd,BorderLayout.NORTH);
		this.add(span, BorderLayout.CENTER);
		
		//设置滚动条
		JScrollPane scroll = new JScrollPane(txtArea); 
		scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroll);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		//创建一个线程对象
		Thread t=new Thread(this);
		t.start();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String text=txtMsg.getText();
		if(!text.equals("")){
			String msg=userName+":"+text+"\n";
			txtArea.append(msg);
			try {
				FileUtils.writerMessage(msg);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(null, "请输入内容");
		}
		//txtArea.append(text);
		try{
			if(text!=null&&!text.equals("")){
			OutputStream os=socket.getOutputStream();
			OutputStreamWriter osw=new OutputStreamWriter(os);
			PrintWriter pw=new PrintWriter(osw,true);
			String um=(String) this.cmbUserList.getSelectedItem();
			pw.println("msg%"+um+":"+text+"\n");
			}
		}catch(Exception e1){
			e1.printStackTrace();
		}
	}
	//线程方法主体
	public void run(){
		try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader br=new BufferedReader(isr);
            
            while(true){
            String huifu=br.readLine();
            
            String[] ms=huifu.split("%");
            if(ms[0].equals("add")){
            	this.cmbUserList.addItem(ms[1]);
            }else if (ms[0].equals("msg")) {
				this.txtArea.append("\n"+ms[1]);
			}else if (ms[0].equals("exit")) {
				this.cmbUserList.removeItem(ms[1]);
			}
            }
   } catch (Exception e) {
            e.printStackTrace();
   }
		
	}

}
