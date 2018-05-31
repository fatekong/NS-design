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
	int my;

	//String ftp_IP = "127.0.0.1";
	String model = "";// upload是上传，download是下载
	int portnum_in;
	int portnum_out;
	String IP = "";
	public Cftpthread(int port, String m,String ip) {
		this.portnum = port;
		this.model = m;
		this.portnum_in = port + 1100;
		this.portnum_out = port + 1000;
		if(port == 10012)
			my = 0;
		else if(port == 10013)
			my = 1;
		else if(port == 10014)
			my = 2;
		else if(port == 10015)
			my = 3;
		IP = ip;
	}

	@SuppressWarnings({ "unchecked", "resource" })
	public void run() {
		if (model.equals("upload")) {
			try {
//				Client01.filepath = "D:\\TestforNS-design\\NS.txt";
				Socket socket = new Socket(IP, portnum_out);
				HashMap<String, String> ToFTP = new HashMap<String, String>();
				ToFTP.put("Prelude", Appoint_Prelude.C_V_ftp_name);
				DES des = new DES();
				String FileName = "";
				if(my == 0) 
					FileName = des.encode(Client01.FileName, Client01.kcv);
				/*else if(my == 1)
					FileName = des.encode("NS.txt", Client02.kcv);
				else if(my == 2)
					FileName = des.encode("NS.txt", Client03.kcv);
				else if(my == 3)
					FileName = des.encode("NS.txt", Client04.kcv);*/
				ToFTP.put("FileName", FileName);
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
					File fff = new File(Client01.filepath);
					long lonng = fff.length();
					
	                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(Client01.filepath)));
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
	                		System.out.println("长度："+read);
	                		//String s = new String(bytes);
	                		bytes = des.encode_f(bytes, Client01.kcv);
	                		//String s1 = des.encode(s, Client01.kcv);
	                		//String s2 = des.decode(s1, Client01.kcv);
	                		//System.out.println("文件的内容是  "+s2);
	                		/*String frombytes =  new String(bytes);
	    					if(my == 0) 
	    						frombytes = des.encode(frombytes, Client01.kcv);
	    					/*else if(my == 1)
	    						frombytes = des.encode(frombytes, Client02.kcv);
	    					else if(my == 2)
	    						frombytes = des.encode(frombytes, Client03.kcv);
	    					else if(my == 3)
	    						frombytes = des.encode(frombytes, Client04.kcv);
	    					bytes = frombytes.getBytes();
	    					System.out.println(frombytes);
	    					System.out.println("*******************************************");*/
	                		
	                		if(read == -1) {
	                			System.out.println("over");
		                		toFTP.put("Prelude", Appoint_Prelude.C_V_ftp_file);
		                		toFTP.put("Flag","over");
		                		oos.writeObject(toFTP);
		                		ToFTP.clear();
		                		break;
		                	}
		                	else{
		                		while(true){
		                			if( read%8 == 0)
		                				break;
		                			else
		                				read ++ ;
		                		}
		                		TXT.time1 = (int) (lonng/1024+1);
		                		System.out.println("send file...");
		                		toFTP.put("Prelude", Appoint_Prelude.C_V_ftp_file);
		                		toFTP.put("Flag","continue");
		                		//toFTP.put("Length", read+"");
		                		//toFTP.put("s",s);
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
				String filepath = "D://NS-download";
				Socket Sockets = new Socket(IP, portnum_in);
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
				String FilesLength = "";
				DES des = new DES();
				if(Prelude.equals(Appoint_Prelude.V_C_ftp_download)) {
					filesname = formFTP.get("FilesName");
					FilesLength = formFTP.get("FilesLength");
					filesname = des.decode(filesname, Client01.kcv);
					String filechoice[] = filesname.split("-");
					String lengthchoice[] = FilesLength.split("-");
					for(int i = 0; i<filechoice.length ; i++) {
						System.out.println(filechoice[i]);
					}
					for(int i=0;i<filechoice.length;i++)
					Client01.txt.ta2.addItem(filechoice[i]);
					
					while(Client01.kk == 0)
					{
						Thread.sleep(1000);
					}
					Client01.kk = 0;
					String fnd = Client01.FileName_d;
					int k=0;
					for(k=0;k<filechoice.length;++k)
					{
						if(filechoice[k]==fnd)
						{
							break;
						}
					}
					System.out.println(lengthchoice[k]);
					fnd = des.encode(fnd, Client01.kcv);
					File file = new File(filepath + File.separatorChar + Client01.FileName_d);
					HashMap<String,String> toftp = new HashMap<String,String>();
					toftp.put("Prelude", Appoint_Prelude.C_V_ftp_name);
					toftp.put("FileName",fnd);
					oos.writeObject(toftp);
					FileOutputStream fos = new FileOutputStream(file);
					DataInputStream dis = new DataInputStream(Sockets.getInputStream());
					int length = 0;
					byte[] bytes = new byte[1024];
					TXT.time2 = (int) (Long.parseLong(lengthchoice[k])/1024+1);
					lengthchoice=null;
					filechoice=null;
					k=0;
					Client01.txt.ta2.removeAllItems();
					while(true) {
						Thread.sleep(500);
						formFTP = (HashMap<String, String>) ois.readObject();
						HashMap<String, String> ToFTP = new HashMap<String, String>();
						System.out.println("等待。。。"+formFTP.get("Prelude") + "||" + formFTP.get("Flag"));
						if(formFTP.get("Prelude").equals(Appoint_Prelude.V_C_ftp_file) && formFTP.get("Flag").equals("continue")) {
							ToFTP.put("Prelude", Appoint_Prelude.C_V_ftp_file);
							System.out.println("recv file...");
							length = dis.read(bytes);
							bytes = des.decode_f(bytes, Client01.kcv);
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
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
