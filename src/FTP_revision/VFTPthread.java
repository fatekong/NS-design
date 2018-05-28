package FTP_revision;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;



public class VFTPthread extends Thread {
	int portnum;
	int portnum_in;
	int portnum_out;
	ServerSocket Servers;
	Socket Sockets = null;
	String model = "";// upload是上传，download是下载
	String MY_IP = "";
	private static OutputStream os = null;
	private static ObjectOutputStream oos = null;
	private static InputStream is = null;
	private static ObjectInputStream ois = null;
	
	//int my ;
	public VFTPthread(int port ,String IP, String m) {
		this.portnum = port;
		this.model = m;
		this.portnum_in = port + 1000;
		this.portnum_out = port + 1100;
		MY_IP = IP;
		/*if(port == 10012)
			my = 0;
		else if(port == 10013)
			my = 1;
		else if(port == 10014)
			my = 2;
		else if(port == 10015)
			my = 3;*/
	}

	public void Break() {
		try {
			Sockets.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings({ "unchecked", "resource" })
	public void run() {
		if(model.equals("download")) {
			try {
				String filepath = FTP.FilePath;
				ServerSocket Server = new ServerSocket(portnum_in);
				Sockets = Server.accept();
				Server.close();
				is = Sockets.getInputStream();
				ois = new ObjectInputStream(is);
				HashMap<String,String> formclient = (HashMap<String, String>) ois.readObject();
				String Prelude = formclient.get("Prelude");
				String filename = "";
				if(Prelude.equals(Appoint_Prelude.C_V_ftp_name)) {
					filename = formclient.get("FileName");
					DES des = new DES();
					filename = des.decode(filename, FTP.kcv.get(MY_IP));
					filename = MY_IP + "_" +filename;
					
					File file = new File(filepath + File.separatorChar + filename);
					HashMap<String, String> toclient = new HashMap<String, String>();
					toclient.put("Prelude", Appoint_Prelude.V_C_ftp_upload);
					os = Sockets.getOutputStream();
					oos = new ObjectOutputStream(os);
					oos.writeObject(toclient);
					FileOutputStream fos = new FileOutputStream(file);
					DataInputStream dis = new DataInputStream(Sockets.getInputStream());
					int length = 0;
					byte[] bytes = new byte[1024];
					formclient.clear();
					toclient.clear();
					while(true) {
						formclient = (HashMap<String, String>) ois.readObject();
						HashMap<String, String> Toclient = new HashMap<String, String>();
						System.out.println("等待。。。"+formclient.get("Prelude") + "||" + formclient.get("Flag"));
						FTP.ftpshow.SetTex("等待。。。"+formclient.get("Prelude") + "||" + formclient.get("Flag")+"\n");
						//if(formclient.get("Prelude").equals(Appoint_Prelude.C_V_ftp_file))
							//System.out.println("Prelude没问题" + formclient.get("Prelude") + "," + Appoint_Prelude.C_V_ftp_file);
						//if(formclient.get("Flag").equals("continue"))
							//System.out.println("Flag没问题");
						if(formclient.get("Prelude").equals(Appoint_Prelude.C_V_ftp_file) && formclient.get("Flag").equals("continue")) {
							Toclient.put("Prelude", Appoint_Prelude.V_C_ftp_file);
							System.out.println("recv file...");
							FTP.ftpshow.SetTex("recv file...\n");
							length = dis.read(bytes);
							bytes = des.decode_b(bytes, FTP.kcv.get(MY_IP));
							//String s = formclient.get("s");
							/*String frombytes = new String(bytes);
							frombytes = des.decode(frombytes, FTP.kcv[my]);
							System.out.println(frombytes);
							System.out.println( "*******************************");
							bytes = frombytes.getBytes();
							length = bytes.length;*/
							//byte[] temp = s.getBytes(); 
							fos.write(bytes, 0, length);
							oos.writeObject(Toclient);
		                    fos.flush();
		                    
						}
						else if(formclient.get("Prelude").equals(Appoint_Prelude.C_V_ftp_file) && formclient.get("Flag").equals("over")) {
							System.out.println("结束");
							FTP.ftpshow.SetTex("结束\n");
							formclient.clear();
							break;
						}
						else {
							System.out.println("验证错误");
							FTP.ftpshow.SetTex("验证错误\n");
							Toclient.put("Prelude", Appoint_Prelude.V_C_ftp_upload);
							oos.writeObject(Toclient);
						}
						Toclient.clear();
					}
					fos.flush();
					fos.close();
					//Servers.close();
					Sockets.close();
					System.out.println("结束");
					//FTP.thread_break = false;
					FTP.ftpshow.SetTex("结束\n");
				}
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					//Servers.close();
					Sockets.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		else if(model.equals("upload")) {
			try {
				String filepath = FTP.FilePath;
				ServerSocket Server = new ServerSocket(portnum_out);
				Sockets = Server.accept();
				Server.close();
				is = Sockets.getInputStream();
				ois = new ObjectInputStream(is);
				HashMap<String,String> fromclient = (HashMap<String, String>) ois.readObject();
				String FilesName = "";
				//String filename = "";
				if(fromclient.get("Prelude").equals(Appoint_Prelude.C_V_ftp_download)) {
					HashMap<String, String> toclient = new HashMap<String, String>();
					toclient.put("Prelude", Appoint_Prelude.V_C_ftp_download);
					File dir = new File(filepath);
					String[] fileNames=dir.list();
					for(int i = 0 ; i < fileNames.length ; i++) {
						FilesName += fileNames[i] + "-";
					}
					DES des = new DES();
					System.out.println(FilesName);
					FilesName = des.encode(FilesName, FTP.kcv.get(MY_IP));
					System.out.println(FilesName);
					toclient.put("FilesName", FilesName);
					os = Sockets.getOutputStream();
					oos = new ObjectOutputStream(os);
					oos.writeObject(toclient);
					fromclient = (HashMap<String, String>) ois.readObject();
					FilesName = fromclient.get("FileName");
					FilesName = des.decode(FilesName, FTP.kcv.get(MY_IP));
					System.out.println("首部信息：" + fromclient.get("Prelude") + "," + "文件信息：" + FilesName);
					FTP.ftpshow.SetTex("首部信息：" + fromclient.get("Prelude") + "," + "文件信息：" + FilesName+"\n");
					if(fromclient.get("Prelude").equals(Appoint_Prelude.C_V_ftp_name)) {
						filepath = filepath + "\\" + FilesName;
						System.out.println(filepath);
						FTP.ftpshow.SetTex(filepath+"\n");
						byte[] bytes = new byte[1024];
		                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filepath)));
		                DataOutputStream dos = new DataOutputStream(Sockets.getOutputStream());
		                int sign = 0;
		                while(true) {	
		                	System.out.println("ois.readObject()");
		                	HashMap<String,String> Toclient = new HashMap<String,String>();
		                	if(fromclient.get("Prelude").equals(Appoint_Prelude.C_V_ftp_file) || sign == 0) {
		                		int read = 0;
		                		read = dis.read(bytes);
		                		if(read == -1) {
		                			System.out.println("over");
		                			FTP.ftpshow.SetTex("over\n");
			                		Toclient.put("Prelude", Appoint_Prelude.V_C_ftp_file);
			                		Toclient.put("Flag","over");
			                		//Thread.sleep(1000);
			                		oos.writeObject(Toclient);
			                		Toclient.clear();
			                		break;
			                	}
			                	else{
			                		bytes = des.encode_b(bytes, FTP.kcv.get(MY_IP));
			                		System.out.println("send file...");
			                		FTP.ftpshow.SetTex("send file...\n");
			                		Toclient.put("Prelude", Appoint_Prelude.V_C_ftp_file);
			                		Toclient.put("Flag","continue");
			                		oos.writeObject(Toclient);
			                		System.out.println(Toclient.get("Prelude"));
			                		System.out.println(Toclient.get("Flag"));
				                    dos.write(bytes, 0, read);  
				                    dos.flush();
				                    Toclient.clear();
			                	}
		                	}
		                	else{
		                		Toclient.put("Prelude", Appoint_Prelude.V_C_error);
		                		Toclient.clear();
		                		break;
		                	}
		                	sign ++;
		                	fromclient.clear();
		                	fromclient = (HashMap<String, String>) ois.readObject();
		                }
					}
					//FTP.thread_break = false;
				}
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					Servers.close();
					Sockets.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
}}
