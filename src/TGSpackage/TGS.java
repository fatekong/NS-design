package TGSpackage;

import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;

class TGSThread extends Thread {
	public static final String SERVER_IP = "127.0.0.1";
	int portnum;
	private String kv = "hhhhhhhh";
	private String ktgs = "bbbbbbbb";
	public String IDv;
	public String ticket;
	public String ticket_M;// ���ܺ��tickets
	public String Prelude;
	public String kctgs;
	public String IDc;
	public String kcv;
	public String ADc;
	public String IDtgs;
	public String TS2;
	public String TS3;
	public String TS4;
	public String LifeTime2;
	public String LifeTime4;
	public String Authenticator;
	public String Authenticator_M;// ���ܺ��Authenticator
	public String checker;// ��Ʊ��

	public TGSThread(int num) {
		portnum = num;
	}

	public void unpacked(HashMap<String, String> fromclient) {
		Prelude = fromclient.get("Prelude");// �ײ�
		System.out.println("Prelude:" + Prelude);
		
		IDv = fromclient.get("IDv");// IDv
		System.out.println("IDv:" + IDv);
		
		ticket = fromclient.get("Ticket");//
		System.out.println("ticket:" + ticket);
		
		DES des = new DES();
		ticket_M = des.decode(ticket, ktgs);
		/*
		 * �˴���Ҫʹ��Etgs�Խ���ticket����
		 *
		 */
		String[] ticket_pro = ticket_M.split("-");// ���ܺ��tickets���
		kctgs = ticket_pro[0];// C��TGS֮���Կ��
		System.out.println("kctgs:" + kctgs);
		IDc = ticket_pro[1];// C��ID��
		System.out.println("IDc: "+IDc);
		ADc = ticket_pro[2];//
		System.out.println("ADc: "+ADc);
		IDtgs = ticket_pro[3];// TGS��ID��
		System.out.println("IDtgs: "+IDtgs);
		TS2 = ticket_pro[4];
		System.out.println("TS2: "+TS2);
		LifeTime2 = ticket_pro[5];
		System.out.println("LifeTime2: "+LifeTime2);
		
		Authenticator = fromclient.get("Authenticator");
		System.out.println("Authenticator:" + Authenticator);
		Authenticator_M = des.decode(Authenticator, kctgs);// ����
		/*
		 * �˴���Ҫʹ��Kc,tgs��Authenticator���н���
		 * 
		 */
		String[] Authenticator_pro = Authenticator_M.split("-");// Authenticators���
		IDc = Authenticator_pro[0];
		System.out.println("IDc: "+IDc);
		ADc = Authenticator_pro[1];
		System.out.println("ADc: "+ADc);
		TS3 = Authenticator_pro[2];
		System.out.println("TS3: "+TS3);
	}

	public HashMap<String, String> packet() {
		// ��kcv + IDv + TS4 + Ticketv
		HashMap<String, String> toclient = new HashMap<String, String>();
		DES des = new DES();
		kcv = des.Main_key();// ���
		System.out.println("Kcv: "+kcv);
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		TS4 = month + "." + date + "." + hour + "." + minute;// ���
		// Ticketv
		LifeTime4 = Integer.parseInt(LifeTime2)+"";
		ADc = "china";
		String Ticket_beforeDES = kcv + "-" + IDc + "-" + ADc + "-" + IDv + "-" + TS4 + "-" + LifeTime4;
		System.out.println("Ticket: "+Ticket_beforeDES);
		String Ticketv = des.encode(Ticket_beforeDES, kv);
		String kcv_afterDES = des.encode(kcv, kctgs);
		String idv_afterDES = des.encode(IDv, kctgs);
		String TS4_afterDES = des.encode(TS4, kctgs);
		//String Tct_afterDES = des.encode(Ticket_beforeDES, kctgs);
		toclient.put("Prelude", Appoint_Prelude.TGS_C);
		toclient.put("K(c,v)", kcv_afterDES);
		toclient.put("IDv", idv_afterDES);
		toclient.put("TS4", TS4_afterDES);
		toclient.put("Ticket", Ticketv);
		return toclient;
	}

	public void run() {
		try {
			ServerSocket Servers = new ServerSocket(portnum);
			while (true) {
				Socket Sockets = Servers.accept();
				OutputStream os = Sockets.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				InputStream in = Sockets.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(in);
				@SuppressWarnings("unchecked")
				HashMap<String, String> fromclient = (HashMap<String, String>) ois.readObject();
				System.out.println("portnum:" + portnum);
				unpacked(fromclient);
				Calendar c = Calendar.getInstance();
				int month = c.get(Calendar.MONTH);
				int date = c.get(Calendar.DATE);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);
				HashMap<String, String> toclient = new HashMap<String, String>();
				System.out.println(TS2);
				String[] ts = TS2.split("\\.");
				System.out.println(ts.length);
				if (month == Integer.parseInt(ts[0]) && date == Integer.parseInt(ts[1])
						&& hour == Integer.parseInt(ts[2])) {
					System.out.println("ʱ����ȷ��");
					if (minute - Integer.parseInt(ts[3]) < 1) {
						if (!ADc.equals("china"))// ����ײ�����������󷵻ش�����Ϣ���¿�ʼ����
						{
							toclient.put("Prelude", Appoint_Prelude.TGS_C_error);
							System.out.println("ADc��֤ʧ�ܣ�");
							toclient.put("error", "ADc��֤ʧ��");
							oos.writeObject(toclient);
							continue;
						} else if (Integer.parseInt(LifeTime2) != 2)// ���tickets�еĸ�������
						{
							toclient.put("Prelude", Appoint_Prelude.TGS_C_error);
							toclient.put("error", "�������ڳ�ʱ");
							System.out.println("�������ڳ�ʱ��");
							oos.writeObject(toclient);
						} else {
							System.out.println("�������ͣ�");
							toclient = packet();
							oos.writeObject(toclient);
						}
					} else {
						toclient.put("Prelude", Appoint_Prelude.TGS_C_error);
						toclient.put("error", "��ʱ");
						System.out.println("��ʱ��");
						oos.writeObject(toclient);
					}
				} else {
					toclient.put("Prelude", Appoint_Prelude.TGS_C_error);
					toclient.put("error", "��ʱ");
					oos.writeObject(toclient);
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("����");
		}
	}
}

public class TGS {
	public static void main(String[] args) throws IOException {
		TGSThread forclient1 = new TGSThread(10004);
		TGSThread forclient2 = new TGSThread(10005);
		TGSThread forclient3 = new TGSThread(10006);
		TGSThread forclient4 = new TGSThread(10007);
		forclient1.start();
		forclient2.start();
		forclient3.start();
		forclient4.start();
	}
}
