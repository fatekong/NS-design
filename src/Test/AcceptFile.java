package Test;
import java.io.*;
import java.net.*;
import java.util.HashMap;
class ACFile extends Thread{
	//Socket so;
	public ACFile() {
		//so = socket;
	}
	@SuppressWarnings({ "resource", "unchecked" })
	public void run(){
		byte[] bytes = new byte[1024];
		try {
			ServerSocket Server = new ServerSocket(10002);
			Socket socket = Server.accept();
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			@SuppressWarnings("unchecked")
			HashMap<String,String> formclient = (HashMap<String, String>) ois.readObject();
			String filename = formclient.get("FileName");
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			//DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(TestForFile.filepath)));
			int read = 0;
            //DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            File file = new File(AcceptFile.filepath + File.separatorChar + filename);
            FileOutputStream fos = new FileOutputStream(file);
            String Flag = "continue";
            while(true) {
            	if(Flag == "continue") {
            		read = dis.read(bytes);
    	    		//String nnn = new String(bytes);
    	    		//byte[] temp = s.getBytes(); 
    				fos.write(bytes, 0, read);
    	    		//System.out.println(nnn);
    	    		//Thread.sleep(2000);
    				fos.flush();
            	}
            	else
            		break;
				formclient = (HashMap<String, String>) ois.readObject();
            }
			dis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
public class AcceptFile {
	static String filepath = "D:\\学术\\资料\\智能优化\\差分进化";
	public static void main(String[] args) throws UnknownHostException, IOException {
		@SuppressWarnings("resource")
		
		
		ACFile m1 = new ACFile();
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
