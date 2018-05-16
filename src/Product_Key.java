import java.util.Random;
public class Product_Key {
	public String product() {
		String key = "";
		int element = 0;
		Random random = new Random();
		for(int i = 0 ; i < 8 ; i++) {
			element = Math.abs(random.nextInt())%95+32;
			System.out.println(element +"," +(char)element);
			key += (char)element;
		}
		return key;
	}
	public static void main(String[] args) {
		Product_Key my = new Product_Key();
		System.out.println(my.product());
	}
}
