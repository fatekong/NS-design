package Vpackage;

import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

class chatVThread extends Thread {
	public static final String SERVER_IP = "127.0.0.1";
	int my;
	int portnum;
	int client_num;
	private String IDc = "";
	private static String kcv = "";
	private static String kv = "hhhhhhhh";
	private String ADc = "";
	private String IDv = "";
	private String TS4 = "";
	private String TS5 = "";
	private String LifeTime = "";

	public chatVThread(int num) {
		portnum = num;
		if(num == 10008)
			my = 0;
		else if(num == 10009)
			my = 1;
		else if(num == 10010)
			my = 2;
		else if(num == 10011)
			my = 3;
	}

	public HashMap<String, String> packet() {
		HashMap<String, String> toclient = new HashMap<String, String>();
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		minute++;// TS5加一操作
		String TS5_beforeDES = month + "." + date + "." + hour + "." + minute;// 封包
		DES des = new DES();
		String TS5 = des.encode(TS5_beforeDES, kcv);
		toclient.put("Prelude", Appoint_Prelude.V_C);
		toclient.put("TS5", TS5);
		return toclient;
	}

	public boolean unpacked(HashMap<String, String> fromclient) {
		DES des = new DES();
		String Ticketv_beforeDES = fromclient.get("Ticket");
		@SuppressWarnings("unused")
		String Authric_beforeDES = fromclient.get("Authenticator");
		
		chatV.RSA_E[my] = fromclient.get("E");
		chatV.RSA_N[my] = fromclient.get("N");
		
		String Ticketv = des.decode(Ticketv_beforeDES, kv);
		String ticket[] = Ticketv.split("-");
		kcv = ticket[0];
		String IDce = ticket[1];
		String ADce = ticket[2];
		IDv = ticket[3];
		TS4 = ticket[4];
		LifeTime = ticket[5];

		String Authenticator = des.decode(Authric_beforeDES, kcv);
		String authtc[] = Authenticator.split("-");
		String IDca = authtc[0];
		String ADca = authtc[1];
		TS5 = authtc[2];
		if (IDca.equals(IDce) && ADca.equals(ADce) && ADca.equals("china")) {
			IDc = IDca;
			ADc = ADca;
			Calendar c = Calendar.getInstance();
			int month = c.get(Calendar.MONTH);
			int date = c.get(Calendar.DATE);
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			String ts1[] = TS4.split("\\.");
			String ts2[] = TS5.split("\\.");
			if (month == Integer.parseInt(ts1[0]) && date == Integer.parseInt(ts1[1])
					&& hour == Integer.parseInt(ts1[2]) && month == Integer.parseInt(ts2[0])
					&& date == Integer.parseInt(ts2[1]) && hour == Integer.parseInt(ts2[2])) {
				if (minute - Integer.parseInt(ts1[3]) < 2 && minute - Integer.parseInt(ts2[3]) < 1) {
					if (IDv.equals(chatV.MyID) && Integer.parseInt(LifeTime) > 1) {
						return true;
					} else
						return false;
				} else
					return false;
			} else
				return false;
		} else {
			return false;
		}
	}

	@SuppressWarnings("resource")
	public void run() {
		try {
			while (true) {
				System.out.println("run " + portnum);
				ServerSocket Servers = new ServerSocket(portnum);
				Socket Sockets = Servers.accept();
				System.out.println("accept:" + portnum);
				InputStream in = Sockets.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(in);
				OutputStream os = Sockets.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				System.out.println("waiting...");
				@SuppressWarnings("unchecked")
				HashMap<String, String> fromclient = (HashMap<String, String>) ois.readObject();
				String prelude = fromclient.get("Prelude");
				System.out.println("C:" + prelude);
				if (prelude.equals(Appoint_Prelude.C_V)) {
					boolean s = unpacked(fromclient);
					if (s == true) {
						if (portnum == 10008) {
							chatV.state[0] = 1;
							chatV.exit[0] = true;
							client_num = 0;
							chatV.kcv[0] = kcv;
						} else if (portnum == 10009) {
							chatV.state[1] = 1;
							chatV.exit[1] = true;
							client_num = 1;
							chatV.kcv[1] = kcv;
						} else if (portnum == 10010) {
							chatV.state[2] = 1;
							chatV.exit[2] = true;
							client_num = 2;
							chatV.kcv[2] = kcv;
						} else if (portnum == 10011) {
							chatV.state[3] = 1;
							chatV.exit[3] = true;
							client_num = 3;
							chatV.kcv[3] = kcv;
						}
						HashMap<String, String> toclient = new HashMap<String, String>();
						toclient = packet();
						oos.writeObject(toclient);
						System.out.println("write_over");
						Vchatthread init = new Vchatthread(portnum, "in");
						Vchatthread out = new Vchatthread(portnum, "out");
						init.start();
						out.start();
						Servers.close();
						Sockets.close();
						while (chatV.exit[client_num]) {
							Thread.sleep(1000);
						}
						System.out.println("进入下一循环！");
					}
				}

			}
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			System.out.println("错误");
		}
	}
}

public class chatV {
	// static final Vector<String> test = new Vector<String>();
	public static final int pre[] = { 0, 0, 0, 0 };
	public static final Vector<ChatInfor> queue = new Vector<ChatInfor>();
	public static final int state[] = { 0, 0, 0, 0 };// state各代表4个端口，关闭是0，开启是1。
	public static boolean exit[] = { false, false, false, false };
	public static String[] kcv = new String[4];
	public static String MyID = "chatV";
	public static String[] RSA_E = new String[4];
	public static String[] RSA_N = new String[4]; 
	public static void main(String[] args) throws IOException {
		chatVThread forclient1 = new chatVThread(10008);
		chatVThread forclient2 = new chatVThread(10009);
		chatVThread forclient3 = new chatVThread(10010);
		chatVThread forclient4 = new chatVThread(10011);
		forclient1.start();
		forclient2.start();
		forclient3.start();
		forclient4.start();
	}
}
