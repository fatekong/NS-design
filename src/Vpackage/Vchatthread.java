package Vpackage;

import java.util.HashMap;
import java.io.*;
import java.net.*;
import java.math.BigInteger;

public class Vchatthread extends Thread {
	public static final String SERVER_IP = "127.0.0.1";
	int my = 0;
	String flag = "";
	int portnum_in;
	int portnum_out;
	int portnum;
	ServerSocket Serv;
	Socket Sock;

	public Vchatthread(int port, String f) {
		this.portnum = port;
		if (port == 10008) {
			my = 0;
		} else if (port == 10009) {
			my = 1;
		} else if (port == 10010) {
			my = 2;
		} else if (port == 10011) {
			my = 3;
		}
		this.flag = f;
		this.portnum_in = port + 1000;
		this.portnum_out = port + 1100;
	}

	public void run() {
		if (flag.equals("in")) {
			while (chatV.exit[my]) {
				try {
					ServerSocket Servers = new ServerSocket(portnum_in);
					Socket Sockets = Servers.accept();
					Serv = Servers;
					Sock = Sockets;
					InputStream in = Sockets.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(in);
					while (true && chatV.exit[my]) {
						@SuppressWarnings("unchecked")
						HashMap<String, String> fromclient = (HashMap<String, String>) ois.readObject();
						String Prelude = fromclient.get("Prelude");
						//System.out.println("Prelude:" + Prelude);
						if (Prelude.equals(Appoint_Prelude.C_V_chat)) {
							synchronized (chatV.queue) {
								String Time_beforeDES = fromclient.get("time");
								String User_beforeDES = fromclient.get("user");
								String Conv_beforeDES = fromclient.get("conv");
								String Sect_beforeDES = fromclient.get("sect");
								String Hash = fromclient.get("hash");
								DES des =new DES();
								MyHash mh = new MyHash();
								Transfer transfer = new Transfer(new BigInteger(chatV.RSA_N[my]));
								
								String Time = des.decode(Time_beforeDES, chatV.kcv[my]);
								String User = des.decode(User_beforeDES, chatV.kcv[my]);
								String Conv = des.decode(Conv_beforeDES, chatV.kcv[my]);
								String Sect = des.decode(Sect_beforeDES, chatV.kcv[my]);
								Sect = transfer.TransToMes(Sect, new BigInteger(chatV.RSA_E[my]));
								if(mh.MD5(Sect).equals(Hash))
									System.out.println("数字签名验证通过");
								
								
								
								System.out.println("V:" + Time + "-" + User + ":" + Conv + " size: " + chatV.queue.size());
								ChatInfor ci = new ChatInfor();
								ci.SetConv(Conv);
								ci.SetTime(Time);
								ci.SetUser(User);
								chatV.queue.add(ci);
								Thread.sleep(1000);
							}
						} else if (Prelude.equals(Appoint_Prelude.C_V_chatcut)) {
							if (portnum == 10008)
								chatV.state[0] = 0;
							else if (portnum == 10009)
								chatV.state[1] = 0;
							else if (portnum == 10010)
								chatV.state[2] = 0;
							else if (portnum == 10011)
								chatV.state[3] = 0;
							Servers.close();
							Sockets.close();
							break;
						}
					}
					//System.out.println("closed");
				} catch (IOException | ClassNotFoundException | InterruptedException e) {
					//System.out.println("错误in");
					try {
						Serv.close();
						Sock.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					chatV.exit[my] = false;
				}
			}
			//System.out.println("in->out");
			Thread.interrupted();
		} else if (flag.equals("out")) {
			while (chatV.exit[my]) {
				try {
					Socket socket = new Socket(SERVER_IP, portnum_out);
					OutputStream out = socket.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(out);
					while (true && chatV.exit[my]) {
						if (chatV.state[my] == 0) {
							socket.close();
							break;
						}
						while (chatV.state[my] == 1 && chatV.pre[my] == chatV.queue.size()) {
							Thread.sleep(1000);
						}
						
						System.out.println(portnum + ":" + chatV.pre[my]);
						DES des = new DES();
						for (int i = chatV.pre[my]; i < chatV.queue.size(); i++) {
							synchronized (chatV.queue) {
								HashMap<String, String> toclient = new HashMap<String, String>();
								chatV.pre[my]++;
								//System.out.println(portnum + " " + chatV.pre[my] + " " + i);
								toclient.put("Prelude", Appoint_Prelude.V_C_chat);
								toclient.put("time", des.encode(chatV.queue.get(i).GetTime(), chatV.kcv[my]));
								toclient.put("user", des.encode(chatV.queue.get(i).GetUser(), chatV.kcv[my]));
								toclient.put("conv", des.encode(chatV.queue.get(i).GetConv(), chatV.kcv[my]));
								System.out.println(portnum + ":" + chatV.queue.get(i).GetConv());
								oos.writeObject(toclient);
								//System.out.println("1111");
								Thread.sleep(1000);
								System.out.println("------------------------");
							}
						}
						//System.out.println("222");
					}
					// socket.close();
				} catch (IOException | InterruptedException e) {
					System.out.println("错误out");
					chatV.exit[my] = false;
				}
			}
			//System.out.println("out->out");
			Thread.interrupted();
		}
		//System.out.println("over...");
	}
}
