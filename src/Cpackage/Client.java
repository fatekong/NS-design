package Cpackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.HashMap;


public class Client {
	//private SW sw;
	//private TXT txt;
	private static Select slt;
	private int ASport = 10000;
	private int TGSport = 10001;
	private int chatVport = 10008;
	private int FTPport = 10012;
	private String AS_IP = "192.168.0.144";
	private String TGS_IP = "192.168.0.144";
	private String chatV_IP = "";
	private String FTP_IP = "";
	public static String V = "";
	public static boolean signforchoose = false;
	//public static boolean Csign = false;
	public void SetCIPaddress(String s) {
		if (!s.equals(""))
			chatV_IP = s;
	}
	public void SetFIPaddress(String s) {
		if (!s.equals(""))
			FTP_IP = s;
	}
	@SuppressWarnings("unchecked")
	public void RunchatV(String V) throws InterruptedException, IOException, ClassNotFoundException {
		Client01.sw = new SW();
		while (Client01.sign == false) {
			Thread.sleep(1000);
		}
		System.out.println("连接按钮");
		// String V = "FTP";
		Client01 c1 = new Client01(V);
		SetCIPaddress(Client01.sw.GetIPaddress());
		c1.SetUserName(Client01.sw.GetUserName());
		Cchatthread init = null;
		Cchatthread out = null;
		int for_thread_sign = 0;
		while (true) {
			while (!Client01.break_internet) {
				Thread.sleep(500);
				if (Client01.thread_sign == false && for_thread_sign == 0) {
					init.breaksoc();
					out.breaksoc();
					for_thread_sign = 1;
					System.out.println("断开连接");
				}
			}
			Client01.break_internet = false;
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
			Client01.sw.SetInfo("AS连接成功");
			while (!as_Prelude.equals(Appoint_Prelude.AS_C)) {
				Client01.sw.SetInfo(FromAS.get("error"));
				ToAS = c1.ToAS();
				oos.writeObject(ToAS);
				FromAS = (HashMap<String, String>) ois.readObject();
				as_Prelude = FromAS.get("Prelude");
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
			System.out.println(tgs_Prelude);
			while (!tgs_Prelude.equals(Appoint_Prelude.TGS_C)) {
				Client01.sw.SetInfo(FromTGS.get("error"));
				ToAS = c1.ToTGS();
				oos.writeObject(ToTGS);
				FromTGS = (HashMap<String, String>) ois.readObject();
				tgs_Prelude = FromTGS.get("Prelude");
			}
			c1.FormTGS(FromTGS);
			// System.out.println("over");
			socket.close();
			Client01.sw.SetInfo("TGS连接成功");
			HashMap<String, String> ToV = new HashMap<String, String>();
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
				Client01.sw.SetInfo("V连接成功");
				Appoint_Client.state[c1.my] = 1;
				init = new Cchatthread(chatVport, c1.my, "in", c1.MyName,chatV_IP);
				out = new Cchatthread(chatVport, c1.my, "out", c1.MyName,chatV_IP);
				System.out.println("开启线程");
				init.start();
				out.start();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void RunFTP(String V) throws ClassNotFoundException, InterruptedException {
		try {
			Client01.txt = new TXT();
//			String V = "FTP";
			while (Client01.sign == false) {
				Thread.sleep(1000);
			}
			System.out.println("连接按钮");
			// String V = "FTP";
			Client01 c1 = new Client01(V);
			SetFIPaddress(Client01.txt.GetIPaddress());
			//while (true) {
				while (!Client01.break_internet) {
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
						Appoint_Client.state[Client01.my] = 1;
						Cchatthread init = new Cchatthread(chatVport, Client01.my, "in", Client01.MyName , FTP_IP);
						Cchatthread out = new Cchatthread(chatVport, Client01.my, "out", Client01.MyName , FTP_IP);
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
							Cftpthread upload = new Cftpthread(FTPport, "upload",FTP_IP);// 上传
							upload.start();
						}
						if(Client01.ff == 1)
						{
							Cftpthread download = new Cftpthread(FTPport,"download",FTP_IP);//下载
							download.start();
						}
					}
				}
			//}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, IOException {
		slt = new Select();
		Client c = new Client();
		while(!signforchoose) {
			Thread.sleep(500);
		}
		System.out.println("出来");
		if(V.equals("chatV")) {
			c.RunchatV(V);
		}
		else if(V.equals("FTP")) {
			c.RunFTP(V);
		}
	}
}
