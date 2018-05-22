package Test;
import java.io.*;
import java.net.*;
import java.util.HashMap;
class MyFile extends Thread{
	Socket so;
	public MyFile(Socket socket) {
		so = socket;
	}
	public void run(){
		byte[] bytes = new byte[1024];
		try {
			OutputStream is = so.getOutputStream();
			ObjectOutputStream ois = new ObjectOutputStream(is);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(TestForFile.filepath)));
			int read = 0;
            DataOutputStream dos = new DataOutputStream(so.getOutputStream());
            HashMap<String,String> aaa = new HashMap<String,String>();
            aaa.put("FlieName", "NS.txt");
            aaa.put("Flag", "continue");
            ois.writeObject(aaa);
			while(read != -1) {
	    		read = dis.read(bytes);
	    		//String nnn = new String(bytes);
	    		dos.write(bytes, 0, read);
	    		dos.flush();
	    		//System.out.println(nnn);
	    		//Thread.sleep(2000);
	    		ois.writeObject(aaa);
			}
			aaa.put("Flag", "over");
			ois.writeObject(aaa);
			dis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
public class TestForFile {
	static String filepath = "D:\\TestforNS-design\\NS.txt";
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1",10002);
		
		MyFile m1 = new MyFile(socket);
		//MyFile m2 = new MyFile();
		//MyFile m3 = new MyFile();
		m1.start();
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//System.out.println("********************************************");
		//m2.start();
		//System.out.println("********************************************");
		//m3.start();
	}
}
