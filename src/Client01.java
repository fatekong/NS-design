import java.net.*; 
import java.io.IOException;  
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.*;
public class Client01 {
	static final int ASport = 10000;
	static final int TGSport = 10004;
	public static final String AS_IP = "192.168.1.125";
	public static final String TGS_IP = "127.0.0.1";
	public static final String chatV_IP = "127.0.0.1";
	public static final String FTPV_IP = "127.0.0.1";
	public static final String My_IP = "127.0.0.1";
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws ClassNotFoundException {
		try {
			 Socket socket = new Socket(AS_IP, ASport);
			 HashMap<String,String> ToAs = new HashMap<String,String>();
			 ToAs.put("Prelude", "ÎÄ¹·Éµ±Æ");
			 OutputStream os = socket.getOutputStream();
	         ObjectOutputStream oos = new ObjectOutputStream(os);
	         oos.writeObject(ToAs);
	         InputStream is = socket.getInputStream();
	         ObjectInputStream ois = new ObjectInputStream(is);
	         HashMap<String ,String> FromAs = (HashMap<String, String>) ois.readObject();
	         //²ð°ü
	         String as_Prelude = FromAs.get("Prelude");
	         System.out.println(as_Prelude);
	         socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
