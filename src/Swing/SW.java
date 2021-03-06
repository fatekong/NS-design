package Swing;

import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.EmptyBorder;

public class SW extends JFrame {

	JFrame f = new JFrame("聊天室");
	JButton button1 = new JButton("连接");
	JButton button2 = new JButton("发送");
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
	String[] na= {"172.31.18.172","192.168.1.105"};
	JComboBox comboBox2 = new JComboBox(na);

	SW() {
		contentPane.setLayout(null);
		comboBox1.setBounds(80, 30, 70, 20);
		comboBox1.addItem("chatV");
		contentPane.add(comboBox1);
		comboBox2.setBounds(360, 30, 120, 20);
		contentPane.add(comboBox2);
		ta2.setBounds(230, 30, 70, 20);
		jsp3.setBounds(150, 110, 350, 200);
		jsp3.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp4.setBounds(20, 110, 100, 200);
		jsp5.setBounds(20, 350, 450, 40);
		contentPane.add(ta2);
		contentPane.add(jsp3);
		contentPane.add(jsp4);
		contentPane.add(jsp5);
		button1.setBounds(500, 30, 70, 40);
		button2.setBounds(500, 350, 70, 40);
		label1.setBounds(30, 15, 150, 50);
		label2.setBounds(170, 15, 150, 50);
		label3.setBounds(30, 65, 150, 50);
		label4.setBounds(300, 65, 150, 50);
		label6.setBounds(325, 15, 150, 50);
		label5.setBounds(0, 300, 650, 50);
		contentPane.add(label1);
		contentPane.add(label2);
		contentPane.add(label3);
		contentPane.add(label4);
		contentPane.add(label5);
		contentPane.add(label6);
		contentPane.add(button1);
		contentPane.add(button2);
		ta2.setLineWrap(true);
		ta2.setWrapStyleWord(true);
		ta3.setLineWrap(true);
		ta3.setWrapStyleWord(true);
		ta4.setLineWrap(true);
		ta4.setWrapStyleWord(true);
		ta5.setLineWrap(true);
		ta5.setWrapStyleWord(true);
		f.setSize(650, 450);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta2.setEditable(false);
				String s=ta2.getText();
				String a=(String)comboBox2.getSelectedItem();
				System.out.println(s+a);
			}
		});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s=ta5.getText();
			}
		});
	}

	public static void main(String args[]) throws IOException {
		new SW();
	}
}