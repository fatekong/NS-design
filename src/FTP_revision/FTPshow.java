package FTP_revision;

import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class FTPshow {
	JFrame f = new JFrame("FTP������");
	JButton button = new JButton("���");
	JButton button1 = new JButton("����");
	JButton button2 = new JButton("�ر�");
	JTextArea ta = new JTextArea();
	JScrollPane jsp = new JScrollPane(ta);
	final JLabel label = new JLabel("FTP��Ϣ�б�");
	Container contentPane = f.getContentPane();

	 FTPshow() {
		
		contentPane.setLayout(null);
		button.setBounds(180, 300, 100, 40);
		button1.setBounds(50, 300, 100, 40);
		button2.setBounds(310, 300, 100, 40);
		label.setBounds(160, 15, 150, 50);
		label.setFont(new Font("", Font.BOLD, 22));
		contentPane.add(label);
		contentPane.add(button);
		contentPane.add(button1);
		contentPane.add(button2);
		ta.setLineWrap(true);
		//ta.setText("aafafaf");
		ta.setWrapStyleWord(true);
		jsp.setBounds(20, 60, 420, 200);
		// Ĭ�ϵ������ǳ����ı���Ż���ʾ�����������������ù�����һֱ��ʾ
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// �ѹ�������ӵ���������
		f.add(jsp);
		f.setSize(500, 400);
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
				ta.setText("FTP��������ʼ���У�\n");
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
				JOptionPane.showMessageDialog(null, "�����˳�...");
				System.exit(0);
			}
		});
	}

	public void SetTex(String txt) {
		ta.append(txt);
	}
	
	/*public static void main(String args[]) throws IOException {
		ASshow myas = new ASshow();
		myas.SetTex("\nASD");
	}*/
}