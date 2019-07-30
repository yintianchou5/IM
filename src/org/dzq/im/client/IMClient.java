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

		this.setResizable(false);// ���ڲ��ܸı��С
		this.setLocationRelativeTo(null);// һ��ʼ�������ھ����м�
		setTitle("�û���¼");// ���ô��ڱ���

		ImageIcon icon = new ImageIcon("src/img/icon.jpg");
		this.setIconImage(icon.getImage()); // ���ô���ͼ��
		// this.getContentPane().setBackground(Color.CYAN);//���ñ�����ɫ

		JLabel labuser = new JLabel();
		labuser.setText("�û�����");
		labuser.setBounds(30, 155, 60, 33);
		this.add(labuser);
		txtuser = new JTextField();
		txtuser.setBounds(134, 155, 260, 33);
		this.add(txtuser);

		JLabel labpass = new JLabel();
		labpass.setText("���룺");
		labpass.setBounds(30, 195, 60, 33);
		this.add(labpass);
		txtpass = new JPasswordField();
		txtpass.setBounds(134, 195, 260, 33);
		this.add(txtpass);

		button1 = new JButton("ע��");
		button1.setBounds(58, 247, 130, 40);
		this.add(button1);

		button2 = new JButton("��¼");
		button2.setBounds(237, 247, 130, 40);
		button2.addActionListener(this);// ��ӵ�������
		this.add(button2);

		ImageIcon background = new ImageIcon("src/img/bgimg.jpg");
		// �ѱ���ͼƬ��ʾ��һ����ǩ����
		JLabel label = new JLabel(background);
		// �ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ�����������
		label.setBounds(0, 0, this.getWidth(), this.getHeight());
		// �����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸��
		JPanel imagePanel = (JPanel) this.getContentPane();
		imagePanel.setOpaque(false);
		// �ѱ���ͼƬ��ӵ��ֲ㴰�����ײ���Ϊ����
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String user = txtuser.getText();
		String pwd = txtpass.getText();
		System.out.println(user + "---" + pwd);

		// ������ͨ�ŵĿͻ���ֱ���ù���
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		// ��������IP��ַ
		String serverIP = "127.0.0.1";
		// �������˶˿ں�
		int port = 10085;
		// ��������
		String data = user + "=" + pwd;
		try {
			// ��������
			socket = new Socket(serverIP, port);
			System.out.println("�ͻ�������......");
			// while(true){
			// ��������
			os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			PrintWriter pw = new PrintWriter(osw, true);
			pw.println(data);
			// os.write(data.getBytes());

			// ��������
			is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
//                 byte[] b = new byte[1024];
//                 int n = is.read(b);

			String huifu = br.readLine();
			// �����������
			// data=new String(b, 0, n);
			System.out.println("������������" + huifu);
			if (huifu.equals("ok")) {
				JOptionPane.showMessageDialog(null, "��¼�ɹ�");
				this.setVisible(false);
				IMMessage mes = new IMMessage();
				mes.setSocket(socket);
				mes.addUser(user);
			} else {
				JOptionPane.showMessageDialog(null, "��¼ʧ��");
			}
		} catch (Exception e1) {
			e1.printStackTrace(); // ��ӡ�쳣��Ϣ
		}
	}

	public static void main(String[] args) {
		IMClient login = new IMClient();
	}

}
