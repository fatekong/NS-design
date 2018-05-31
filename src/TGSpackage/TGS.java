package TGSpackage;

import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


class TGSThread extends Thread {
	public static final String SERVER_IP = "127.0.0.1";
	int portnum;
	private String kv = "hhhhhhhh";
	private String ktgs = "bbbbbbbb";
	public String IDv;
	public String ticket;
	public String ticket_M;// 解密后的tickets
	public String Prelude;
	public String kctgs;
	public String IDc;
	public String kcv;
	public String cADc;
	private String ADc;
	public String IDtgs;
	public String TS2;
	public String TS3;
	public String TS4;
	public String LifeTime2;
	public String LifeTime4;
	public String Authenticator;
	public String Authenticator_M;// 解密后的Authenticator
	public String checker;// 检票用
	Socket Sockets = null;
	public TGSThread(Socket socket) {
		Sockets = socket;
	}

	public void unpacked(HashMap<String, String> fromclient) throws UnsupportedEncodingException {
		TGS.tgshow.SetTex("******************rescive******************\n解密前：\n");
		Prelude = fromclient.get("Prelude");// 首部
		System.out.println("Prelude:" + Prelude);
		TGS.tgshow.SetTex("首部：" + Prelude + "\n");
		IDv = fromclient.get("IDv");// IDv
		System.out.println("IDv:" + IDv);
		TGS.tgshow.SetTex("IDv：" + IDv + "\n");
		ticket = fromclient.get("Ticket");//
		System.out.println("ticket:" + ticket);
		TGS.tgshow.SetTex("Tickettgs：" + ticket + "\n");
		DES des = new DES();
		//ticket_M = des.decode(ticket, ktgs);
		byte[] kkk11 = ticket.getBytes("ISO_8859_1");
		byte[] sss = des.decode_b(kkk11, ktgs);//第二次解密后的密文二级制（明文二进制）
		ticket_M = new String(sss,"UTF8");
		TGS.tgshow.SetTex("Ticket解密：" + ticket_M + "\n");
		/*
		 * 此处需要使用Etgs对进行ticket解密
		 *
		 */
		String[] ticket_pro = ticket_M.split("-");// 解密后的tickets拆包
		kctgs = ticket_pro[0];// C和TGS之间的钥匙
		//TGS.tgshow.SetTex("kctgs：" + kctgs + "\n");
		System.out.println("kctgs:" + kctgs);
		IDc = ticket_pro[1];// C的ID号
		System.out.println("IDc: "+IDc);
		//TGS.tgshow.SetTex("IDc：" + IDc + "\n");
		cADc = ticket_pro[2];//
		System.out.println("cADc: "+cADc);
		//TGS.tgshow.SetTex("ADc：" + ADc + "\n");
		IDtgs = ticket_pro[3];// TGS的ID号
		System.out.println("IDtgs: "+IDtgs);
		//TGS.tgshow.SetTex("IDtgs：" + IDtgs + "\n");
		TS2 = ticket_pro[4];
		System.out.println("TS2: "+TS2);
		//TGS.tgshow.SetTex("TS2：" + TS2 + "\n");
		LifeTime2 = ticket_pro[5];
		System.out.println("LifeTime2: "+LifeTime2);
		//TGS.tgshow.SetTex("LifeTime2：" + LifeTime2 + "\n");
		
		Authenticator = fromclient.get("Authenticator");
		TGS.tgshow.SetTex("Authenticator解密前：" + Authenticator + "\n");
		System.out.println("Authenticator:" + Authenticator);
		Authenticator_M = des.decode(Authenticator, kctgs);// 解密
		TGS.tgshow.SetTex("Authenticator解密后：" + Authenticator_M + "\n");
		/*
		 * 此处需要使用Kc,tgs对Authenticator进行解密
		 * 
		 */
		String[] Authenticator_pro = Authenticator_M.split("-");// Authenticators拆包
		IDc = Authenticator_pro[0];
		System.out.println("IDc: "+IDc);
		cADc = Authenticator_pro[1];
		System.out.println("cADc: "+cADc);
		TS3 = Authenticator_pro[2];
		System.out.println("TS3: "+TS3);
		TGS.tgshow.SetTex("*****************************************\n");
	}

	public HashMap<String, String> packet() throws UnsupportedEncodingException {
		// 包kcv + IDv + TS4 + Ticketv
		HashMap<String, String> toclient = new HashMap<String, String>();
		TGS.tgshow.SetTex("******************send******************\n");
		DES des = new DES();
		kcv = des.Main_key();// 封包
		System.out.println("Kcv: "+kcv);
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		TS4 = month + "." + date + "." + hour + "." + minute;// 封包
		//TGS.tgshow.SetTex("TS4：" + TS4 + "\n");
		// Ticketv
		LifeTime4 = Integer.parseInt(LifeTime2)+"";
		//ADc = "china";
		String Ticket_beforeDES = kcv + "-" + IDc + "-" + cADc + "-" + IDv + "-" + TS4 + "-" + LifeTime4;
		System.out.println("Ticket: "+Ticket_beforeDES);
		
		String Ticketv = des.encode(Ticket_beforeDES, kv);
		TGS.tgshow.SetTex("Ticketv：" + Ticketv + "\n");
		String kcv_afterDES = des.encode(kcv, kctgs);
		TGS.tgshow.SetTex("kcv：" + kcv + "\n");
		String idv_afterDES = des.encode(IDv, kctgs);
		TGS.tgshow.SetTex("IDv：" + IDv + "\n");
		String TS4_afterDES = des.encode(TS4, kctgs);
		TGS.tgshow.SetTex("加密后TS4：" + TS4_afterDES + "\n");
		//String Tct_afterDES = des.encode(Ticket_beforeDES, kctgs);
		toclient.put("Prelude", Appoint_Prelude.TGS_C);
		toclient.put("K(c,v)", kcv_afterDES);
		toclient.put("IDv", idv_afterDES);
		toclient.put("TS4", TS4_afterDES);
		toclient.put("Ticket", Ticketv);
		TGS.tgshow.SetTex("*****************************************\n");
		return toclient;
	}

	public void run() {
		try {
			//ServerSocket Servers = new ServerSocket(portnum);
			//while (true) {
				//Socket Sockets = Servers.accept();
				ADc = Sockets.getInetAddress().toString();
				String[] ip = ADc.split("/");
				ADc = ip[1];
				OutputStream os = Sockets.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				InputStream in = Sockets.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(in);
				@SuppressWarnings("unchecked")
				HashMap<String, String> fromclient = (HashMap<String, String>) ois.readObject();
				System.out.println("portnum:" + portnum);
				unpacked(fromclient);
				//System.out.println("cADc：" + cADc);
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
					System.out.println("时间正确！");
					if (minute - Integer.parseInt(ts[3]) < 1) {
						if (!ADc.equals(cADc))// 检查首部数据如果有误返回错误信息重新开始监听
						{
							System.out.println("ADc："+ ADc);
							System.out.println("cADc：" + cADc);
							toclient.put("Prelude", Appoint_Prelude.TGS_C_error);
							System.out.println("ADc验证失败！");
							toclient.put("error", "ADc验证失败");
							oos.writeObject(toclient);
							//continue;
						} else if (Integer.parseInt(LifeTime2) != 2)// 检查tickets中的各项数据
						{
							toclient.put("Prelude", Appoint_Prelude.TGS_C_error);
							toclient.put("error", "生存周期超时");
							System.out.println("生存周期超时！");
							oos.writeObject(toclient);
						} else {
							System.out.println("正常发送！");
							toclient = packet();
							oos.writeObject(toclient);
						}
					} else {
						toclient.put("Prelude", Appoint_Prelude.TGS_C_error);
						toclient.put("error", "超时");
						System.out.println("超时！");
						oos.writeObject(toclient);
					}
				} else {
					toclient.put("Prelude", Appoint_Prelude.TGS_C_error);
					toclient.put("error", "超时");
					oos.writeObject(toclient);
				}
			//}
				Sockets.close();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("错误");
		}
	}
}

public class TGS {
	@SuppressWarnings("resource")
	public static boolean sign = false;
	static TGSshow tgshow ;
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException {
		tgshow = new TGSshow();
		ServerSocket Server = null;
		@SuppressWarnings("unused")
		Socket Socket = null;
		while(sign == false) {
			Thread.sleep(1000);
		}
		Server = new ServerSocket(10001);
		Executor executor = Executors.newFixedThreadPool(4);
		while(true) {
			Socket = Server.accept();
			//ASThread forclient1 = new ASThread(10000);
			//forclient1.start();
			executor.execute(new TGSThread(Socket));

		}
		/*TGSThread forclient1 = new TGSThread(10004);
		TGSThread forclient2 = new TGSThread(10005);
		TGSThread forclient3 = new TGSThread(10006);
		TGSThread forclient4 = new TGSThread(10007);
		forclient1.start();
		forclient2.start();
		forclient3.start();
		forclient4.start();*/
	}
}
