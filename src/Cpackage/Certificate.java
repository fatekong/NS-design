package Cpackage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Certificate {
	public void GetCertificate() throws IOException {
		String file = "D:\\Certificate\\V.txt";
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		String str = null; 
		String RSA[] = new String[2];
        while((str = br.readLine()) != null) {   
               System.out.println(str);
               RSA = str.split("-");
         } 
         br.close();
         reader.close();
         String E = RSA[0];
         String D = RSA[0];
         System.out.println(RSA[0]);
         System.out.println(RSA[1]);
	}
	
	public static void main(String[] args) throws IOException {
		Certificate c = new Certificate();
		c.GetCertificate();
	}
}
