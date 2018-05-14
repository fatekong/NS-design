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
				InputStream in = Sockets.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(in);
				while (true) {
					@SuppressWarnings("unchecked")
					HashMap<String, String> fromclient = (HashMap<String, String>) ois.readObject();
					String Prelude = fromclient.get("Prelude");
					System.out.println("Prelude:" + Prelude);
					if (Prelude.equals(Appoint_Prelude.V_C_chat)) {
						String Time = fromclient.get("time");
						String User = fromclient.get("user");
						String Conv = fromclient.get("conv");
						System.out.println("In:" + Time + "-" + User + ":" + Conv);
					} else if (Prelude.equals(Appoint_Prelude.V_C_chatcut)) {
						Servers.close();
						Appoint_Client.state[my] = 0;
						break;
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("错误");
			}
		} else if (flag.equals("out")) {
			try {
				Socket socket = new Socket(Client04.chatV_IP, portnum_out);
				OutputStream out = socket.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(out);
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
					toclient.put("time", time);
					toclient.put("user", USER);
					if (my == 3)
						toclient.put("conv", "我是萝莉控");
					else if (my == 2)
						toclient.put("conv", "狗一样的男人");
					oos.writeObject(toclient);
					Thread.sleep(3000);
				}

			} catch (IOException | InterruptedException e) {
				System.out.println("错误");
			}
		}
	}
}
