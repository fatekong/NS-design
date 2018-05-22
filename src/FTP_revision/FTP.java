package FTP_revision;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class FTPVThread extends Thread {
	public static final String SERVER_IP = "127.0.0.1";
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
	private String SOCKET_IP = ""; 
	private Socket Sockets;
	public FTPVThread(int num,String ip,Socket so) {
		portnum = num;
		SOCKET_IP = ip;
		Sockets = so;
	}

	public HashMap<String, String> packet() throws UnsupportedEncodingException {
		HashMap<String, String> toclient = new HashMap<String, String>();
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		minute++;// TS5��һ����
		String TS5_beforeDES = month + "." + date + "." + hour + "." + minute;// ���
		DES des = new DES();
		String TS5 = des.encode(TS5_beforeDES, kcv);
		toclient.put("Prelude", Appoint_Prelude.V_C);
		toclient.put("TS5", TS5);
		return toclient;
	}

	public boolean unpacked(HashMap<String, String> fromclient) throws UnsupportedEncodingException {
		DES des = new DES();
		String Ticketv_beforeDES = fromclient.get("Ticket");
		@SuppressWarnings("unused")
		String Authric_beforeDES = fromclient.get("Authenticator");

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
			System.out.println("IDc��ADcû����");
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
				System.out.println("ʱ��û����");
				if (minute - Integer.parseInt(ts1[3]) < 2 && minute - Integer.parseInt(ts2[3]) < 1) {

					if (IDv.equals(FTP.MyID) && Integer.parseInt(LifeTime) > 1) {

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

	@SuppressWarnings({ "unchecked", "resource" })
	public void run() {
		// ��ȡ�ļ��������ļ�����
		/*
		 * File dir = new File(FTP.FilePath); String[] fileNames=dir.list(); for(int
		 * i=0;i<fileNames.length;i++) { System.out.println(fileNames[i]+";"); }
		 * System.out.println(" ");
		 */

		try {
			while (true) {
				//ServerSocket Servers = new ServerSocket(portnum);
				//Socket Sockets = Servers.accept();
				InputStream in = Sockets.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(in);
				OutputStream os = Sockets.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				HashMap<String, String> fromclient = (HashMap<String, String>) ois.readObject();
				String prelude = fromclient.get("Prelude");
				System.out.println("C:" + prelude);
				if (prelude.equals(Appoint_Prelude.C_V)) {
					boolean s = unpacked(fromclient);
					System.out.println(s);
					if (s == true) {
						FTP.kcv.put(SOCKET_IP,kcv);
						/*if (portnum == 10012) {
							client_num = 0;
							FTP.kcv[0] = kcv;
						} else if (portnum == 10013) {
							client_num = 1;
							FTP.kcv[1] = kcv;
						} else if (portnum == 10014) {
							client_num = 2;
							FTP.kcv[2] = kcv;
						} else if (portnum == 10015) {
							client_num = 3;
							FTP.kcv[3] = kcv;
						}*/
						HashMap<String, String> toclient = new HashMap<String, String>();
						toclient = packet();
						oos.writeObject(toclient);
						// VFTPthread upload = new VFTPthread("upload", portnum);// C����V���ļ�
						VFTPthread download = new VFTPthread(portnum, SOCKET_IP, "download");// C��V�ϴ��ļ�
						// upload.start();// C����V���ļ�
						download.start();// C��V�ϴ��ļ�
						System.out.println("������");
					}
				}
				//Servers.close();
				Sockets.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("socket����");
		}
	}
}

public class FTP {
	public static final String FilePath = "D:\\ѧ��\\����\\�����Ż�\\��ֽ���";
	static String MyID = "FTP";
	static int port = 10012;
	//static String kcv[] = new String[4];
	public static HashMap<String , String> kcv = new HashMap<String,String>();
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		ServerSocket Server = null;
		Socket Socket = null;
		Server = new ServerSocket(port);
		Executor executor = Executors.newFixedThreadPool(4);
		while(true) {
			Socket = Server.accept();
			String ip = Socket.getInetAddress().toString();
			//String ip = "127.0.0.1";
			System.out.println("�յ���" +ip);
			//if(ip == "/127.0.0.1")
				ip = "127.0.0.1";
			//ASThread forclient1 = new ASThread(10000);
			//forclient1.start();
			executor.execute(new FTPVThread(port,ip,Socket));
			
		}
		
		/*FTPVThread forclient1 = new FTPVThread(10012);
		FTPVThread forclient2 = new FTPVThread(10013);
		FTPVThread forclient3 = new FTPVThread(10014);
		FTPVThread forclient4 = new FTPVThread(10015);
		forclient1.start();
		forclient2.start();
		forclient3.start();
		forclient4.start();*/
	}
}