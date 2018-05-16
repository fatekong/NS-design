package FTPpackage;

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
	String model = "";// upload是上传，download是下载

	public VFTPthread(String m, int port) {
		this.portnum = port;
		this.model = m;
		this.portnum_in = port + 1000;
		this.portnum_out = port + 1100;
	}

	@SuppressWarnings({ "unchecked", "resource" })
	public void run() {
		if(model.equals("download")) {
			try {
				String filepath = FTP.FilePath;
				ServerSocket Servers = new ServerSocket(portnum_in);
				Socket Sockets = Servers.accept();
				InputStream is = Sockets.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				HashMap<String,String> formclient = (HashMap<String, String>) ois.readObject();
				String Prelude = formclient.get("Prelude");
				String filename = "";
				if(Prelude.equals(Appoint_Prelude.C_V_ftp_name)) {
					filename = formclient.get("FileName");
					File file = new File(filepath + File.separatorChar + filename);
					HashMap<String, String> toclient = new HashMap<String, String>();
					toclient.put("Prelude", Appoint_Prelude.V_C_ftp_upload);
					OutputStream os = Sockets.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(os);
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
						if(formclient.get("Prelude").equals(Appoint_Prelude.C_V_ftp_file) && formclient.get("Flag").equals("continue")) {
							Toclient.put("Prelude", Appoint_Prelude.V_C_ftp_file);
							System.out.println("recv file...");
							length = dis.read(bytes);
							fos.write(bytes, 0, length);  
							oos.writeObject(Toclient);
		                    fos.flush();
						}
						else if(formclient.get("Prelude").equals(Appoint_Prelude.C_V_ftp_file) && formclient.get("Flag").equals("over")) {
							formclient.clear();
							break;
						}
						else {
							Toclient.put("Prelude", Appoint_Prelude.V_C_ftp_upload);
							oos.writeObject(Toclient);
						}
						Toclient.clear();
					}
					fos.flush();
					fos.close();
				}
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(model.equals("upload")) {
			try {
				String filepath = FTP.FilePath;
				ServerSocket Servers = new ServerSocket(portnum_out);
				Socket Sockets = Servers.accept();
				InputStream is = Sockets.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
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
					toclient.put("FilesName", FilesName);
					OutputStream os = Sockets.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(os);
					oos.writeObject(toclient);
					fromclient = (HashMap<String, String>) ois.readObject();
					System.out.println("首部信息：" + fromclient.get("Prelude") + "," + "文件信息：" + fromclient.get("FileName"));
					if(fromclient.get("Prelude").equals(Appoint_Prelude.C_V_ftp_name)) {
						filepath = filepath + "\\" + fromclient.get("FileName");
						System.out.println(filepath);
						byte[] bytes = new byte[1024];
		                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filepath)));
		                DataOutputStream dos = new DataOutputStream(Sockets.getOutputStream());
		                int sign = 0;
		                while(true) {	
		                	System.out.println("ois.readObject()");
		                	HashMap<String,String> Toclient = new HashMap<String,String>();
		                	if(fromclient.get("Prelude").equals(Appoint_Prelude.V_C_ftp_file) || sign == 0) {
		                		int read = 0;
		                		read = dis.read(bytes);
		                		if(read == -1) {
		                			System.out.println("over");
			                		Toclient.put("Prelude", Appoint_Prelude.V_C_ftp_file);
			                		Toclient.put("Flag","over");
			                		oos.writeObject(Toclient);
			                		Toclient.clear();
			                		break;
			                	}
			                	else{
			                		System.out.println("send file...");
			                		Toclient.put("Prelude", Appoint_Prelude.V_C_ftp_file);
			                		Toclient.put("Flag","continue");
			                		oos.writeObject(Toclient);
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
				}
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}}
