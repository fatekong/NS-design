import java.io.*;
class MyFile extends Thread{
	public void run(){
		byte[] bytes = new byte[1024];
		try {
			DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(TestForFile.filepath)));
			int read = 0;
			while(read != -1) {
	    		read = dis.read(bytes);
	    		String nnn = new String(bytes);
	    		System.out.println(nnn);
	    		Thread.sleep(2000);
			}
			dis.close();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
public class TestForFile {
	static String filepath = "D:\\\\TestforNS-design\\\\NS.txt";
	public static void main(String[] args) {
		MyFile m1 = new MyFile();
		MyFile m2 = new MyFile();
		MyFile m3 = new MyFile();
		m1.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("********************************************");
		m2.start();
		System.out.println("********************************************");
		m3.start();
	}
}
