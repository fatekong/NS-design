package Cpackage;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.io.*;
import java.math.BigInteger;
import java.net.*;

public class Cchatthread extends Thread {
	public static final String SERVER_IP = "127.0.0.1";
	//int my;
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
	private String vn = "";
	private String ve = "";
			
	public Cchatthread(int port, String f, String u , String IP) throws IOException {
		//this.my = m;
		this.portnum = port;
		this.portnum_in = port + 1100;
		this.portnum_out = port + 1000;
		this.flag = f;
		this.USER = u;
		ip = IP;
		GetCertificate();
	}
	
	public void GetCertificate() throws IOException {
		String file = "D:\\Certificate\\V.txt";
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		String str = null; 
		String RSA[] = new String[2];
        while((str = br.readLine()) != null) {   
               System.out.println(str);
               RSA = str.split("-");
         } 
         br.close();
         reader.close();
         vn = RSA[0];
         ve = RSA[1];
         System.out.println(vn);
         System.out.println(ve);
	}

	@SuppressWarnings("deprecation")
	public void breaksoc() throws IOException {
		// Ser.close();
		Soc.close();
		this.stop();
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
				//int time = 0;
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
						String Sect_beforeDES = fromclient.get("sect");
						String Hash_beforeDES = fromclient.get("hash");
						System.out.println("SSSSSSSSSSSSSSSSSS:" + Sect_beforeDES);
						DES des = new DES();
						MyHash mh = new MyHash();
						String Time = "";
						String User = "";
						String Conv = "";
						String Name = "";
						// if(my == 0) {
						String Sect = "";
						Sect = des.decode(Sect_beforeDES, Client01.kcv);
						Time = des.decode(Time_beforeDES, Client01.kcv);
						User = des.decode(User_beforeDES, Client01.kcv);
						Conv = des.decode(Conv_beforeDES, Client01.kcv);
						Name = des.decode(Name_beforeDES, Client01.kcv);
						System.out.println("sssssssssssssssssssss:"+Sect);
						Transfer transfer = new Transfer(new BigInteger(vn));
						System.out.println();
						Sect = transfer.TransToMes(Sect, new BigInteger(ve));
						System.out.println(Sect);
						if(mh.MD5(Sect).equals(Hash_beforeDES)) {
							System.out.println(Sect + "数字签名通过！");
							
						}else {
							System.out.println(Sect + "数字签名未通过！");
							break;
						}
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
						//if(time != 0)
						Client01.sw.ClearUser();
						Client01.sw.SetUser(Names);
						//time ++ ;
						System.out.println(Name);
						System.out.println("----------------------------------------------");
					} else if (Prelude.equals(Appoint_Prelude.V_C_chatcut)) {
						// Ser.close();
						Soc.close();
						//Appoint_Client.state[my] = 0;
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
				//int i = 0;
				while (true) {
					/*if (Appoint_Client.state[my] == 0) {
						Soc.close();
						break;
					}*/
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
						
						//改动部分21：56
						Random rand = new Random();  
						int random = rand.nextInt(9000) + 1000;
						String randoms = random + "";
						CONV = des.encode(label, Client01.kcv);
						Transfer transfer = new Transfer(Client01.N);
						Sect = transfer.TransToSec(randoms, Client01.D);
						Sect = des.encode(Sect, Client01.kcv);
						Hash = myHash.MD5(randoms);
						//改动部分21：56
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
					//i++;
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
