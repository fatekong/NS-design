package chatV_revision;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.io.*;
import java.net.*;
import java.math.BigInteger;

public class Vchatthread extends Thread {
	public String SOCKET_IP = "";
	// int my = 0;
	String flag = "";
	int portnum_in;
	int portnum_out;
	int portnum;
	ServerSocket Serv;
	Socket Sock;
	private String Username = "";
	private String MY_RSA_N = "2649289";
	private String MY_RSA_D = "2572355";
	/*
	 * public Vchatthread(int port, String f ) { this.portnum = port; if (port ==
	 * 10008) { my = 0; } else if (port == 10009) { my = 1; } else if (port ==
	 * 10010) { my = 2; } else if (port == 10011) { my = 3; } this.flag = f;
	 * this.portnum_in = port + 1000; this.portnum_out = port + 1100; }
	 */

	public Vchatthread(int port, String f, String IP) {
		this.portnum = port;
		this.flag = f;
		this.portnum_in = port + 1000;
		this.portnum_out = port + 1100;
		this.SOCKET_IP = IP;
	}

	public void run() {
		if (flag.equals("in")) {
			// while (chatV.exit[my]) {
			while (chatV.exit.get(SOCKET_IP).equals("true")) {
				try {
					ServerSocket Servers = new ServerSocket(portnum_in);
					System.out.println("in启动成功");
					chatV.Vshow.SetTex("in启动成功");
					Socket Sockets = Servers.accept();
					Serv = Servers;
					Servers.close();
					Sock = Sockets;
					InputStream in = Sockets.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(in);
					// while (true && chatV.exit[my]) {
					while (true && chatV.exit.get(SOCKET_IP).equals("true")) {
						@SuppressWarnings("unchecked")
						HashMap<String, String> fromclient = (HashMap<String, String>) ois.readObject();
						String Prelude = fromclient.get("Prelude");
						// System.out.println("Prelude:" + Prelude);
						if (Prelude.equals(Appoint_Prelude.C_V_chat)) {
							synchronized (chatV.queue) {
								String Time_beforeDES = fromclient.get("time");
								String User_beforeDES = fromclient.get("user");
								String Conv_beforeDES = fromclient.get("conv");
								String Sect_beforeDES = fromclient.get("sect");
								String Hash = fromclient.get("hash");
								DES des = new DES();
								MyHash mh = new MyHash();
								/*
								 * Transfer transfer = new Transfer(new BigInteger(chatV.RSA_N[my])); String
								 * Time = des.decode(Time_beforeDES, chatV.kcv[my]); String User =
								 * des.decode(User_beforeDES, chatV.kcv[my]); String Conv =
								 * des.decode(Conv_beforeDES, chatV.kcv[my]); String Sect =
								 * des.decode(Sect_beforeDES, chatV.kcv[my]); Sect = transfer.TransToMes(Sect,
								 * new BigInteger(chatV.RSA_E[my]));
								 */

								Transfer transfer = new Transfer(new BigInteger(chatV.RSA_N.get(SOCKET_IP)));
								String Time = des.decode(Time_beforeDES, chatV.kcv.get(SOCKET_IP));
								String User = des.decode(User_beforeDES, chatV.kcv.get(SOCKET_IP));
								if (!chatV.UserName.contains(User) && this.Username == "") {
									synchronized (chatV.UserName) {
										this.Username = User;
										chatV.UserName.add(User);
									}
								}
								String Conv = des.decode(Conv_beforeDES, chatV.kcv.get(SOCKET_IP));
								String Sect = des.decode(Sect_beforeDES, chatV.kcv.get(SOCKET_IP));
								Sect = transfer.TransToMes(Sect, new BigInteger(chatV.RSA_E.get(SOCKET_IP)));
								chatV.Vshow.SetTex("------------in------------");
								if (mh.MD5(Sect).equals(Hash)) {
									System.out.println("数字签名验证通过");
									chatV.Vshow.SetTex("数字签名验证通过");
								}

								else {
									chatV.Vshow.SetTex("数字签名验证失败");
									chatV.exit.put(SOCKET_IP, "false");
									break;
								}

								System.out.println(
										"V:" + Time + "-" + User + ":" + Conv + " size: " + chatV.queue.size());
								chatV.Vshow
										.SetTex("V:" + Time + "-" + User + ":" + Conv + " size: " + chatV.queue.size());
								ChatInfor ci = new ChatInfor();
								ci.SetConv(Conv);
								ci.SetTime(Time);
								ci.SetUser(User);
								
								chatV.queue.add(ci);
								Thread.sleep(500);
							}
						} else if (Prelude.equals(Appoint_Prelude.C_V_chatcut)) {
							chatV.state.put(SOCKET_IP, 0);
							/*
							 * if (portnum == 10008) chatV.state[0] = 0; else if (portnum == 10009)
							 * chatV.state[1] = 0; else if (portnum == 10010) chatV.state[2] = 0; else if
							 * (portnum == 10011) chatV.state[3] = 0;
							 */
							Servers.close();
							Sockets.close();
							break;
						}
					}
					// System.out.println("closed");
				} catch (IOException | ClassNotFoundException | InterruptedException e) {
					System.out.println("错误in");
					chatV.Vshow.SetTex("断开连接");
					try {
						for (int i = 0; i < chatV.UserName.size(); i++) {
							synchronized (chatV.UserName) {
								if (chatV.UserName.get(i).equals(this.Username)) {
									chatV.UserName.remove(i);
									synchronized (chatV.queue) {
										ChatInfor ci = new ChatInfor();
										ci.SetConv("我已退出聊天室！");
										chatV.Vshow.SetTex(Username + "已退出聊天室！");
										Calendar c = Calendar.getInstance();
										int month = c.get(Calendar.MONTH);
										int date = c.get(Calendar.DATE);
										int hour = c.get(Calendar.HOUR_OF_DAY);
										int minute = c.get(Calendar.MINUTE);
										String time = month + "月" + date + "日" + hour + "时" + minute + "分";
										ci.SetTime(time);
										ci.SetUser(Username);
										chatV.queue.add(ci);
									}
									break;
								}
							}
						}
						Serv.close();
						Sock.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// chatV.exit[my] = false;
					chatV.exit.put(SOCKET_IP, "false");
				}
			}
			// System.out.println("in->out");
			Thread.interrupted();
		} else if (flag.equals("out")) {
			// while (chatV.exit[my]) {
			while (chatV.exit.get(SOCKET_IP).equals("true")) {
				try {
					Socket socket = new Socket(SOCKET_IP, portnum_out);
					System.out.println("out连接成功");
					chatV.Vshow.SetTex("out连接成功");
					OutputStream out = socket.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(out);
					// while (true && chatV.exit[my]) {
					while (true && chatV.exit.get(SOCKET_IP).equals("true")) {
						// System.out.println("进来out循环发信息了");
						if (chatV.state.get(SOCKET_IP) == 0) {
							// if (chatV.state[my] == 0) {
							socket.close();
							break;
						}
						// while (chatV.state[my] == 1 && chatV.pre[my] == chatV.queue.size()) {
						while (chatV.state.get(SOCKET_IP) == 1 && chatV.pre.get(SOCKET_IP) == chatV.queue.size()) {
							Thread.sleep(500);
						}

						// System.out.println(portnum + ":" + chatV.pre[my]);
						System.out.println(portnum + ":" + chatV.pre.get(SOCKET_IP));
						DES des = new DES();
						MyHash myHash = new MyHash();
						// for (int i = chatV.pre[my]; i < chatV.queue.size(); i++) {
						int size = chatV.queue.size();
						for (int i = chatV.pre.get(SOCKET_IP); i < size; i++) {
							chatV.Vshow.SetTex("------------out------------");
							// synchronized (chatV.queue) {
							HashMap<String, String> toclient = new HashMap<String, String>();
							// chatV.pre[my]++;
							// int p = chatV.pre.get(SOCKET_IP);
							// chatV.pre.put(SOCKET_IP, p+1);
							// System.out.println(portnum + " " + chatV.pre[my] + " " + i);
							toclient.put("Prelude", Appoint_Prelude.V_C_chat);
							/*
							 * toclient.put("time", des.encode(chatV.queue.get(i).GetTime(),
							 * chatV.kcv[my])); toclient.put("user",
							 * des.encode(chatV.queue.get(i).GetUser(), chatV.kcv[my]));
							 * toclient.put("conv", des.encode(chatV.queue.get(i).GetConv(),
							 * chatV.kcv[my]));
							 */
							Random rand = new Random();  
							
							int random = rand.nextInt(9000) + 1000;
							String randoms = random + "";
							Transfer transfer = new Transfer(new BigInteger(MY_RSA_N));
							String Sect = transfer.TransToSec(randoms, new BigInteger(MY_RSA_D));
							System.out.println("Sect:" + Sect);
							Sect = des.encode(Sect, chatV.kcv.get(SOCKET_IP));
							String Hash = myHash.MD5(randoms);
							
							toclient.put("time", des.encode(chatV.queue.get(i).GetTime(), chatV.kcv.get(SOCKET_IP)));
							toclient.put("user", des.encode(chatV.queue.get(i).GetUser(), chatV.kcv.get(SOCKET_IP)));
							toclient.put("conv", des.encode(chatV.queue.get(i).GetConv(), chatV.kcv.get(SOCKET_IP)));
							toclient.put("sect", Sect);
							System.out.println("SSSSSSSSSSSSSSSSSS:" + Sect);
							toclient.put("hash", Hash);
							String namestring = "";
							for(int index = 0 ; index < chatV.UserName.size() ; index ++) {
								namestring += chatV.UserName.get(index) + "-";
							}
							toclient.put("name", des.encode(namestring, chatV.kcv.get(SOCKET_IP)));
							System.out.println(SOCKET_IP + ":" + chatV.queue.get(i).GetConv());
							chatV.Vshow.SetTex(SOCKET_IP + ":" + chatV.queue.get(i).GetConv());
							oos.writeObject(toclient);
							// System.out.println("1111");
							Thread.sleep(2000);
							System.out.println("------------------------");

							// }
						}
						chatV.pre.put(SOCKET_IP, size);
						// System.out.println("222");
						// }
						// socket.close();
					}
				} catch (IOException | InterruptedException e) {
					System.out.println("错误out");
					chatV.Vshow.SetTex("错误out");
					// chatV.exit[my] = false;
					chatV.exit.put(SOCKET_IP, "false");
				}
			}
			// System.out.println("out->out");
			Thread.interrupted();
		}
		// System.out.println("over...");
	}
}
