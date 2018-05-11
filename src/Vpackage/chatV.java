package Vpackage;
import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
class chatVThread extends Thread{
	public static final String SERVER_IP = "127.0.0.1";
	static final Vector<ChatInfor> queue = new Vector<ChatInfor>();
	static final chatV c = new chatV();
	private static int[] pre = {0,0,0,0};
	int portnum;
	public chatVThread(int num) {
		portnum = num;
	}
	@SuppressWarnings("resource")
	public void run() {
		try {
			System.out.println("run "+portnum);
			ServerSocket Servers = new ServerSocket(portnum);
			while(true) {
			Socket Sockets = Servers.accept();
			System.out.println("accept:"+portnum);
			InputStream in =Sockets.getInputStream();
			ObjectInputStream ois =new ObjectInputStream(in);
			OutputStream os = Sockets.getOutputStream();
			ObjectOutputStream oos =new ObjectOutputStream(os);
			System.out.println("waiting...");
			@SuppressWarnings("unchecked")
			HashMap<String,String> fromclient = (HashMap<String,String>)ois.readObject();
			String prelude = fromclient.get("Prelude");
			System.out.println("C04:"+prelude);
			if(prelude.equals(Appoint_Prelude.C_V)) {
				if(portnum == 10008)
					chatV.state[0] = 1;
				else if(portnum == 10009)
					chatV.state[1] = 1;
				else if(portnum == 10010)
					chatV.state[2] = 1;
				else if(portnum == 10011)
					chatV.state[3] = 1;
				HashMap<String , String> toclient = new HashMap<String , String>();
				toclient.put("Prelude", Appoint_Prelude.V_C);
				oos.writeObject(toclient);
				System.out.println("write_over");
				Vchatthread init = new Vchatthread(portnum,"in");
				Vchatthread out = new Vchatthread(portnum,"out");
				init.start();
				out.start();
				while(chatV.state[3] == 1) {
					Thread.sleep(1);
				}
			}
			}
			//Servers.close();
		}catch(IOException | ClassNotFoundException | InterruptedException e) {
			System.out.println("错误");
		}
	}
}
public class chatV {
	//static final Vector<String> test = new Vector<String>();
	static final int pre[] = {0,0,0,0};
	static final Vector<ChatInfor> queue = new Vector<ChatInfor>();
	static final int state[] = {0,0,0,0};//state各代表4个端口，关闭是0，开启是1。
	public static void main(String[] args) throws IOException {
		chatVThread forclient1 = new chatVThread(10008);
		chatVThread forclient2 = new chatVThread(10009);
		chatVThread forclient3 = new chatVThread(10010);
		chatVThread forclient4 = new chatVThread(10011);
		forclient1.start();
		forclient2.start();
		forclient3.start();
		forclient4.start();
	}
}
