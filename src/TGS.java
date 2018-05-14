import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
class TGSThread extends Thread{
	public static final String SERVER_IP = "127.0.0.1";
	int portnum;
	public String IDv;
	public String ticket;
	public String ticket_M;//解密后的tickets
	public String Prelude;
	public String kctgs;
	public String IDc;
	public String kcv;
	public String ADc;
	public String IDtgs;
	public String TS2;
	public String TS3;
	public String LifeTime2;
	public String Authenticator;
	public String Authenticator_M;//解密后的Authenticator
	public String checker;//检票用
	public TGSThread(int num) {
		portnum = num;
	}
	public void unpacked(HashMap<String, String> fromclient)
	{
		Prelude = fromclient.get("Prelude");
		System.out.println("Prelude:"+Prelude);
		IDv = fromclient.get("IDv");
		System.out.println("IDv:"+IDv);
		ticket = fromclient.get("Ticket(tgs)");
		System.out.println("ticket:"+ticket);
		/*
		 *此处需要使用Etgs对进行ticket解密
		 *
		 */
		String[] ticket_pro = ticket_M.split("-");//解密后的tickets拆包
		kctgs=ticket_pro[0];
		IDc=ticket_pro[1];
		ADc=ticket_pro[2];
		IDtgs=ticket_pro[3];
		TS2=ticket_pro[4];
		LifeTime2=ticket_pro[5];
		Authenticator = fromclient.get("Authenticator");
		System.out.println("Authenticator:"+Authenticator);
		/*
		 * 此处需要使用Kc,tgs对Authenticator进行解密
		 * 
		 */
		String[] Authenticator_pro = ticket.split("-");//Authenticators拆包
		kctgs=ticket_pro[0];
		IDc=ticket_pro[1];
		ADc=ticket_pro[2];
		TS3=ticket_pro[3];
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
				System.out.println("portnum:"+portnum);
				unpacked(fromclient);

				Calendar c = Calendar.getInstance(); 
<<<<<<< HEAD
				
				//添加了两点注释
=======
>>>>>>> TCW
				int month = c.get(Calendar.MONTH);
				int date = c.get(Calendar.DATE);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);			
				HashMap<String,String> toclient = new HashMap<String,String>();
				toclient.put("Prelude", "100000000000");
				toclient.put("key(c,v)", "12345567");
				toclient.put("IDv", "tgs");
				String TS4 = month + "-" + date + "-" + hour + "-" +minute;
				toclient.put("TS4", TS4);
				toclient.put("ticket(v)", "xxxx");
				oos.writeObject(toclient);
				/*String[] ts = TS1.split("-");
				if(month == Integer.parseInt(ts[0]) && date == Integer.parseInt(ts[1]) && hour == Integer.parseInt(ts[2])) {
					if(minute - Integer.parseInt(ts[3])<1) {
						toclient.put("Prelude", "010000000000");
						toclient.put("key(c,v)", "12345678");
						toclient.put("IDtgs", "010");
						String TS2 = month + "-" + date + "-" + hour + "-" +minute;
						toclient.put("LifeTime2", "7");
						toclient.put("Ticket(tgs)", "hhhh");
						toclient.put("TS2", TS2);
						oos.writeObject(toclient);
					}
					else {
						toclient.put("Prelude", "010010000000");
						toclient.put("error", "超时");
						oos.writeObject(toclient);
					}
				}
				else {
					toclient.put("Prelude", "010010000000");
					toclient.put("error", "超时");
					oos.writeObject(toclient);
				}
					if()//检查首部数据如果有误返回错误信息重新开始监听
					{
						toclient.put("Prelude", "TGS_C");
						toclient.put("error", "首部出错");
						oos.writeObject(toclient);
						continue;
					}
					if()//检查tickets中的各项数据
					{
						toclient.put("Prelude", "TGS_C");
						toclient.put("error", "tickets出错");
						oos.writeObject(toclient);
						continue;//如果有错返回数据重新开始监听
					}*/
				}
				//Servers.close();
			}catch(IOException | ClassNotFoundException e) {
				System.out.println("错误");
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
