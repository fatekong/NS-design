import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
class chatVThread extends Thread{
	public static final String SERVER_IP = "127.0.0.1";
	static final Vector<ChatInfor> queue = new Vector<ChatInfor>();
	static final chatV c = new chatV();
	private static int[] pre = {-1,-1,-1,-1};
	int portnum;
	public chatVThread(int num) {
		portnum = num;
	}
	public void run() {
		try {
			ServerSocket Servers = new ServerSocket(portnum);
			while(true) {
			Socket Sockets = Servers.accept();
			InputStream in =Sockets.getInputStream();
			ObjectInputStream ois =new ObjectInputStream(in);
			@SuppressWarnings("unchecked")
			HashMap<String,String> fromclient = (HashMap<String,String>)ois.readObject();
			String prelude = fromclient.get("Prelude");
			if(prelude.equals("001101000000")) {
				if(portnum == 10008)
					chatV.state[0] = 1;
				else if(portnum == 10009)
					chatV.state[1] = 1;
				else if(portnum == 10010)
					chatV.state[2] = 1;
				else if(portnum == 10011)
					chatV.state[3] = 1;
				
			}
			else if(prelude.equals("001111000000")){
				if(portnum == 10008)
					chatV.state[0] = 0;
				else if(portnum == 10009)
					chatV.state[1] = 0;
				else if(portnum == 10010)
					chatV.state[2] = 0;
				else if(portnum == 10011)
					chatV.state[3] = 0;
			}
			
			}
			//Servers.close();
		}catch(IOException | ClassNotFoundException e) {
			System.out.println("����");
		}
	}
}
public class chatV {
	//static final Vector<String> test = new Vector<String>();
	static final int pre[] = {0,0,0,0};
	static final Vector<ChatInfor> queue = new Vector<ChatInfor>();
	static final int state[] = {0,0,0,0};//state������4���˿ڣ��ر���0��������1��
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