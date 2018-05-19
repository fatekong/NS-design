package Cpackage;
import java.net.*; 
import java.io.IOException;  
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.*;
public class Client02 {
	static final int ASport = 10001;
	static final int TGSport = 10005;
	static final int chatVport = 10009;
	static final int FTPport = 10013;
	static final int my = 0;
	static final String MyName = "杜兰特";
	private static final String AS_IP = "127.0.0.1";
	private static final String TGS_IP = "";
	private static final String FTP_IP = "127.0.0.1";
	private static final String ChatV_IP = "";
	private String IDv = "chatV";
	private String IDtgs = "TGS";
	private String ADc = "china";
	private String MyID = "C1";
	private String kctgs = "asvcwasd";
	private String ktgs = "bbbbbbbb";
	static String kcv = "";
	private String TS4 = "";
	private String TS5 = "";
	private String Ticketv = "";
	static final String filepath = "D:\\TestforNS-design\\NS.txt";
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws ClassNotFoundException {
		try {
			 Socket socket = new Socket(FTP_IP, FTPport);  
			 HashMap<String,String> ToFTP = new HashMap<String,String>();
			 ToFTP.put("Prelude", Appoint_Prelude.C_V);
			// ToFTP.put("FileName","NS.txt");
			 OutputStream os = socket.getOutputStream();
	         ObjectOutputStream oos = new ObjectOutputStream(os);
	         oos.writeObject(ToFTP);
	         InputStream is = socket.getInputStream();
	         ObjectInputStream ois = new ObjectInputStream(is);
	         HashMap<String ,String> FromFTP = (HashMap<String, String>) ois.readObject();
	         //拆包
	         String ftp_Prelude = FromFTP.get("Prelude");
	         if(ftp_Prelude.equals(Appoint_Prelude.V_C)) {
	        	 System.out.println("收到V-》C");
	        	 Cftpthread upload = new Cftpthread(FTPport,"upload");//上传
	        	 Cftpthread download = new Cftpthread(FTPport,"download");//下载 
	        	 upload.start();
	        	 download.start();
	         }
	         socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
