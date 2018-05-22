import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
class ASThread extends Thread{
	//public static final String SERVER_IP = "127.0.0.1";
	int portnum;
	public String Prelude;
	public String IDtgs;
	public String IDc;
	public String TS1;
	public ASThread(int num) {
		portnum = num;
	}
	public void unpacked(HashMap<String, String> fromclient)
	{
		Prelude = fromclient.get("Prelude");
		System.out.println("Prelude:"+Prelude);
		IDtgs = fromclient.get("IDtgs");
		System.out.println("IDtgs"+IDtgs);
		IDc = fromclient.get("IDc");
		System.out.println("IDc:"+IDc);
		String TS1 = fromclient.get("TS1");
		System.out.println("TS1:"+TS1);
	}
	public void run() {
			try {
				ServerSocket Servers = new ServerSocket(portnum);
				while(true) {
				Socket Sockets = Servers.accept();
				System.out.println("Sockets.getInetAddress() : " + Sockets.getInetAddress());//获取请求服务端的ip地址
				System.out.println("Sockets.getLocalAddress() : " + Sockets.getLocalAddress());
				System.out.println("Sockets.getLocalAddress() : "+Sockets.getLocalAddress().getHostAddress() );
				OutputStream os = Sockets.getOutputStream();
				ObjectOutputStream oos =new ObjectOutputStream(os);
				InputStream in =Sockets.getInputStream();
				ObjectInputStream ois =new ObjectInputStream(in);
				@SuppressWarnings("unchecked")
				HashMap<String,String> fromclient = (HashMap<String,String>)ois.readObject();
				String Prelude = fromclient.get("Prelude");
				System.out.println("Prelude:" + Prelude);
				HashMap<String,String> toclient = new HashMap<String,String>();
				toclient.put("Prelude", "文狗傻逼");
				oos.writeObject(toclient);
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
