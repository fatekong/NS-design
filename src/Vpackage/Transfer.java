package Vpackage;
import java.util.ArrayList;


import java.math.BigInteger;  
//使用BigInteger，目前因为各种原因待改进

public class Transfer {
	private BigInteger N;
	ArrayList<String> Num = new ArrayList<String>();
	
	public Transfer(BigInteger n){
		N = n;
		System.out.println("N:"+N);
	}
	
	//字符串转化为数字
	void TransByMes(String message){
		char[] num = message.toCharArray();
		String number = "";
		for(int i = 0 ; i < num.length ; i++){
			number += (int)num[i];
			Num.add(number);
			number = "";
		}
	}
	
	//明文转化为密文
	String TransToSec(String message,BigInteger e){
		ArrayList<BigInteger> secret = new ArrayList<BigInteger>();
		TransByMes(message);
		for(int i = 0 ; i<Num.size() ; i++){
			BigInteger x = new BigInteger(Num.get(i));
			secret.add(x.modPow(e, N));
		}
		String secs = "";//字符串用“-”存储一些数据
		for(int i = 0 ; i < secret.size() ; i++){
			secs += secret.get(i) + "-";
			System.out.print(secret.get(i) + "|");
		}
		return secs;
	}
	
	//密文转化为明文
	String TransToMes(String secret,BigInteger d){
		System.out.println("D:"+d);
		String[] secss = secret.split("-");
		for(int i = 0 ; i < secss.length ; i++) {
			System.out.print(secss[i] + " ");
		}
		System.out.println();
		ArrayList<String> message = new ArrayList<String>();
		for(int i = 0 ; i<secss.length; i++){
			BigInteger x = new BigInteger(secss[i]);
			message.add(x.modPow(d, N).toString());
			System.out.print(x.modPow(d, N).toString()+" ");
		}
		System.out.println();
		return TransBySec(message);
	}
	
	String TransBySec(ArrayList<String> message){
		String answer = "";
		for(int i = 0 ; i < message.size() ; i++){
			int x = Integer.parseInt(message.get(i));
			char m = (char)x;
			answer += String.valueOf(m);
		}
		return answer;
	}
}
