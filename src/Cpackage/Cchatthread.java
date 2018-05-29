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
	private ServerSocket Ser;
	private Socket Soc;
	private ObjectOutputStream oos = null;
	private OutputStream out = null;
	private InputStream in = null;
	private ObjectInputStream ois = null;
	private String ip = "";
	public Cchatthread(int port, int m, String f, String u , String IP) {
		this.my = m;
		this.portnum = port;
		this.portnum_in = port + 1100;
		this.portnum_out = port + 1000;
		this.flag = f;
		this.USER = u;
		ip = IP;
	}

	public void breaksoc() throws IOException {
		// Ser.close();
		Soc.close();
	}

	public void run() {
		if (flag.equals("in")) {
			try {
				Ser = new ServerSocket(portnum_in);
				Soc = Ser.accept();
				Ser.close();
				System.out.println("in连接成功！");
				in = Soc.getInputStream();
				ois = new ObjectInputStream(in);
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
						String Name_beforeDES = fromclient.get("name");
						DES des = new DES();
						String Time = "";
						String User = "";
						String Conv = "";
						String Name = "";
						// if(my == 0) {
						Time = des.decode(Time_beforeDES, Client01.kcv);
						User = des.decode(User_beforeDES, Client01.kcv);
						Conv = des.decode(Conv_beforeDES, Client01.kcv);
						Name = des.decode(Name_beforeDES, Client01.kcv);
						// System.out.println(Name);
						String[] Names = Name.split("-");
						// }
						/*
						 * else if(my == 1) {
						 * 
						 * } else if(my == 2) {
						 * 
						 * } else if(my == 3) {
						 * 
						 * }
						 */
						System.out.println("In:" + Time + "-" + User + ":" + Conv);
						Client01.sw.SetInfo(Time + "-" + User + ":" + Conv);
						Client01.sw.SetUser(Names);
						System.out.println(Name);
						System.out.println("----------------------------------------------");
					} else if (Prelude.equals(Appoint_Prelude.V_C_chatcut)) {
						// Ser.close();
						Soc.close();
						Appoint_Client.state[my] = 0;
						break;
					}
					Thread.sleep(1000);
				}
				// Client01.sw.SetInfo("不能接收");
			} catch (IOException | ClassNotFoundException | InterruptedException e) {
				try {
					//in.close();
					//ois.close();
					Soc.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("错误in");
				Client01.sw.SetInfo("不能接收");
			}
		} else if (flag.equals("out")) {
			try {
				Soc = new Socket(ip, portnum_out);
				System.out.println("out连接成功！");
				out = Soc.getOutputStream();
				oos = new ObjectOutputStream(out);
				int i = 0;
				while (true) {
					if (Appoint_Client.state[my] == 0) {
						Soc.close();
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
					// if (my == 0) {
					Time = des.encode(time, Client01.kcv);
					user = des.encode(USER, Client01.kcv);
					while (!Client01.infor) {
						Thread.sleep(500);
					}
					Client01.infor = false;
					String label = "";
					if (Client01.thread_sign != false) {
						label = Client01.sw.GetInformation();
						//System.out.println("等待输入......");
						Client01.sw.ClearInto();
						Client01.sw.ClearInfor();
						CONV = des.encode(label, Client01.kcv);
						Transfer transfer = new Transfer(Client01.N);
						Sect = transfer.TransToSec(Client01.MyID, Client01.D);
						Sect = des.encode(Sect, Client01.kcv);
						Hash = myHash.MD5(Client01.MyID);
					}
					// }
					/*
					 * else if (my == 3) { des.encode(time, Client04.kcv); des.encode(USER,
					 * Client04.kcv); CONV = des.encode("我是萝莉控", Client04.kcv); } else if (my == 2)
					 * { des.encode(time, Client03.kcv); des.encode(USER, Client03.kcv); CONV =
					 * des.encode("狗一样的男人", Client03.kcv); } else if (my == 1) { des.encode(time,
					 * Client02.kcv); des.encode(USER, Client02.kcv); CONV = des.encode("地大杜兰特",
					 * Client02.kcv); }
					 */

					toclient.put("time", Time);
					toclient.put("user", user);
					toclient.put("conv", CONV);
					toclient.put("sect", Sect);
					toclient.put("hash", Hash);
					oos.writeObject(toclient);
					Thread.sleep(1000);
					i++;
				}
				// Client01.sw.SetInfo("不能发送");
			} catch (IOException | InterruptedException e) {
				
				System.out.println("错误out");
				Client01.sw.SetInfo("不能发送");
				try {
					//out.close();
					//oos.close();
					Soc.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
	}
}
