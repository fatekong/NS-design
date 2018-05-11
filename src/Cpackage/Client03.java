package Cpackage;
import java.net.*;
import java.io.IOException;  
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
public class Client03 {
	static final int myport = 10010;
	static final String chatV_IP = "127.0.0.1";
	public static final String My_IP = "127.0.0.1";
	static final String MyName = "ÎÄ¹·";
	//static final int[] state = {0,0,0,0};
	static final int my = 2;
	@SuppressWarnings("resource")
	public static void main(String args[]) throws ClassNotFoundException {
		try {
			Socket socket = new Socket(chatV_IP, myport);
			System.out.println("connected.");
			OutputStream os = socket.getOutputStream();
	        ObjectOutputStream oos = new ObjectOutputStream(os);
			InputStream is = socket.getInputStream();
	        ObjectInputStream ois = new ObjectInputStream(is);
			HashMap<String,String> TochatV = new HashMap<String,String>();
			TochatV.put("Prelude", Appoint_Prelude.C_V);
			oos.writeObject(TochatV);
			System.out.println("send.");
			@SuppressWarnings("unchecked")
			HashMap<String , String> fromchatV = (HashMap<String , String>)ois.readObject();
			String prelude = fromchatV.get("Prelude");
			System.out.println("V:"+prelude);
			if(prelude.equals(Appoint_Prelude.V_C)) {
					Appoint_Client.state[my] = 1;
				Cchatthread init = new Cchatthread(myport,my,"in",MyName);
				Cchatthread out = new Cchatthread(myport,my,"out",MyName);
				init.start();
				out.start();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
