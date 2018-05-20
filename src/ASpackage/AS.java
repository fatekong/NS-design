package ASpackage;
import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;

import ASpackage.DES;
class ASThread extends Thread{
	public static final String SERVER_IP = "127.0.0.1";
	int portnum;
	String ADc="China";
	String kc = "12345678";
	String kctgs = "";
	String ktgs = "bbbbbbbb";
	String IDtgs = "010";
	String lifetime2 = "2";
	public ASThread(int num) {
		portnum = num;
	}
	public void run() {
			try {
				ServerSocket Servers = new ServerSocket(portnum);
				while(true) {
				Socket Sockets = Servers.accept();
				OutputStream os = Sockets.getOutputStream();
				ObjectOutputStream oos =new ObjectOutputStream(os);
				InputStream in =Sockets.getInputStream();
				ObjectInputStream ois =new ObjectInputStream(in);
				@SuppressWarnings("unchecked")
				HashMap<String,String> fromclient = (HashMap<String,String>)ois.readObject();	
				String ticket_to_tgs="";
				DES des = new DES();
				HashMap<String,String> toclient = new HashMap<String,String>();
				String TS1 = fromclient.get("TS1");
				Calendar c = Calendar.getInstance(); 
				int month = c.get(Calendar.MONTH);
				int date = c.get(Calendar.DATE);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);
//				System.out.println(TS1);
				String[] ts = TS1.split("\\.");
				String IDc=fromclient.get("IDc");
				System.out.println(fromclient);
				System.out.println(ts[0]);
				System.out.println(ts[1]);
				System.out.println(ts[2]);
				System.out.println("hhhhhhhh");
				if(month == Integer.parseInt(ts[0]) && date == Integer.parseInt(ts[1]) && hour == Integer.parseInt(ts[2])) {
					if(minute - Integer.parseInt(ts[3])<1) {
						toclient.put("Prelude", Appoint_Prelude.AS_C);
						kctgs = des.Main_key();
						String des_kctgs = des.encode(kctgs, kc);
						toclient.put("des_kctgs", des_kctgs);//
						String des_IDtgs=des.encode(IDtgs, kc);
						toclient.put("des_IDtgs",des_IDtgs);//
						
						String TS2 = month + "." + date + "." + hour + "." +minute;
						String des_TS2=des.encode(TS2, kc);
						
						
						String ticket_to_tgs_before= kctgs + "-" + IDc + "-" + ADc + "-" + "TGS" + "-" + TS2 + "-" + "2";
						
						String des_Lifetime2=des.encode(lifetime2, kc);
						toclient.put("des_LifeTime2", des_Lifetime2);//
						ticket_to_tgs = des.encode_pro(ticket_to_tgs_before, ktgs);
						System.out.println("第一次加密：" + ticket_to_tgs);
						ticket_to_tgs = des.encode_pro(ticket_to_tgs, kc);
						System.out.println("第二次加密：" + ticket_to_tgs);
						
						System.out.println("第一次解密：" + des.decode_pro(ticket_to_tgs, kc));
						System.out.println("第二次解密：" + des.decode_pro(des.decode_pro(ticket_to_tgs, kc), ktgs));
						toclient.put("ticket_to_tgs_before", ticket_to_tgs);
						
						toclient.put("TS2", des_TS2);//
						
						oos.writeObject(toclient);
					}
					else {
						toclient.put("Prelude", Appoint_Prelude.AS_C_error);
						toclient.put("error", "超时");
						oos.writeObject(toclient);
					}
				}
				else {
					toclient.put("Prelude", Appoint_Prelude.AS_C_error);
					toclient.put("error", "超时");
					oos.writeObject(toclient);
				}
				}
				//Servers.close();
			}catch(IOException | ClassNotFoundException e) {
				System.out.println("错误");
			}
	}
}
public class AS {
	public static void main(String[] args) throws IOException {
		ASThread forclient1 = new ASThread(10000);
		ASThread forclient2 = new ASThread(10001);
		ASThread forclient3 = new ASThread(10002);
		ASThread forclient4 = new ASThread(10003);
		forclient1.start();
		forclient2.start();
		forclient3.start();
		forclient4.start();
	}
}
