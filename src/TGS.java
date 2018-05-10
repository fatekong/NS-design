import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
class TGSThread extends Thread{
	public static final String SERVER_IP = "127.0.0.1";
	int portnum;
	public TGSThread(int num) {
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
				System.out.println("portnum:"+portnum);
				HashMap<String,String> toclient = new HashMap<String,String>();
				String Prelude = fromclient.get("Prelude");
				System.out.println("Prelude:"+Prelude);
				Calendar c = Calendar.getInstance(); 
				int month = c.get(Calendar.MONTH);
				int date = c.get(Calendar.DATE);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);
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
						toclient.put("error", "³¬Ê±");
						oos.writeObject(toclient);
					}
				}
				else {
					toclient.put("Prelude", "010010000000");
					toclient.put("error", "³¬Ê±");
					oos.writeObject(toclient);
				}*/
				}
				//Servers.close();
			}catch(IOException | ClassNotFoundException e) {
				System.out.println("´íÎó");
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
