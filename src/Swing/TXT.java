package Swing;

import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class TXT {
	String[] ip = {"192.168.111","192.168.111","192.168.111","192.168.111","192.168.111"};
	JFrame f = new JFrame("文件传输");
	JButton button1 = new JButton("选择文件");
	JButton button2 = new JButton("上传");
	JButton button3 = new JButton("获取文件");
	JButton button4 = new JButton("下载");
	JButton button5 = new JButton("退出");
	JButton button6 = new JButton("IP：");
	final JTextArea ta1 = new JTextArea();
	final JComboBox ta2 = new JComboBox();
	final JComboBox ta3 = new JComboBox(ip);
	final JTextArea ta4 = new JTextArea();
	final JTextArea ta5 = new JTextArea();
	final JLabel label1 = new JLabel("*******************************文件上传**********************************");
	final JLabel label2 = new JLabel("*******************************FTP信息列表*******************************");
	Container contentPane = f.getContentPane();

	TXT() {
		contentPane.setLayout(null);
		ta1.setBounds(140, 80, 200, 20);
		contentPane.add(ta1);
		ta2.setBounds(140, 230, 200, 20);
		contentPane.add(ta2);
		ta3.setBounds(140, 20, 200, 20);
		contentPane.add(ta3);
		ta4.setBounds(140, 120, 200, 20);
		contentPane.add(ta4);
		ta4.setEditable(false);
		ta5.setBounds(140, 270, 200, 20);
		contentPane.add(ta5);
		ta5.setEditable(false);
		button1.setBounds(30, 80, 100, 20);
		button2.setBounds(380, 80, 80, 20);
		button3.setBounds(30, 230, 100, 20);
		button4.setBounds(380, 230, 80, 20);
		button5.setBounds(380, 300, 80, 20);
		button6.setBounds(50, 20, 60, 20);
		label1.setBounds(20, 40, 400, 40);
		label2.setBounds(20, 150, 400, 40);
		contentPane.add(label1);
		contentPane.add(label2);
		contentPane.add(button1);
		contentPane.add(button2);
		contentPane.add(button3);
		contentPane.add(button4);
		contentPane.add(button5);
		contentPane.add(button6);
		ta1.setLineWrap(true);
		ta1.setWrapStyleWord(true);
		f.setSize(500, 400);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(); // 设置选择器
				chooser.setMultiSelectionEnabled(true); // 设为多选
				int returnVal = chooser.showOpenDialog(button1); // 是否打开文件选择框
				if (returnVal == JFileChooser.APPROVE_OPTION) { // 如果符合文件类型
					String filepath = chooser.getSelectedFile().getAbsolutePath(); // 获取绝对路径
					ta1.setText(filepath);
				}
			}
		});
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] na = {"1.txt","2.txt","3.txt","4.txt","5.txt"};
				for(int i=0;i<na.length;i++) {
					ta2.addItem(na[i]);
				}
			}
			}
		);
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s=(String)ta2.getSelectedItem();
				System.out.println(s);
			}
			}
		);
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "程序退出...");
				System.exit(0);
			}
		});
	}
	
	public static void main(String args[]) throws IOException {
		new TXT();
	}
}