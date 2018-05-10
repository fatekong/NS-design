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
	public static final String AS_IP = "127.0.0.1";
	public static final String TGS_IP = "127.0.0.1";
	public static final String V1_IP = "127.0.0.1";
	public static final String V2_IP = "127.0.0.1";
	public static final String My_IP = "127.0.0.1";
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws ClassNotFoundException {
		try {
			 Socket socket = new Socket(AS_IP, ASport);  
			 HashMap<String,String> ToAs = new HashMap<String,String>();
			 ToAs.put("Prelude", "000100000000");
			 ToAs.put("IDc","001");
			 ToAs.put("IDtgs","010");
			 Calendar c = Calendar.getInstance(); 
			 int month = c.get(Calendar.MONTH);   
			 int date = c.get(Calendar.DATE);    
			 int hour = c.get(Calendar.HOUR_OF_DAY);
			 int minute = c.get(Calendar.MINUTE);
			 String TS1 = month + "-" + date + "-" + hour + "-" +minute;
			 ToAs.put("TS1",TS1);
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
	         socket = new Socket(TGS_IP,TGSport);
	         HashMap<String,String> ToTGS = new HashMap<String,String>();
	         ToTGS.put("Prelude", "001000000000");
	         ToTGS.put("IDv","chat");
	         ToTGS.put("Ticket(tgs)","xxx");
	         ToTGS.put("Authenticator", "yyy");
	         os = socket.getOutputStream();
	         oos = new ObjectOutputStream(os);
	         oos.writeObject(ToTGS);
	         is = socket.getInputStream();
	         ois = new ObjectInputStream(is);
	         HashMap<String ,String> FromTGS = (HashMap<String, String>) ois.readObject();
	         String tgs_Prelude = FromTGS.get("Prelude");
	         System.out.println(tgs_Prelude);
	         socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
