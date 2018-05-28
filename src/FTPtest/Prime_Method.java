package FTPtest;

import java.math.BigInteger;    
import java.util.Random;
import java.util.ArrayList;
public class Prime_Method {
	BigInteger GetPorQ(){
		BigInteger PorQ;
		while(true){
			BigInteger random = BigInteger.probablePrime(10, new Random()); 
			BigInteger b = new BigInteger("2");
			BigInteger a = new BigInteger("1");
			PorQ = random.multiply(b);
			PorQ = PorQ.add(a);
			//判断PorQ是否是强素数
			if(PorQ.isProbablePrime(10)){
				break;
			}
		}
		return PorQ;
	}
	
	BigInteger GetE(BigInteger P,BigInteger Q){
		BigInteger E;
		while(true){
			E =  BigInteger.probablePrime(10, new Random()); 
			P = P.subtract(new BigInteger("1"));
			Q = Q.subtract(new BigInteger("1"));
			BigInteger N = P.multiply(Q);
			if(N.gcd(E).intValue() == 1){
				break;
			}
		}
		return E;
	}
	
	BigInteger GetD(BigInteger P,BigInteger Q,BigInteger E){
		BigInteger D ;
		P = P.subtract(new BigInteger("1"));
		Q = Q.subtract(new BigInteger("1"));
		BigInteger N = P.multiply(Q);
		D= IE(E,N);
		return D;
	}
	
	BigInteger Gcd(BigInteger a,BigInteger b){
		if(b.equals(BigInteger.ZERO)){
			return a;
		}
		else{
			return Gcd(b, a.mod(b));
		}
	}
	
	BigInteger E_Gcd(BigInteger a,BigInteger b,BigInteger x , BigInteger y){
		if(b.equals(BigInteger.ZERO)){
			x = BigInteger.ONE;
			y = BigInteger.ZERO;
			return a;
		}
		BigInteger ans = E_Gcd(b, a.mod(b), x, y);
		BigInteger temp  = x;
		x = y;
		y = temp.subtract((a.divide(b)).multiply(y));
		return ans;
	}
	
	BigInteger IE(BigInteger E , BigInteger N){
		BigInteger b = BigInteger.ZERO;
		BigInteger nBigInteger =N;
		ArrayList<BigInteger> times = new ArrayList<BigInteger>();
		BigInteger test;
		while(!N.mod(E).equals(BigInteger.ZERO)){
			test = N.mod(E);
			//System.out.println("tese%:"+test);
			test = (N.subtract(test)).divide(E);
			//System.out.println("test/:"+test);
			times.add(test);
			//System.out.println(test);
			test = N.mod(E);
			N = E;
			E = test;
		}
		test = BigInteger.ONE;
		for(int i = 0 ; i < times.size() ; i++){
			if(i == 0){
				b = times.get(times.size()-1);
			}
			else{
				BigInteger beforeb = b;
				//System.out.println("tiems:"+times.get(times.size()-i-1));
				b = times.get(times.size()-i-1).multiply(b).add(test);
				test = beforeb;
			}
			//System.out.println(b);
		}
		if(times.size()%2 != 0){
			b = nBigInteger.subtract(b);
		}
		return b;
	}
	
	
	
	
	
	
	
	
	
	
}
