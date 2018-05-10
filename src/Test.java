import java.util.HashMap;

public class Test {
	public static void main(String args[]) {
		HashMap<String , String> xxx = new HashMap<String , String>();
		xxx.put("x" , "m");
		xxx.put("x" , "y");
		xxx.put("x" , "x");
		System.out.println(xxx.get("x"));
	}
}
