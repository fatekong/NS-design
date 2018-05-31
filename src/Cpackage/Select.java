package Cpackage;

import java.net.*;
import java.awt.*;
import javax.swing.*;

import Cpackage.SW;

import java.awt.event.*;
import java.io.*;

public class Select{
	//static int a=0;
	Select(){
		JFrame f=new JFrame("功能选择");
		JButton button1=new JButton("聊天室");
		JButton button2=new JButton("文件传输");
		//final JTextArea ta1=new JTextArea();
		//final JLabel label1=new JLabel("用户");
		final JLabel label2=new JLabel("请选择功能：");
		Container contentPane = f.getContentPane();
		contentPane.setLayout(null);
		//ta1.setBounds(60,30,70,20);
		//contentPane.add(ta1);
		button1.setBounds(80, 130, 150, 80);
		button2.setBounds(380, 130, 150, 80);
		//label1.setBounds(30,15,150,50);
		label2.setBounds(80,50,150,40);
		label2.setFont(new Font("", Font.BOLD, 22));
	//	contentPane.add(label1);
		contentPane.add(label2);
		contentPane.add(button1);
		contentPane.add(button2);
        //ta1.setLineWrap(true); 
       // ta1.setWrapStyleWord(true); 
		f.setSize(600,300);
		f.setResizable(false);
		f.setVisible(true);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	a=1;
			//	System.out.println(a);
				Client.signforchoose = true;
				Client.V = "chatV";
				f.dispose();
			}
			});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	a=2;
			//	System.out.println(a);
			//	new TXT();
				Client.signforchoose = true;
				Client.V = "FTP";
				f.dispose();
			}
			});
	}
	/*public static void main(String args[]) throws IOException {
		JFrame f=new JFrame("客户机选择");
		JButton button1=new JButton("聊天室");
		JButton button2=new JButton("文件传输");
		final JTextArea ta1=new JTextArea();
		final JLabel label1=new JLabel("用户");
		final JLabel label2=new JLabel("请选择功能：");
		Container contentPane = f.getContentPane();
		contentPane.setLayout(null);
		ta1.setBounds(60,30,70,20);
		contentPane.add(ta1);
		button1.setBounds(30, 120, 100, 40);
		button2.setBounds(150, 120, 100, 40);
		label1.setBounds(30,15,150,50);
		label2.setBounds(60,50,150,50);
		contentPane.add(label1);
		contentPane.add(label2);
		contentPane.add(button1);
		contentPane.add(button2);
        ta1.setLineWrap(true); 
        ta1.setWrapStyleWord(true); 
		f.setSize(300,250);
		f.setVisible(true);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	a=1;
			//	System.out.println(a);
				
			}
			});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	a=2;
			//	System.out.println(a);
			//	new TXT();
			}
			});
	}*/
}