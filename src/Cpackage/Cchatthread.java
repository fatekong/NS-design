package Cpackage;
import java.util.Calendar;
import java.util.HashMap;
import java.io.*;
import java.net.*;
public class Cchatthread extends Thread {
	public static final String SERVER_IP = "127.0.0.1";
	int my;
	String flag = "";
	int portnum_in;
	int portnum_out;
	int portnum;
	private String USER = "";
	public Cchatthread(int port , String f , String u) {
		if(port == 10008) {
			my = 0;
		}
		else if(port == 10009) {
			my = 1;
		}
		else if(port == 10010) {
			my = 2;
		}
		else if(port == 10011) {
			my = 3;
		}
		this.portnum = port;
		this.portnum_in = port + 1000;
		this.portnum_out = port + 1100;
		this.flag = f;
		this.USER = u;
	}
	public void run() {
		if(flag.equals("in")) {
			try {
				ServerSocket Servers = new ServerSocket(portnum_in);
				Socket Sockets = Servers.accept();
				InputStream in =Sockets.getInputStream();
				ObjectInputStream ois =new ObjectInputStream(in);
				while(true) {
				@SuppressWarnings("unchecked")
				HashMap<String,String> fromclient = (HashMap<String,String>)ois.readObject();
				String Prelude = fromclient.get("Prelude");
				System.out.println("Prelude:"+Prelude);
				if(Prelude.equals(Appoint_Prelude.V_C_chat)) {
					String Time = fromclient.get("time");
					String User = fromclient.get("user");
					String Conv = fromclient.get("conv");
					System.out.println(Time + "-" + User + ":" + Conv);
				}
				else if(Prelude.equals(Appoint_Prelude.V_C_chatcut)){
					Servers.close();
					break;
				}
				}
			}catch(IOException | ClassNotFoundException e) {
				System.out.println("´íÎó");
			}
		}
		else if(flag.equals("out")){
			try {
				Socket socket = new Socket(Client04.chatV_IP, portnum_out);
				OutputStream out = socket.getOutputStream();
				ObjectOutputStream oos =new ObjectOutputStream(out);
				while(true) {
					if(Client04.state[my] == 0) {
						socket.close();
						break;
					}
					HashMap<String , String> toclient = new HashMap<String , String>();
						toclient.put("Prelude", Appoint_Prelude.C_V_chat);
						Calendar c = Calendar.getInstance(); 
						int month = c.get(Calendar.MONTH);   
						int date = c.get(Calendar.DATE);    
						int hour = c.get(Calendar.HOUR_OF_DAY);
						int minute = c.get(Calendar.MINUTE);
						String time = month + "-" + date + "-" + hour + "-" +minute;
						toclient.put("time", time);
						toclient.put("user", USER);
						toclient.put("conv", "xxxxx");
						oos.writeObject(toclient);
						Thread.sleep(1);
				}
				
			}catch(IOException | InterruptedException e) {
				System.out.println("´íÎó");
			}
		}
	}
}
