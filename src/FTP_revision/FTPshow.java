package FTP_revision;

import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class FTPshow {
	JFrame f = new JFrame("FTP服务器");
	JButton button = new JButton("清空");
	JButton button1 = new JButton("开启");
	JButton button2 = new JButton("关闭");
	JTextArea ta = new JTextArea();
	JScrollPane jsp = new JScrollPane(ta);
	final JLabel label = new JLabel("FTP信息列表");
	Container contentPane = f.getContentPane();

	 FTPshow() {
		
		contentPane.setLayout(null);
		button.setBounds(500, 500, 100, 40);
		button1.setBounds(100, 500, 100, 40);
		button2.setBounds(310, 500, 100, 40);
		label.setBounds(280, 20, 150, 50);
		label.setFont(new Font("", Font.BOLD, 22));
		contentPane.add(label);
		contentPane.add(button);
		contentPane.add(button1);
		contentPane.add(button2);
		//ta.setCaretPosition(ta.getDocument().getLength());
		jsp.setBounds(80, 80, 550, 400);
		// 默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// 把滚动条添加到容器里面
		f.add(jsp);
		f.setSize(700, 600);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta.setText("");
			}
		});
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FTP.signnn = true;
				ta.setText("FTP服务器开始运行！\n");
				/*AS myas = new AS();
				try {
					myas.Run();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			}
		});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "程序退出...");
				System.exit(0);
			}
		});
	}

	public void SetTex(String txt) {
		ta.append(txt);
		ta.setCaretPosition(ta.getDocument().getLength());
	}
	
	/*public static void main(String args[]) throws IOException {
		ASshow myas = new ASshow();
		myas.SetTex("\nASD");
	}*/
}