package Cpackage;

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
import java.net.UnknownHostException;
import java.util.HashMap;

public class Cftpthread extends Thread {
	int portnum;
	String ftp_IP = "127.0.0.1";
	String model = "";// upload是上传，download是下载
	int portnum_in;
	int portnum_out;

	public Cftpthread(int port, String m) {
		this.portnum = port;
		this.model = m;
		this.portnum_in = port + 1100;
		this.portnum_out = port + 1000;
	}

	@SuppressWarnings({ "unchecked", "resource" })
	public void run() {
		if (model.equals("upload")) {
			try {
				String filepath = "D:\\TestforNS-design\\NS.txt";
				Socket socket = new Socket(ftp_IP, portnum_out);
				HashMap<String, String> ToFTP = new HashMap<String, String>();
				ToFTP.put("Prelude", Appoint_Prelude.C_V_ftp_name);
				ToFTP.put("FileName", "NS.txt");
				OutputStream os = socket.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(ToFTP);
				InputStream is = socket.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				HashMap<String, String> FromFTP = (HashMap<String, String>) ois.readObject();
				// 拆包
				String ftp_Prelude = FromFTP.get("Prelude");
				if(ftp_Prelude.equals(Appoint_Prelude.V_C_ftp_upload)) {
					byte[] bytes = new byte[1024];
	                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filepath)));
	                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	                ToFTP.clear();
	                FromFTP.clear();
	                FromFTP.put("Prelude", Appoint_Prelude.V_C_ftp_file);
	                while(true) {  	
	                	System.out.println("ois.readObject()");
	                	HashMap<String,String> toFTP = new HashMap<String,String>();
	                	if(FromFTP.get("Prelude").equals(Appoint_Prelude.V_C_ftp_file)) {
	                		int read = 0;
	                		read = dis.read(bytes);
	                		if(read == -1) {
	                			System.out.println("over");
		                		toFTP.put("Prelude", Appoint_Prelude.C_V_ftp_file);
		                		toFTP.put("Flag","over");
		                		oos.writeObject(toFTP);
		                		ToFTP.clear();
		                		break;
		                	}
		                	else{
		                		System.out.println("send file...");
		                		toFTP.put("Prelude", Appoint_Prelude.C_V_ftp_file);
		                		toFTP.put("Flag","continue");
		                		oos.writeObject(toFTP);
			                    dos.write(bytes, 0, read);  
			                    dos.flush();
			                    toFTP.clear();
		                	}
	                	}
	                	else{
	                		toFTP.put("Prelude", Appoint_Prelude.V_C_error);
	                		toFTP.clear();
	                		break;
	                	}
	                	FromFTP.clear();
	                	FromFTP = (HashMap<String, String>) ois.readObject();
	                }
				}
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else if(model.equals("download")) {
			try {
				String filepath = "D:\\TestforNS-design";
				Socket Sockets = new Socket(ftp_IP, portnum_in);
				OutputStream os = Sockets.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				HashMap<String,String> toFTP = new HashMap<String,String>();
				toFTP.put("Prelude", Appoint_Prelude.C_V_ftp_download);
				oos.writeObject(toFTP);
				InputStream is = Sockets.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				HashMap<String,String> formFTP = (HashMap<String, String>) ois.readObject();
				String Prelude = formFTP.get("Prelude");
				String filesname = "";
				if(Prelude.equals(Appoint_Prelude.V_C_ftp_download)) {
					filesname = formFTP.get("FilesName");
					String filechoice[] = filesname.split("-");
					for(int i = 0; i<filechoice.length ; i++) {
						System.out.println(filechoice[i]);
					}
					File file = new File(filepath + File.separatorChar + "NS_test.txt");
					HashMap<String,String> toftp = new HashMap<String,String>();
					toftp.put("Prelude", Appoint_Prelude.C_V_ftp_name);
					toftp.put("FileName","NS_test.txt");
					oos.writeObject(toftp);
					FileOutputStream fos = new FileOutputStream(file);
					DataInputStream dis = new DataInputStream(Sockets.getInputStream());
					int length = 0;
					byte[] bytes = new byte[1024];
					while(true) {
						formFTP = (HashMap<String, String>) ois.readObject();
						HashMap<String, String> ToFTP = new HashMap<String, String>();
						System.out.println("等待。。。"+formFTP.get("Prelude") + "||" + formFTP.get("Flag"));
						if(formFTP.get("Prelude").equals(Appoint_Prelude.V_C_ftp_file) && formFTP.get("Flag").equals("continue")) {
							ToFTP.put("Prelude", Appoint_Prelude.C_V_ftp_file);
							System.out.println("recv file...");
							length = dis.read(bytes);
							fos.write(bytes, 0, length);  
							oos.writeObject(ToFTP);
		                    fos.flush();
						}
						else if(formFTP.get("Prelude").equals(Appoint_Prelude.V_C_ftp_file) && formFTP.get("Flag").equals("over")) {
							formFTP.clear();
							break;
						}
						else {
							ToFTP.put("Prelude", Appoint_Prelude.C_V_ftp_download);
							oos.writeObject(ToFTP);
						}
						ToFTP.clear();
					}
					fos.flush();
					fos.close();
				}
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
