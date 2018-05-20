/*
import java.net.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.*;

public class Client01 {
	static final int ASport = 10000;
	static final int TGSport = 10004;
	static final int chatVport = 10008;
	static final int FTPport = 10012;
	static final int my = 0;
	static final String MyName = "曌";
	public static final String AS_IP = "127.0.0.1";
	public static final String TGS_IP = "127.0.0.1";
	public static final String chatV_IP = "127.0.0.1";
	public static final String FTP_IP = "127.0.0.1";
	public static final String My_IP = "127.0.0.1";
	private String IDv = "chatV";
	private String IDtgs = "TGS";
	private String ADc = "china";
	private String MyID = "C1";
	private String kctgs = "asvcwasd";
	private String ktgs = "bbbbbbbb";
	private String kcv = "";
	private String kc = "12345678";
	private String TS2 = "";
	private String TS4 = "";
	private String TS5 = "";
	private String Ticketv = "";
	private String ticket_to_tgs="";

	
	// 给AS发送的包
	
		public HashMap<String, String> ToAS() {
			HashMap<String, String> toas = new HashMap<String, String>();
			DES des = new DES();
			Calendar c = Calendar.getInstance();
			int month = c.get(Calendar.MONTH);
			int date = c.get(Calendar.DATE);
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			String TS1 = month + "." + date + "." + hour + "." + minute;// 封包
			//String LifeTime = "2";
			//String Ticket_beforeDES = kctgs + "-" + MyID + "-" + ADc + "-" + IDtgs + "-" + TS2 + "-" + LifeTime;
			//String Authenticator_beforeDES = MyID + "-" + ADc + "-" + TS1;
			//String Ticket = des.encode(Ticket_beforeDES, ktgs);
			//String Authenticator = des.encode(Authenticator_beforeDES, kctgs);
			toas.put("Prelude", Appoint_Prelude.C_AS);//首部
			toas.put("IDc", MyID);
			toas.put("TS1", TS1);
			//totgs.put("Ticket", Ticket);
			//totgs.put("Authenticator", Authenticator);
			return toas;
		}
		
	
	// 给TGS发送的包
	public HashMap<String, String> ToTGS() {
		HashMap<String, String> totgs = new HashMap<String, String>();
		DES des = new DES();
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		String TS2 = month + "." + date + "." + hour + "." + minute;// 封包
		String LifeTime = "2";
		String Ticket_beforeDES = kctgs + "-" + MyID + "-" + ADc + "-" + IDtgs + "-" + TS2 + "-" + LifeTime;
		String Authenticator_beforeDES = MyID + "-" + ADc + "-" + TS2;
		String Ticket = des.encode(Ticket_beforeDES, ktgs);
		String Authenticator = des.encode(Authenticator_beforeDES, kctgs);
		totgs.put("Prelude", Appoint_Prelude.C_TGS);
		totgs.put("IDv", IDv);
		totgs.put("Ticket", Ticket);
		totgs.put("Authenticator", Authenticator);
		return totgs;
	}

	
	public void FormAS(HashMap<String, String> fromtgs) {
		// 先从hashmap中得到未解密的数据
		String kctgs_beforeDES = fromtgs.get("des_kctgs");
		System.out.println("解密前的Kctgs:" + kctgs_beforeDES);
		String IDtgs_beforeDES = fromtgs.get("des_IDtgs");
		System.out.println("解密前的IDtgs:" + IDtgs_beforeDES);
		String TS2_beforeDES = fromtgs.get("TS2");
		System.out.println("解密前的TS2:" + TS2_beforeDES);
		String tct_beforeDES = fromtgs.get("ticket_to_tgs_before");
		System.out.println("解密前的Ticket to tgs:" + tct_beforeDES);
		DES des = new DES();
		kctgs = des.decode(kctgs_beforeDES, kc);
		System.out.println("解密后的K(c,tgs):" + kctgs);
		IDtgs = des.decode(IDtgs_beforeDES, kc);
		System.out.println("解密后的IDtgs:" + IDtgs);
		TS2 = des.decode(TS2_beforeDES, kc);
		System.out.println("解密后的TS2:" + TS2);
		ticket_to_tgs = tct_beforeDES;
	}
	// 获取TGS发过来的信息
	public void FormTGS(HashMap<String, String> fromtgs) {
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

	public HashMap<String, String> ToV() {
		HashMap<String,String> tov = new HashMap<String,String>();
		DES des = new DES();
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		String TS5 = month + "." + date + "." + hour + "." + minute;
		String Authenticator_beforeDES = MyID + "-" + ADc + "-" + TS5;
		String Authenticator = des.encode(Authenticator_beforeDES, kcv);
		tov.put("Prelude", Appoint_Prelude.C_V);
		tov.put("Ticket", Ticketv);
		tov.put("Authenticator",Authenticator);
		return tov;
	}
	
	public void FromV(HashMap<String,String> fromv) {
		String TS5_beforeDES = fromv.get("TS5");
		DES des = new DES();
		TS5 = des.decode(TS5_beforeDES, kcv);
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws ClassNotFoundException {
		try {
			String V = "chatV";
			Client01 c1 = new Client01(V);
			
			Socket socket1 = new Socket(AS_IP, ASport);
			HashMap<String, String> ToAS = new HashMap<String, String>();
			OutputStream os1 = socket1.getOutputStream();
			ObjectOutputStream oos1 = new ObjectOutputStream(os1);
			InputStream is1 = socket1.getInputStream();
			ObjectInputStream ois1 = new ObjectInputStream(is1);
			ToAS = c1.ToAS();
			oos1.writeObject(ToAS);
			
			
			HashMap<String, String> FromAS = (HashMap<String, String>) ois1.readObject();
			String as_Prelude = FromAS.get("Prelude");
			while (!as_Prelude.equals(Appoint_Prelude.AS_C)) {
				oos1.writeObject(ToAS);
			}
			c1.FormAS(FromAS);
			System.out.println("over");
			socket1.close();
			
			
			
			
			
			
			Socket socket = new Socket(TGS_IP, TGSport);
			HashMap<String, String> ToTGS = new HashMap<String, String>();
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			ToTGS = c1.ToTGS();
			oos.writeObject(ToTGS);
			HashMap<String, String> FromTGS = (HashMap<String, String>) ois.readObject();
			String tgs_Prelude = FromTGS.get("Prelude");
			while (!tgs_Prelude.equals(Appoint_Prelude.TGS_C)) {
				oos.writeObject(ToTGS);
			}
			c1.FormTGS(FromTGS);
			//System.out.println("over");
			socket.close();
			
			HashMap<String, String> ToV = new HashMap<String, String>();
			if(V == "chatV") {
				socket = new Socket(chatV_IP, chatVport);
				os = socket.getOutputStream();
				oos = new ObjectOutputStream(os);
				ToV = c1.ToV();
				oos.writeObject(ToV);
				is = socket.getInputStream();
				ois = new ObjectInputStream(is);
				HashMap<String,String> FromV = (HashMap<String, String>) ois.readObject();
				String v_Prelude = FromV.get("Prelude");
				while(!v_Prelude.equals(Appoint_Prelude.V_C)) {
					oos.writeObject(ToV);
				}
				c1.FromV(FromV);
				String V_Prelude = FromV.get("Prelude");
				if(V_Prelude.equals(Appoint_Prelude.V_C)) {
					Appoint_Client.state[my] = 1;
					Cchatthread init = new Cchatthread(chatVport,my,"in",MyName);
					Cchatthread out = new Cchatthread(chatVport,my,"out",MyName);
					init.start();
					out.start();
				}
			}
			else if(V == "FTP") {
				socket = new Socket(FTP_IP, FTPport);
				os = socket.getOutputStream();
				oos = new ObjectOutputStream(os);
				ToV = c1.ToV();
				oos.writeObject(ToV);
				is = socket.getInputStream();
				ois = new ObjectInputStream(is);
				HashMap<String,String> FromV = (HashMap<String, String>) ois.readObject();
				String v_Prelude = FromV.get("Prelude");
				while(!v_Prelude.equals(Appoint_Prelude.V_C)) {
					oos.writeObject(ToV);
				}
				c1.FromV(FromV);
				String V_Prelude = FromV.get("Prelude");
				if(V_Prelude.equals(Appoint_Prelude.V_C)) {
					System.out.println("收到V-》C");
		        	Cftpthread upload = new Cftpthread(FTPport,"upload");//上传
		        	Cftpthread download = new Cftpthread(FTPport,"download");//下载 
		        	upload.start();
		        	download.start();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
*/