package FTPtest;

import java.net.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

import java.math.BigInteger;

public class Client01 {
	static boolean break_internet = true;
	static final int ASport = 10000;
	static final int TGSport = 10001;
	static final int chatVport = 10008;
	static final int FTPport = 10012;
	static final int my = 0;
	static String MyName = "曌";
	public static final String AS_IP = "192.168.0.144";
	public static final String TGS_IP = "192.168.0.144";
	public static String chatV_IP = "192.168.0.144";
	public static String FTP_IP = "192.168.0.144";
	public static final String My_IP = "192.168.0.144";
	public static String filepath;
	public static String FileName;
	public static String FileName_d;
	public static int ff ;
	public static int kk=0 ;
	
	// private String IDv = "chatV";//FTP or chatV
	private String IDv = "FTP";
	private String IDtgs = "TGS";
	private String ADc = "";
	public static String MyID = "C1";
	private String kctgs = "asvcwasd";
	private String ktgs = "bbbbbbbb";
	private String kc = "12345678";
	static String kcv = "";
	private String TS2 = "";
	private String TS4 = "";
	private String TS5 = "";
	private String Ticketv = "";
	private String Tickettgs = "";
	public static BigInteger E;
	public static BigInteger D;
	public static BigInteger N;

	// 给AS发送的包
	public HashMap<String, String> ToAS() {
		HashMap<String, String> toas = new HashMap<String, String>();
		// DES des = new DES();
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		String TS1 = month + "." + date + "." + hour + "." + minute;// 封包
		// String LifeTime = "2";
		// String Ticket_beforeDES = kctgs + "-" + MyID + "-" + ADc + "-" + IDtgs + "-"
		// + TS2 + "-" + LifeTime;
		// String Authenticator_beforeDES = MyID + "-" + ADc + "-" + TS1;
		// String Ticket = des.encode(Ticket_beforeDES, ktgs);
		// String Authenticator = des.encode(Authenticator_beforeDES, kctgs);
		toas.put("Prelude", Appoint_Prelude.C_AS);// 首部
		toas.put("IDc", MyID);
		toas.put("TS1", TS1);
		// totgs.put("Ticket", Ticket);
		// totgs.put("Authenticator", Authenticator);
		return toas;
	}

	// 给TGS发送的包
	public HashMap<String, String> ToTGS() throws UnsupportedEncodingException {
		HashMap<String, String> totgs = new HashMap<String, String>();
		DES des = new DES();
		/*
		 * Calendar c = Calendar.getInstance(); int month = c.get(Calendar.MONTH); int
		 * date = c.get(Calendar.DATE); int hour = c.get(Calendar.HOUR_OF_DAY); int
		 * minute = c.get(Calendar.MINUTE); String TS2 = month + "." + date + "." + hour
		 * + "." + minute;// 封包 String LifeTime = "2"; String Ticket_beforeDES = kctgs +
		 * "-" + MyID + "-" + ADc + "-" + IDtgs + "-" + TS2 + "-" + LifeTime;
		 * 
		 * String Ticket = des.encode(Ticket_beforeDES, ktgs);
		 */

		String Authenticator_beforeDES = MyID + "-" + ADc + "-" + TS2;
		String Authenticator = des.encode(Authenticator_beforeDES, kctgs);
		totgs.put("Prelude", Appoint_Prelude.C_TGS);
		totgs.put("IDv", IDv);
		totgs.put("Ticket", Tickettgs);
		totgs.put("Authenticator", Authenticator);
		return totgs;
	}

	// 获取AS发过来的信息
	public void FormAS(HashMap<String, String> fromtgs) throws UnsupportedEncodingException {
		// 先从hashmap中得到未解密的数据
		String kctgs_beforeDES = fromtgs.get("des_kctgs");
		System.out.println("解密前的Kctgs:" + kctgs_beforeDES);
		String IDtgs_beforeDES = fromtgs.get("des_IDtgs");
		System.out.println("解密前的IDtgs:" + IDtgs_beforeDES);
		String TS2_beforeDES = fromtgs.get("TS2");
		System.out.println("解密前的TS2:" + TS2_beforeDES);
		String tct_beforeDES = fromtgs.get("ticket_to_tgs_before");
		byte[] kkk = tct_beforeDES.getBytes("ISO_8859_1");

		DES des = new DES();
		byte[] mmm = des.decode_b(kkk, kc);
		// String tct_beforeDES = new String(des.decode_b(fortgs, kc));
		System.out.println("解密前的Ticket to tgs:" + tct_beforeDES);

		kctgs = des.decode(kctgs_beforeDES, kc);
		System.out.println("解密后的K(c,tgs):" + kctgs);
		IDtgs = des.decode(IDtgs_beforeDES, kc);
		System.out.println("解密后的IDtgs:" + IDtgs);
		/*
		 * if(IDtgs.equals("TGS")) System.out.println("ok");
		 */
		TS2 = des.decode(TS2_beforeDES, kc);
		System.out.println("解密后的TS2:" + TS2);

		Tickettgs = new String(mmm, "ISO_8859_1");
		byte[] kkk11 = Tickettgs.getBytes("ISO_8859_1");
		byte[] sss = des.decode_b(kkk11, ktgs);// 第二次解密后的密文二级制（明文二进制）
		String ttt = new String(sss, "UTF8");// 第二次解密后的明文String
		ttt.trim();
		System.out.println("第二次解密：" + ttt);
		// Tickettgs = des.decode(tct_beforeDES, kc);
		// String ttt = new String(des.decode_b(fortgs, kc));
		// Tickettgs = des.decode(ttt, ktgs);
		System.out.println("解密后的Ticket：" + Tickettgs);
	}

	// 获取TGS发过来的信息
	public void FormTGS(HashMap<String, String> fromtgs) throws UnsupportedEncodingException {
		// 先从hashmap中得到未解密的数据
		String kcv_beforeDES = fromtgs.get("K(c,v)");
		System.out.println("解密前的K(c,v):" + kcv_beforeDES);
		String IDv_beforeDES = fromtgs.get("IDv");
		System.out.println("解密前的IDv:" + IDv_beforeDES);
		String TS4_beforeDES = fromtgs.get("TS4");
		System.out.println("解密前的TS4:" + TS4_beforeDES);
		String tct_beforeDES = fromtgs.get("Ticket");
		System.out.println("解密前的Ticket:" + tct_beforeDES);
		DES des = new DES();
		kcv = des.decode(kcv_beforeDES, kctgs);
		System.out.println("解密后的K(c,v):" + kcv);
		IDv = des.decode(IDv_beforeDES, kctgs);
		System.out.println("解密后的IDv:" + IDv);
		TS4 = des.decode(TS4_beforeDES, kctgs);
		System.out.println("解密后的TS4:" + TS4);
		Ticketv = tct_beforeDES;
	}

	public Client01(String v) {
		this.IDv = v;
	}

	public HashMap<String, String> ToV() throws UnsupportedEncodingException {
		HashMap<String, String> tov = new HashMap<String, String>();
		DES des = new DES();
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		String TS5 = month + "." + date + "." + hour + "." + minute;
		String Authenticator_beforeDES = MyID + "-" + ADc + "-" + TS5;
		String Authenticator = des.encode(Authenticator_beforeDES, kcv);
		Prime_Method prime_Method = new Prime_Method();
		BigInteger p = prime_Method.GetPorQ();
		BigInteger q = prime_Method.GetPorQ();
		while (p.equals(q)) {
			q = prime_Method.GetPorQ();
		}
		E = prime_Method.GetE(p, q);
		D = prime_Method.GetD(p, q, E);
		N = p.multiply(q);
		tov.put("Prelude", Appoint_Prelude.C_V);
		tov.put("Ticket", Ticketv);
		tov.put("Authenticator", Authenticator);
		tov.put("E", E.toString());
		tov.put("N", N.toString());
		return tov;
	}

	public boolean FromV(HashMap<String, String> fromv) throws UnsupportedEncodingException {
		String TS5_beforeDES = fromv.get("TS5");
		DES des = new DES();
		String TS5_1 = des.decode(TS5_beforeDES, kcv);
		if (!TS5_1.equals(TS5)) {
			String ts[] = TS5_1.split("\\.");
			Calendar c = Calendar.getInstance();
			int month = c.get(Calendar.MONTH);
			int date = c.get(Calendar.DATE);
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			if (month == Integer.parseInt(ts[0]) && date == Integer.parseInt(ts[1])
					&& hour == Integer.parseInt(ts[2])) {
				if (Integer.parseInt(ts[3]) - minute >= 0) {
					return true;
				} else
					return false;
			} else
				return false;
		} else
			return false;
	}

	public void SetUserName(String s) {
		if (!s.equals(""))
			MyName = s;
	}

	public void SetIPaddress_chat(String s) {
		if (!s.equals(""))
			chatV_IP = s;
	}

	public void SetIPaddress_ftp(String s)
	{
		if (!s.equals(""))
		FTP_IP = s;
	}
	
	public void SetADc(String s) {
		String ip[] = s.split("/");
		ADc = ip[1];
	}
	
	public static boolean sign = false;
	public static boolean infor = false;
	public static SW sw;
	public static TXT txt;
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws ClassNotFoundException, InterruptedException {
		
		//String V = "chatV";
		String V = "FTP";
		if(V == "chatV")
		{
			try {
				sw = new SW();
				//String V = "chatV";
				while (true) {
				while (sign == false) {
					Thread.sleep(1000);
				}
				System.out.println("连接按钮");
				// String V = "FTP";
				Client01 c1 = new Client01(V);
				c1.SetIPaddress_chat(sw.GetIPaddress());
				c1.SetUserName(sw.GetUserName());
				
	
				
				
					while (!break_internet) {
						Thread.sleep(1000);
					}
					Socket socket = new Socket(AS_IP, ASport);
					c1.SetADc(socket.getLocalAddress().toString());
					OutputStream os = socket.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(os);
					InputStream is = socket.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);
	
					HashMap<String, String> ToAS = new HashMap<String, String>();
					ToAS = c1.ToAS();
					oos.writeObject(ToAS);
	
					HashMap<String, String> FromAS = (HashMap<String, String>) ois.readObject();
					// System.out.println("asassdadsadadadasdadadsafafafad");
					// DataInputStream in = new DataInputStream(socket.getInputStream());
					// byte[] fortgs = new byte[40];
					// int i = in.read(fortgs);
					// System.out.println(i);
					// System.out.println();
					String as_Prelude = FromAS.get("Prelude");
					//sw.SetInfo("AS连接成功");
					while (!as_Prelude.equals(Appoint_Prelude.AS_C)) {
						oos.writeObject(ToAS);
					}
					c1.FormAS(FromAS);
					socket.close();
	
					socket = new Socket(TGS_IP, TGSport);
					HashMap<String, String> ToTGS = new HashMap<String, String>();
					os = socket.getOutputStream();
					oos = new ObjectOutputStream(os);
					is = socket.getInputStream();
					ois = new ObjectInputStream(is);
					ToTGS = c1.ToTGS();
					oos.writeObject(ToTGS);
					HashMap<String, String> FromTGS = (HashMap<String, String>) ois.readObject();
					String tgs_Prelude = FromTGS.get("Prelude");
					while (!tgs_Prelude.equals(Appoint_Prelude.TGS_C)) {
						oos.writeObject(ToTGS);
					}
					c1.FormTGS(FromTGS);
					// System.out.println("over");
					socket.close();
					sw.SetInfo("TGS连接成功");
					HashMap<String, String> ToV = new HashMap<String, String>();
					if (V == "chatV") {
						socket = new Socket(chatV_IP, chatVport);
						os = socket.getOutputStream();
						oos = new ObjectOutputStream(os);
						ToV = c1.ToV();
						oos.writeObject(ToV);
						is = socket.getInputStream();
						ois = new ObjectInputStream(is);
						HashMap<String, String> FromV = (HashMap<String, String>) ois.readObject();
						String v_Prelude = FromV.get("Prelude");
						while (!v_Prelude.equals(Appoint_Prelude.V_C)) {
							oos.writeObject(ToV);
						}
						boolean s = c1.FromV(FromV);
						String V_Prelude = FromV.get("Prelude");
						System.out.println("s:" + s);
						if (V_Prelude.equals(Appoint_Prelude.V_C) && s == true) {
							sw.SetInfo("V连接成功");
							Appoint_Client.state[my] = 1;
							Cchatthread init = new Cchatthread(chatVport, my, "in", MyName);
							Cchatthread out = new Cchatthread(chatVport, my, "out", MyName);
							System.out.println("开启线程");
							init.start();
							out.start();
						}
					} else if (V == "FTP") {
						socket = new Socket(FTP_IP, FTPport);
						os = socket.getOutputStream();
						oos = new ObjectOutputStream(os);
						ToV = c1.ToV();
						oos.writeObject(ToV);
						is = socket.getInputStream();
						ois = new ObjectInputStream(is);
						HashMap<String, String> FromV = (HashMap<String, String>) ois.readObject();
						String v_Prelude = FromV.get("Prelude");
						while (!v_Prelude.equals(Appoint_Prelude.V_C)) {
							oos.writeObject(ToV);
						}
						boolean s = c1.FromV(FromV);
						String V_Prelude = FromV.get("Prelude");
						if (V_Prelude.equals(Appoint_Prelude.V_C) && s == true) {
							System.out.println("收到V-》C");
							Cftpthread upload = new Cftpthread(FTPport, "upload");// 上传
							// Cftpthread download = new Cftpthread(FTPport,"download");//下载
							upload.start();
							// download.start();
						}
					}
					sign=false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else//FTP
		{
			try {
				txt = new TXT();
//				String V = "FTP";
				while(true)
				{
				while (sign == false) {
					Thread.sleep(1000);
				}
				System.out.println("连接按钮");
				// String V = "FTP";
				Client01 c1 = new Client01(V);
				c1.SetIPaddress_ftp(txt.GetIPaddress());
				//while (true) {
					while (!break_internet) {
						Thread.sleep(1000);
					}
					Socket socket = new Socket(AS_IP, ASport);
					c1.SetADc(socket.getLocalAddress().toString());
					OutputStream os = socket.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(os);
					InputStream is = socket.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);
	
					HashMap<String, String> ToAS = new HashMap<String, String>();
					ToAS = c1.ToAS();
					oos.writeObject(ToAS);
	
					HashMap<String, String> FromAS = (HashMap<String, String>) ois.readObject();
					String as_Prelude = FromAS.get("Prelude");
					while (!as_Prelude.equals(Appoint_Prelude.AS_C)) {
						oos.writeObject(ToAS);
					}
					c1.FormAS(FromAS);
					socket.close();
	
					socket = new Socket(TGS_IP, TGSport);
					HashMap<String, String> ToTGS = new HashMap<String, String>();
					os = socket.getOutputStream();
					oos = new ObjectOutputStream(os);
					is = socket.getInputStream();
					ois = new ObjectInputStream(is);
					ToTGS = c1.ToTGS();
					oos.writeObject(ToTGS);
					HashMap<String, String> FromTGS = (HashMap<String, String>) ois.readObject();
					String tgs_Prelude = FromTGS.get("Prelude");
					while (!tgs_Prelude.equals(Appoint_Prelude.TGS_C)) {
						System.out.println("错误原因："+FromTGS.get("error"));
						oos.writeObject(ToTGS);
					}
					c1.FormTGS(FromTGS);
					socket.close();
					//sw.SetInfo("TGS连接成功");
					HashMap<String, String> ToV = new HashMap<String, String>();
					if (V == "chatV") {
						socket = new Socket(chatV_IP, chatVport);
						os = socket.getOutputStream();
						oos = new ObjectOutputStream(os);
						ToV = c1.ToV();
						oos.writeObject(ToV);
						is = socket.getInputStream();
						ois = new ObjectInputStream(is);
						HashMap<String, String> FromV = (HashMap<String, String>) ois.readObject();
						String v_Prelude = FromV.get("Prelude");
						while (!v_Prelude.equals(Appoint_Prelude.V_C)) {
							oos.writeObject(ToV);
						}
						boolean s = c1.FromV(FromV);
						String V_Prelude = FromV.get("Prelude");
						System.out.println("s:" + s);
						if (V_Prelude.equals(Appoint_Prelude.V_C) && s == true) {
							//sw.SetInfo("V连接成功");
							Appoint_Client.state[my] = 1;
							Cchatthread init = new Cchatthread(chatVport, my, "in", MyName);
							Cchatthread out = new Cchatthread(chatVport, my, "out", MyName);
							System.out.println("开启线程");
							init.start();
							out.start();
						}
					} else if (V == "FTP") {
						socket = new Socket(FTP_IP, FTPport);
						os = socket.getOutputStream();
						oos = new ObjectOutputStream(os);
						ToV = c1.ToV();
						oos.writeObject(ToV);
						is = socket.getInputStream();
						ois = new ObjectInputStream(is);
						HashMap<String, String> FromV = (HashMap<String, String>) ois.readObject();
						String v_Prelude = FromV.get("Prelude");
						while (!v_Prelude.equals(Appoint_Prelude.V_C)) {
							oos.writeObject(ToV);
						}
						boolean s = c1.FromV(FromV);
						String V_Prelude = FromV.get("Prelude");
						if (V_Prelude.equals(Appoint_Prelude.V_C) && s == true) {
							System.out.println("收到V-》C");
							if(Client01.ff == 0)
							{
								Cftpthread upload = new Cftpthread(FTPport, "upload");// 上传
								upload.start();
							}
							if(Client01.ff == 1)
							{
								Cftpthread download = new Cftpthread(FTPport,"download");//下载
								download.start();
							}
						}
					}
					sign=false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
		
}
