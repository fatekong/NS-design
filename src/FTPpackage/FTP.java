package FTPpackage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class FTPVThread extends Thread{
	public static final String SERVER_IP = "127.0.0.1";
	int portnum;
	public FTPVThread(int num) {
		portnum = num;
	}
	@SuppressWarnings({ "unchecked", "resource" })
	public void run() {
		//��ȡ�ļ��������ļ�����
		/*File dir = new File(FTP.FilePath);
		String[] fileNames=dir.list();
		for(int i=0;i<fileNames.length;i++)
        {
            System.out.println(fileNames[i]+";");
        }
        System.out.println(" ");*/
		try {
			ServerSocket Servers = new ServerSocket(portnum);
			Socket Sockets = Servers.accept();
			InputStream in = Sockets.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(in);
			OutputStream os = Sockets.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			HashMap<String, String> fromclient = (HashMap<String, String>) ois.readObject();
			if(fromclient.get("Prelude").equals(Appoint_Prelude.C_V)) {
				System.out.println("�յ�C-��V");
				HashMap<String,String> toclient = new HashMap<String,String>();
				toclient.put("Prelude", Appoint_Prelude.V_C);
				oos.writeObject(toclient);
				VFTPthread upload = new VFTPthread("upload",portnum);//C����V���ļ�
				VFTPthread download = new VFTPthread("download",portnum);//C��V�ϴ��ļ�
				upload.start();//C����V���ļ�
				download.start();//C��V�ϴ��ļ�
			}
		}catch(IOException | ClassNotFoundException e) {
			System.out.println("socket����");
		}
	}
}
public class FTP {
	public static final String FilePath = "D:\\ѧ��\\����\\�����Ż�\\��ֽ���";
	public static void main(String[] args) {
		FTPVThread forclient1 = new FTPVThread(10013);
		forclient1.start();
	}
}
