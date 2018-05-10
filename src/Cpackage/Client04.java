package Cpackage;
import java.net.*;
import java.io.IOException;  
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import Vpackage.Vchatthread;
public class Client04 {
	static final int chatVport = 10011;
	static final String chatV_IP = "127.0.0.1";
	public static final String My_IP = "127.0.0.1";
	static final String MyName = "Ìð½´";
	static final int[] state = {0,0,0,0};
	public static void main(String args[]) throws ClassNotFoundException {
		try {
			Socket socket = new Socket(chatV_IP, chatVport);
			InputStream in = socket.getInputStream();
			ObjectInputStream ois =new ObjectInputStream(in);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos =new ObjectOutputStream(os);
			HashMap<String,String> TochatV = new HashMap<String,String>();
			TochatV.put("Prelude", Appoint_Prelude.C_V);
			oos.writeObject(TochatV);
			@SuppressWarnings("unchecked")
			HashMap<String , String> fromchatV = (HashMap<String , String>)ois.readObject();
			String prelude = fromchatV.get("Prelude");
			if(prelude.equals(Appoint_Prelude.V_C)) {
				if(chatVport == 10008)
					state[0] = 1;
				else if(chatVport == 10009)
					state[1] = 1;
				else if(chatVport == 10010)
					state[2] = 1;
				else if(chatVport == 10011)
					state[3] = 1;
				Cchatthread init = new Cchatthread(chatVport,"in",MyName);
				Cchatthread out = new Cchatthread(chatVport,"out",MyName);
				init.start();
				out.start();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
