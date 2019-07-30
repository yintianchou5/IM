package org.dzq.im.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class IMClient extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3036784783480847023L;
	private JTextField txtuser;
	private JPasswordField txtpass;
	private JButton button1;
	private JButton button2;

	public IMClient() {
		this.setLayout(null);
		this.setSize(430, 330);

		this.setResizable(false);// 窗口不能改变大小
		this.setLocationRelativeTo(null);// 一开始弹出窗口就在中间
		setTitle("用户登录");// 设置窗口标题

		ImageIcon icon = new ImageIcon("src/img/icon.jpg");
		this.setIconImage(icon.getImage()); // 设置窗口图标
		// this.getContentPane().setBackground(Color.CYAN);//设置背景颜色

		JLabel labuser = new JLabel();
		labuser.setText("用户名：");
		labuser.setBounds(30, 155, 60, 33);
		this.add(labuser);
		txtuser = new JTextField();
		txtuser.setBounds(134, 155, 260, 33);
		this.add(txtuser);

		JLabel labpass = new JLabel();
		labpass.setText("密码：");
		labpass.setBounds(30, 195, 60, 33);
		this.add(labpass);
		txtpass = new JPasswordField();
		txtpass.setBounds(134, 195, 260, 33);
		this.add(txtpass);

		button1 = new JButton("注册");
		button1.setBounds(58, 247, 130, 40);
		this.add(button1);

		button2 = new JButton("登录");
		button2.setBounds(237, 247, 130, 40);
		button2.addActionListener(this);// 添加到监听器
		this.add(button2);

		ImageIcon background = new ImageIcon("src/img/bgimg.jpg");
		// 把背景图片显示在一个标签里面
		JLabel label = new JLabel(background);
		// 把标签的大小位置设置为图片刚好填充整个面板
		label.setBounds(0, 0, this.getWidth(), this.getHeight());
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		JPanel imagePanel = (JPanel) this.getContentPane();
		imagePanel.setOpaque(false);
		// 把背景图片添加到分层窗格的最底层作为背景
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String user = txtuser.getText();
		String pwd = txtpass.getText();
		System.out.println(user + "---" + pwd);

		// 将网络通信的客户端直接拿过来
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		// 服务器端IP地址
		String serverIP = "127.0.0.1";
		// 服务器端端口号
		int port = 10085;
		// 发送内容
		String data = user + "=" + pwd;
		try {
			// 建立连接
			socket = new Socket(serverIP, port);
			System.out.println("客户端启动......");
			// while(true){
			// 发送数据
			os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			PrintWriter pw = new PrintWriter(osw, true);
			pw.println(data);
			// os.write(data.getBytes());

			// 接收数据
			is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
//                 byte[] b = new byte[1024];
//                 int n = is.read(b);

			String huifu = br.readLine();
			// 输出反馈数据
			// data=new String(b, 0, n);
			System.out.println("服务器反馈：" + huifu);
			if (huifu.equals("ok")) {
				JOptionPane.showMessageDialog(null, "登录成功");
				this.setVisible(false);
				IMMessage mes = new IMMessage();
				mes.setSocket(socket);
				mes.addUser(user);
			} else {
				JOptionPane.showMessageDialog(null, "登录失败");
			}
		} catch (Exception e1) {
			e1.printStackTrace(); // 打印异常信息
		}
	}

	public static void main(String[] args) {
		IMClient login = new IMClient();
	}

}
