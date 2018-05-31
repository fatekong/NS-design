package Cpackage;

import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.EmptyBorder;

public class SW extends JFrame {

	String IP = "";
	String UN = "";
	String IO = "";
	JFrame f = new JFrame("聊天室");
	JButton button1 = new JButton("连接");
	JButton button2 = new JButton("发送");
	JButton button3 = new JButton("断开");
	final JTextArea ta2 = new JTextArea();
	final JTextArea ta3 = new JTextArea();
	final JTextArea ta4 = new JTextArea();
	final JTextArea ta5 = new JTextArea();
	
	JScrollPane jsp3 = new JScrollPane(ta3);
	JScrollPane jsp4 = new JScrollPane(ta4);
	JScrollPane jsp5 = new JScrollPane(ta5);
	final JLabel label1 = new JLabel("服务器：");
	final JLabel label2 = new JLabel("用户名：");
	final JLabel label3 = new JLabel("在线用户");
	final JLabel label4 = new JLabel("信息显示");
	final JLabel label6 = new JLabel("IP：");
	final JLabel label5 = new JLabel("************************************************信息发送区******************************************************************");
	Container contentPane = f.getContentPane();
	JComboBox comboBox1 = new JComboBox();
	String[] na= {"192.168.0.144","172.31.18.172","192.168.1.100"};
	JComboBox comboBox2 = new JComboBox(na);

	public SW() {
		contentPane.setLayout(null);
		comboBox1.setBounds(130, 30, 100, 30);
		comboBox1.addItem("chatV");
		contentPane.add(comboBox1);
		ta4.setForeground(Color.BLUE);
		comboBox2.setBounds(500, 30, 120, 30);
		contentPane.add(comboBox2);
		ta2.setBounds(330, 30,100, 40);//用户名
		/*ta3.setBounds(150, 110, 350, 200);//消息显示
		ta4.setBounds(20, 110, 100, 200);//在线用户
		ta5.setBounds(20, 350, 450, 40);//信息发送
*/		
		jsp3.setBounds(200, 120, 400, 260);
		jsp3.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp4.setBounds(50, 120, 100, 260);
		jsp5.setBounds(50, 450,550, 50);
		contentPane.add(jsp3);
		contentPane.add(jsp4);
		contentPane.add(jsp5);
		contentPane.add(ta2);
        button1.setBounds(670, 30, 100, 40);
		button2.setBounds(670, 450, 100, 40);
		button3.setBounds(670, 90, 100, 40);
		label1.setBounds(50, 20, 150, 50);
		label1.setFont(new Font("", Font.BOLD, 18));
		label2.setBounds(250, 20, 150, 50);
		label2.setFont(new Font("", Font.BOLD, 18));
		label3.setBounds(50, 70, 150, 50);
		label3.setFont(new Font("", Font.BOLD, 18));
		label4.setBounds(400, 70, 150, 50);
		label4.setFont(new Font("", Font.BOLD, 18));
		label5.setBounds(20, 400, 800, 50);
		label5.setFont(new Font("", Font.BOLD, 18));
		label6.setBounds(450, 20, 150, 50);
		label6.setFont(new Font("", Font.BOLD, 18));
		contentPane.add(label1);
		contentPane.add(label2);
		contentPane.add(label3);
		contentPane.add(label4);
		contentPane.add(label5);
		contentPane.add(label6);
		contentPane.add(button1);
		contentPane.add(button2);
		contentPane.add(button3);
		ta2.setLineWrap(true);
		ta2.setWrapStyleWord(true);
		f.setSize(800, 550);
		f.setResizable(false);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta2.setEditable(false);
				Client01.sign = true;
				UN=ta2.getText();//获取用户名
				IP=(String)comboBox2.getSelectedItem();//获取IP地址
				Client01.break_internet = true;
				Client01.thread_sign = true;
				button1.setEnabled(false);
				//System.out.println(UN);
			}
		});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(Client.signforsend) {
					Client01.infor = true;
					IO = ta5.getText();
				}else {
					SetInfo("离线状态，不能发送“" + ta5.getText() + "”，请续连后再进行相关操作");
					ClearInto();
				}
				//System.out.println(IO);
				//SetInfo(GetInformation());
				//ClearInto();
				//System.out.println("button"+IO);
			}
		});
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button1.setEnabled(true);
				button1.setText("续连");
				Client01.thread_sign = false;
				//System.out.println(IO);
				//SetInfo(GetInformation());
				//ClearInto();
				//System.out.println("button"+IO);
			}
		});
	}

	public void SetUser(String[] s) {
		//ta2.setText("1");
		//ta3.setText("3");
		//ta4.setText("");
		for(int i = 0 ; i < s.length ; i++) {
			ta4.append(s[i]+ "\n");
		}
		
		//ta5.setText("5");
	}
	
	
	public void MyName() {
		ta4.append(UN + "\n");
	}
	
	public void ClearUser() {
		ta4.setText("");
	}
	
	public void ClearInto() {
		ta5.setText("");
	}
	
	public void SetInfo(String s) {
		ta3.append(s+"\n");
		ta3.setCaretPosition(ta3.getDocument().getLength());
	}
	
	public String GetUserName() {
		return UN;
	}
	
	public String GetIPaddress() {
		return IP;
	}
	
	public String GetInformation() {
		//System.out.println(IO);
		return IO;
	}
	
	public void ClearInfor() {
		if(Client01.thread_sign) {
			IO = "";
			
		}
		else
			SetInfo("网络中断，无法发送："+IO);
	}
	
	/*public static void main(String args[]) throws IOException {
		SW xx = new SW();
		//xx.SetUser("123");
		
	}*/
}