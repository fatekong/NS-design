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
	JFrame f = new JFrame("������");
	JButton button1 = new JButton("����");
	JButton button2 = new JButton("����");
	JButton button3 = new JButton("�Ͽ�");
	final JTextArea ta2 = new JTextArea();
	final JTextArea ta3 = new JTextArea();
	final JTextArea ta4 = new JTextArea();
	final JTextArea ta5 = new JTextArea();
	JScrollPane jsp3 = new JScrollPane(ta3);
	JScrollPane jsp4 = new JScrollPane(ta4);
	JScrollPane jsp5 = new JScrollPane(ta5);
	final JLabel label1 = new JLabel("��������");
	final JLabel label2 = new JLabel("�û�����");
	final JLabel label3 = new JLabel("�����û�");
	final JLabel label4 = new JLabel("��Ϣ��ʾ");
	final JLabel label6 = new JLabel("IP��");
	final JLabel label5 = new JLabel("************************************************��Ϣ������******************************************************************");
	Container contentPane = f.getContentPane();
	JComboBox comboBox1 = new JComboBox();
	String[] na= {"192.168.0.144","172.31.18.172","192.168.1.100"};
	JComboBox comboBox2 = new JComboBox(na);

	public SW() {
		contentPane.setLayout(null);
		comboBox1.setBounds(80, 30, 70, 20);
		comboBox1.addItem("chatV");
		contentPane.add(comboBox1);
		comboBox2.setBounds(360, 30, 120, 20);
		contentPane.add(comboBox2);
		ta2.setBounds(230, 30, 70, 20);//�û���
		/*ta3.setBounds(150, 110, 350, 200);//��Ϣ��ʾ
		ta4.setBounds(20, 110, 100, 200);//�����û�
		ta5.setBounds(20, 350, 450, 40);//��Ϣ����
*/		
		jsp3.setBounds(150, 110, 350, 200);
		jsp3.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp4.setBounds(20, 110, 100, 200);
		jsp5.setBounds(20, 350, 450, 40);
		contentPane.add(jsp3);
		contentPane.add(jsp4);
		contentPane.add(jsp5);
		contentPane.add(ta2);
		button1.setBounds(530, 30, 70, 40);
		button2.setBounds(500, 350, 70, 40);
		button3.setBounds(530, 80, 70, 40);
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
		contentPane.add(button3);
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
				Client01.sign = true;
				UN=ta2.getText();//��ȡ�û���
				IP=(String)comboBox2.getSelectedItem();//��ȡIP��ַ
				Client01.break_internet = true;
				Client01.thread_sign = true;
				//System.out.println(UN);
			}
		});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IO = ta5.getText();
				Client01.infor = true;
				//System.out.println(IO);
				//SetInfo(GetInformation());
				//ClearInto();
				//System.out.println("button"+IO);
			}
		});
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button1.setText("����");
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
		ta4.setText("");
		for(int i = 0 ; i < s.length ; i++) {
			ta4.append(s[i]+ "\n");
		}
		
		//ta5.setText("5");
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
			SetInfo("�����жϣ��޷����ͣ�"+IO);
	}
	
	/*public static void main(String args[]) throws IOException {
		SW xx = new SW();
		//xx.SetUser("123");
		
	}*/
}