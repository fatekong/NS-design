package FTPtest;

import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class TXT {
	String[] na = {"192.168.0.144","192.168.111","192.168.111","192.168.111","192.168.111"};
	String[] nb = {};
	String IP = "";
	String filepath;
	JFrame f = new JFrame("�ļ�����");
	JButton button1 = new JButton("ѡ���ļ�");
	JButton button2 = new JButton("�ϴ�");
	JButton button3 = new JButton("��ȡ�ļ�");
	JButton button4 = new JButton("����");
	JButton button5 = new JButton("�˳�");
	JButton button6 = new JButton("IP��");
	final JTextArea ta1 = new JTextArea();
	final JComboBox ta2 = new JComboBox();
	final JComboBox ta3 = new JComboBox(na);
	final JTextArea ta4 = new JTextArea();
	final JTextArea ta5 = new JTextArea();
	final JLabel label1 = new JLabel("*******************************�ļ��ϴ�**********************************");
	final JLabel label2 = new JLabel("*******************************FTP��Ϣ�б�*******************************");
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
				JFileChooser chooser = new JFileChooser(); // ����ѡ����
				chooser.setMultiSelectionEnabled(true); // ��Ϊ��ѡ
				int returnVal = chooser.showOpenDialog(button1); // �Ƿ���ļ�ѡ���
				if (returnVal == JFileChooser.APPROVE_OPTION) { // ��������ļ�����
					filepath = chooser.getSelectedFile().getAbsolutePath(); // ��ȡ����·��
					ta1.setText(filepath);
					if(filepath == null)
					{
						filepath = " ";
					}
				}
			}
		});
		//IP=(String)comboBox2.getSelectedItem();//��ȡIP��ַ
		button2.addActionListener(new ActionListener() {//�ϴ�
			public void actionPerformed(ActionEvent e) {
				Client01.ff = 0;
				//IP=(String)ta3.getSelectedItem();//��ȡIP��ַ
				//System.out.println("IP:"+IP);
				System.out.println(filepath);
				Client01.filepath = filepath;
				String[] sr=filepath.split("/");
				Client01.FileName = sr[sr.length-1];
				System.out.println(Client01.FileName);
				Client01.sign = true;
			}
		});
		
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client01.ff = 1;
				Client01.sign = true;
			}
			}
		);
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client01.FileName_d=(String)ta2.getSelectedItem();
				Client01.kk = 1;
			}
			}
		);
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "�����˳�...");
				System.exit(0);
			}
		});
	}
	
	public String GetIPaddress() {
		IP=(String)ta3.getSelectedItem();//��ȡIP��ַ
		System.out.println("IP:"+IP);
		//IP="192.168.0.144";
		return IP;
	}
	
	public static void main(String args[]) throws IOException {
		new TXT();
	}
}