import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
import java.io.*;
import java.net.*;
public class Vchatthread extends Thread{
	public static final String SERVER_IP = "127.0.0.1";
	int my = 0;
	String flag = "";
	int portnum_in;
	int portnum_out;
	int portnum;
	public Vchatthread(int port , String f ) {
		this.portnum = port;
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
		this.flag = f;
		this.portnum_in = port + 1000;
		this.portnum_out = port + 1100;
	}
	public void run() {
		if(flag.equals("in")) {
			try {
				ServerSocket Servers = new ServerSocket(portnum_in);
				Socket Sockets = Servers.accept();
				InputStream in =Sockets.getInputStream();
				ObjectInputStream ois =new ObjectInputStream(in);
<<<<<<< HEAD:src/Vchatthread.java
=======
				//@SuppressWarnings("unchecked");
>>>>>>> TCW:src/chatthread.java
				while(true) {
				@SuppressWarnings("unchecked")
				HashMap<String,String> fromclient = (HashMap<String,String>)ois.readObject();
				String Prelude = fromclient.get("Prelude");
				System.out.println("Prelude:"+Prelude);
				if(Prelude.equals(Appoint_Prelude.C_V_chat)) {
					String Time = fromclient.get("time");
					String User = fromclient.get("user");
					String Conv = fromclient.get("conv");
					ChatInfor ci = new ChatInfor();
					ci.SetConv(Conv);
					ci.SetTime(Time);
					ci.SetUser(User);
					chatV.queue.add(ci);
				}
				else if(Prelude.equals(Appoint_Prelude.C_V_chatcut)){
					if(portnum == 10008)
						chatV.state[0] = 0;
					else if(portnum == 10009)
						chatV.state[1] = 0;
					else if(portnum == 10010)
						chatV.state[2] = 0;
					else if(portnum == 10011)
						chatV.state[3] = 0;
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
				ServerSocket Servers = new ServerSocket(portnum_out);
				Socket Sockets = Servers.accept();
				OutputStream out = Sockets.getOutputStream();
				ObjectOutputStream oos =new ObjectOutputStream(out);
				while(true) {
					if(chatV.state[my] == 0) {
						Servers.close();
						break;
					}
					while(chatV.state[my] == 1 && chatV.pre[my] == chatV.queue.size()) {
						Thread.sleep(1);
					}
					HashMap<String , String> toclient = new HashMap<String , String>();
					for(int i = chatV.pre[my] ; i < chatV.queue.size() ; i ++) {
						chatV.pre[my]++;
						toclient.put("Prelude", Appoint_Prelude.V_C_chat);
						toclient.put("time", chatV.queue.get(i).GetTime());
						toclient.put("user", chatV.queue.get(i).GetUser());
						toclient.put("conv", chatV.queue.get(i).GetConv());
						oos.writeObject(toclient);
						Thread.sleep(1);
					}
				}
				
			}catch(IOException | InterruptedException e) {
				System.out.println("´íÎó");
			}
		}
	}
}
