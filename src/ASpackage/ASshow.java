package ASpackage;

import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class ASshow {
	JFrame f = new JFrame("AS������");
	JButton button = new JButton("���");
	JButton button1 = new JButton("����");
	JButton button2 = new JButton("�ر�");
	JTextArea ta = new JTextArea();
	JScrollPane jsp = new JScrollPane(ta);
	final JLabel label = new JLabel("AS��Ϣ�б�");
	Container contentPane = f.getContentPane();

	ASshow() {
		
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
		// Ĭ�ϵ������ǳ����ı���Ż���ʾ�����������������ù�����һֱ��ʾ
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// �ѹ�������ӵ���������
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
				ta.setText("AS��������ʼ���У�\n");
				AS.sign = true;
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
		ta.setCaretPosition(ta.getDocument().getLength());
	}
	
	/*public static void main(String args[]) throws IOException {
		ASshow myas = new ASshow();
		myas.SetTex("\nASD");
	}*/
}