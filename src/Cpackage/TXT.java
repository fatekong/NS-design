package Cpackage;

import java.net.*;
import java.awt.*;
import javax.swing.*;

import Cpackage.Client01;

import java.awt.event.*;
import java.io.*;

public class TXT {
	String[] na = {"192.168.0.144","192.168.111","192.168.111","192.168.111","192.168.111"};
	String[] nb = {};
	String IP = "";
	String filepath;
	public static int time1=0;
	public static int time2;
	JFrame f = new JFrame("�ļ�����");
	JButton button1 = new JButton("ѡ���ļ�");
	JButton button2 = new JButton("�ϴ�");
	JButton button3 = new JButton("��ȡ�ļ�");
	JButton button4 = new JButton("����");
	JButton button5 = new JButton("�˳�");
	final JTextArea ta1 = new JTextArea();
	final JComboBox ta2 = new JComboBox();
	final JComboBox ta3 = new JComboBox(na);
	JScrollPane jsp1 = new JScrollPane(ta1);
	final JLabel label3 = new JLabel("IP��");
	final JLabel label1 = new JLabel("******************************************�ļ��ϴ�**************************************");
	final JLabel label2 = new JLabel("******************************************FTP��Ϣ�б�***********************************");
	final JProgressBar progressBar1=new JProgressBar(); 
	final JProgressBar progressBar2=new JProgressBar(); 
	Container contentPane = f.getContentPane();

	TXT() {
		contentPane.setLayout(null);
		progressBar1.setBounds(200, 210, 300, 40);
		progressBar1.setForeground(Color.green);
		progressBar1.setStringPainted(true); 
		contentPane.add(progressBar1);
		progressBar2.setBounds(200, 410, 300, 40);
		progressBar2.setForeground(Color.green);
		progressBar2.setStringPainted(true); 
		contentPane.add(progressBar2);
		jsp1.setBounds(200, 130, 300, 40);
		contentPane.add(jsp1);
		ta2.setBounds(200, 340, 300, 40);
		contentPane.add(ta2);
		ta3.setBounds(150, 40, 200, 30);
		contentPane.add(ta3);
		button1.setBounds(90, 130, 100, 40);
		button2.setBounds(560, 130, 100, 40);
		button3.setBounds(90, 340, 100, 40);
		button4.setBounds(560, 340, 100, 40);
		button5.setBounds(560, 500, 100, 40);
		label1.setBounds(20, 80, 700, 40);
		label1.setFont(new Font("", Font.BOLD, 18));
		label2.setBounds(20, 290, 700, 40);
		label2.setFont(new Font("", Font.BOLD, 18));
		label3.setBounds(120, 40, 100, 40);
		label3.setFont(new Font("", Font.BOLD, 18));
		contentPane.add(label1);
		contentPane.add(label2);
		contentPane.add(button1);
		contentPane.add(button2);
		contentPane.add(button3);
		contentPane.add(button4);
		contentPane.add(button5);
		contentPane.add(label3);
		ta1.setLineWrap(true);
		ta1.setWrapStyleWord(true);
		f.setSize(700, 600);
		f.setResizable(false);
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
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client01.ff = 0;
				//IP=(String)ta3.getSelectedItem();//��ȡIP��ַ
				//System.out.println("IP:"+IP);
				System.out.println(filepath);
				Client01.filepath = filepath;
				String[] sr=filepath.split("\\\\");
				Client01.FileName = sr[sr.length-1];
				System.out.println(Client01.FileName);
				Client01.sign = true;
		        new Thread(){  
		            public void run(){  
		            	while(time1==0)
		            	{
		            		try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            	}
		                for(int i=0;i<=100;i++){  
		                    try{
		                        Thread.sleep(time1*2);  
		                    }catch(InterruptedException e){  
		                        e.printStackTrace();  //��ӡ�쳣
		                    }
		                      progressBar1.setValue(i);  
		                }
		                time1=0;
		                progressBar1.setString("�ϴ����");  
		            }
		        }.start();
			}
			}
		);
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
		        new Thread(){  
		            public void run(){  
		            	while(time2==0)
		            	{
		            		try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            	}
		                for(int i=0;i<=100;i++){  
		                    try{
		                        Thread.sleep(time2*5+5);  
		                        
		                    }catch(InterruptedException e){  
		                        e.printStackTrace();  //��ӡ�쳣
		                    }
		                      progressBar2.setValue(i);  
		                }
		                time2=0;
		                progressBar2.setString("�������");  
		            }
		        }.start();
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
	
	/*public static void main(String args[]) throws IOException {
		new TXT();
	}*/
}