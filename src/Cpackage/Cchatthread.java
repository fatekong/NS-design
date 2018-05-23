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

	public Cchatthread(int port, int m, String f, String u) {
		this.my = m;
		this.portnum = port;
		this.portnum_in = port + 1100;
		this.portnum_out = port + 1000;
		this.flag = f;
		this.USER = u;
	}

	public void run() {
		if (flag.equals("in")) {
			try {
				ServerSocket Servers = new ServerSocket(portnum_in);
				Socket Sockets = Servers.accept();
				System.out.println("in连接成功！");
				InputStream in = Sockets.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(in);
				while (true) {
					@SuppressWarnings("unchecked")
					HashMap<String, String> fromclient = (HashMap<String, String>) ois.readObject();
					System.out.println("接收到信息");
					String Prelude = fromclient.get("Prelude");
					System.out.println("Prelude:" + Prelude);
					if (Prelude.equals(Appoint_Prelude.V_C_chat)) {
						String Time_beforeDES = fromclient.get("time");
						String User_beforeDES = fromclient.get("user");
						String Conv_beforeDES = fromclient.get("conv");
						DES des =new DES();
						String Time = "";
						String User = "";
						String Conv = "";
						if(my == 0) {
							Time = des.decode(Time_beforeDES, Client01.kcv);
							User = des.decode(User_beforeDES, Client01.kcv);
							Conv = des.decode(Conv_beforeDES, Client01.kcv);
						}
						else if(my == 1) {
							
						}
						else if(my == 2) {
							
						}
						else if(my == 3) {
							
						}
						System.out.println("In:" + Time + "-" + User + ":" + Conv);
					} else if (Prelude.equals(Appoint_Prelude.V_C_chatcut)) {
						Servers.close();
						Appoint_Client.state[my] = 0;
						break;
					}
					Thread.sleep(1000);
				}
			} catch (IOException | ClassNotFoundException | InterruptedException e) {
				System.out.println("错误in");
			}
		} else if (flag.equals("out")) {
			try {
				Socket socket = new Socket(Client01.chatV_IP, portnum_out);
				System.out.println("out连接成功！");
				OutputStream out = socket.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(out);
				int i = 0;
				while (true) {
					if (Appoint_Client.state[my] == 0) {
						socket.close();
						break;
					}
					System.out.println("send。。。");
					HashMap<String, String> toclient = new HashMap<String, String>();
					toclient.put("Prelude", Appoint_Prelude.C_V_chat);// 断开后信息也是从此发出给服务器
					Calendar c = Calendar.getInstance();
					int month = c.get(Calendar.MONTH);
					int date = c.get(Calendar.DATE);
					int hour = c.get(Calendar.HOUR_OF_DAY);
					int minute = c.get(Calendar.MINUTE);
					String time = month + "月" + date + "日" + hour + "时" + minute + "分";
					String CONV = "";
					String user = "";
					String Time = "";
					String Hash = "";
					String Sect = "";
					DES des = new DES();
					MyHash myHash = new MyHash();
					if(my == 0) {
						Time = des.encode(time, Client01.kcv);
						user = des.encode(USER, Client01.kcv);
						CONV = des.encode("我是萝莉控", Client01.kcv);
						Transfer transfer = new Transfer(Client01.N);
						Sect = transfer.TransToSec(Client01.MyID, Client01.D);
						Sect = des.encode(Sect, Client01.kcv);
						Hash = myHash.MD5(Client01.MyID);
					}
					/*else if (my == 3) {
						des.encode(time, Client04.kcv);
						des.encode(USER, Client04.kcv);
						CONV = des.encode("我是萝莉控", Client04.kcv);
					}
					else if (my == 2) {
						des.encode(time, Client03.kcv);
						des.encode(USER, Client03.kcv);
						CONV = des.encode("狗一样的男人", Client03.kcv);
					}
					else if (my == 1) {
						des.encode(time, Client02.kcv);
						des.encode(USER, Client02.kcv);
						CONV = des.encode("地大杜兰特", Client02.kcv);
					}*/
					toclient.put("time", Time);
					toclient.put("user", user);
					toclient.put("conv", CONV);		
					toclient.put("sect", Sect);
					toclient.put("hash", Hash);
					oos.writeObject(toclient);
					Thread.sleep(5000);
					i++;
				}

			} catch (IOException | InterruptedException e) {
				System.out.println("错误out");
			}
		}
	}
}
