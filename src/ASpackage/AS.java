package ASpackage;
import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class ASThread extends Thread{
	//public static final String SERVER_IP = "127.0.0.1";
	int portnum;
	String ADc="";
	static String kc = "12345678";
	String kctgs = "";
	static String ktgs = "bbbbbbbb";
	String IDtgs = "TGS";
	String lifetime2 = "2";
	Socket Sockets = null;
	//ASshow asshow;
	public ASThread(Socket Socket) {
		Sockets = Socket;
	}
	public void run() {
			try {
				//ServerSocket Servers = new ServerSocket(portnum);
				//while(true) {
				//Socket Sockets = Servers.accept();
				ADc = Sockets.getInetAddress().toString();
				String[] ip = ADc.split("/");
				ADc = ip[1];
				System.out.println("ADc"+ADc);
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
				AS.ashow.SetTex("***********resive***********\n");
				AS.ashow.SetTex("TS1：" + TS1 +"\n");
				Calendar c = Calendar.getInstance(); 
				int month = c.get(Calendar.MONTH);
				int date = c.get(Calendar.DATE);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);
//				System.out.println(TS1);
				String[] ts = TS1.split("\\.");
				String IDc=fromclient.get("IDc");
				AS.ashow.SetTex("IDc：" + IDc +"\n");
				AS.ashow.SetTex("IDtgs："+IDtgs+"\n");
				System.out.println(fromclient);
				System.out.println(ts[0]);
				System.out.println(ts[1]);
				System.out.println(ts[2]);
				System.out.println("hhhhhhhh");
				if(month == Integer.parseInt(ts[0]) && date == Integer.parseInt(ts[1]) && hour == Integer.parseInt(ts[2])) {
					if(minute - Integer.parseInt(ts[3])<1) {
						AS.ashow.SetTex("***********resive***********\n");
						AS.ashow.SetTex("Prelude："+Appoint_Prelude.AS_C +"\n");
						toclient.put("Prelude", Appoint_Prelude.AS_C);
						kctgs = des.Main_key();
						//kctgs = "v7377777";
						String des_kctgs = des.encode(kctgs, kc);
						AS.ashow.SetTex("kctgs："+ des_kctgs +"\n");
						
						toclient.put("des_kctgs", des_kctgs);//
						String des_IDtgs=des.encode(IDtgs, kc);
						
						AS.ashow.SetTex("IDtgs："+ des_IDtgs +"\n");
						toclient.put("des_IDtgs",des_IDtgs);//
						
						String TS2 = month + "." + date + "." + hour + "." +minute;
						String des_TS2=des.encode(TS2, kc);
						
						AS.ashow.SetTex("TS2："+ des_TS2 +"\n");
						String ticket_to_tgs_before= kctgs + "-" + IDc + "-" + ADc + "-" + "TGS" + "-" + TS2 + "-" + "2" + "-";
						System.out.println(ticket_to_tgs_before);
						AS.ashow.SetTex("ticket_to_tgs_before：" + ticket_to_tgs_before + "\n");
						String des_Lifetime2=des.encode(lifetime2, kc);
						AS.ashow.SetTex("Lifetime2："+ des_Lifetime2 +"\n");
						toclient.put("des_LifeTime2", des_Lifetime2);//
						
						//System.out.println("Ticket:" + ticket_to_tgs);
						byte[] aaa= ticket_to_tgs_before.getBytes("UTF8");//明文二进制
						byte[] bbb = des.encode_b(aaa, ktgs);//第一次加密后的票据密文二进制
						byte[] ccc = des.encode_b(bbb, kc);//第二次加密后的密文二进制
						AS.ashow.SetTex("Tickettgs："+ ticket_to_tgs +"\n");
						String temp = new String(ccc,"ISO_8859_1");//第二次加密的密文String
						temp.trim();
						
						byte[] kkk = temp.getBytes("ISO_8859_1");//二次加密后的密文二进制（等同于ccc）
						byte[] mmm = des.decode_b(kkk, kc);//第一次解密后的密文二进制
						String temp11 = new String(mmm,"ISO_8859_1");//第一次解密后的密文String
						temp11.trim();
						byte[] kkk11 = temp11.getBytes("ISO_8859_1");//第一次解密后的密文二进制
						byte[] sss = des.decode_b(kkk11, ktgs);//第二次解密后的密文二级制（明文二进制）
						String ttt = new String(sss,"UTF8");//第二次解密后的明文String
						ttt.trim();
						System.out.println("第二次解密：" + ttt);
						toclient.put("ticket_to_tgs_before", temp);
						
						toclient.put("TS2", des_TS2);//
						//DataOutputStream out = new DataOutputStream(Sockets.getOutputStream());
						oos.writeObject(toclient);
						//out.write(ccc);
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
				//}
				//Servers.close();
			}catch(IOException | ClassNotFoundException e) {
				System.out.println("错误");
			}
	}
}
public class AS {
	@SuppressWarnings("resource")
	public static ASshow ashow;
	public static boolean sign = false;
	@SuppressWarnings("resource")
	public void Run() throws IOException{
		ServerSocket Server = null;
		@SuppressWarnings("unused")
		Socket Socket = null;
		Server = new ServerSocket(10000);
		Executor executor = Executors.newFixedThreadPool(4);
		while(true) {
			Socket = Server.accept();
			//ASThread forclient1 = new ASThread(10000);
			//forclient1.start();
			executor.execute(new ASThread(Socket));

		}
	}
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException  {
		ServerSocket Server = null;
		Socket Socket = null;
		ashow = new ASshow();
		while(sign != true) {
			Thread.sleep(1000);
		}
		Server = new ServerSocket(10000);
		Executor executor = Executors.newFixedThreadPool(4);
		while(true) {
			Socket = Server.accept();
			//ASThread forclient1 = new ASThread(10000);
			//forclient1.start();
			executor.execute(new ASThread(Socket));

		}
		
		/*ASThread forclient2 = new ASThread(10001);
		ASThread forclient3 = new ASThread(10002);
		ASThread forclient4 = new ASThread(10003);
		

		forclient1.start();
		forclient2.start();
		forclient3.start();
		forclient4.start();*/
	}
}
