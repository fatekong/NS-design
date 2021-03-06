

import java.util.Random;


public class DES {
    // 锟矫伙拷IP锟斤拷
    private int[] IP_Table = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14,
            6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45,
            37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };
    // 锟斤拷锟矫伙拷IP-1锟斤拷
    private int[] IPR_Table = { 40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62,
            30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42,
            10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25 };
    // E位选锟斤拷锟�(锟斤拷展锟矫伙拷锟斤拷)
    private int[] E_Table = { 32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17,
            18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1 };
    // P锟斤拷位锟斤拷(锟斤拷锟斤拷锟斤拷位锟斤拷)
    private int[] P_Table = { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9,
            19, 13, 30, 6, 22, 11, 4, 25 };
    // PC1选位锟斤拷(锟斤拷钥锟斤拷锟斤拷锟矫伙拷锟斤拷1)
    private int[] PC1_Table = { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11,
            3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13,
            5, 28, 20, 12, 4 };
    // PC2选位锟斤拷(锟斤拷钥锟斤拷锟斤拷锟矫伙拷锟斤拷2)
    private int[] PC2_Table = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32 };
    // 锟斤拷锟斤拷位锟斤拷锟斤拷
    private int[] LOOP_Table = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
    // S锟斤拷
    private int[][] S_Box = {
            // S1
            { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3,
                    8, 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0, 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10,
                    0, 6, 13 },
            // S2
            { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11,
                    5, 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15, 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0,
                    5, 14, 9 },
            // S3
            { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15,
                    1, 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7, 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11,
                    5, 2, 12 },
            // S4
            { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14,
                    9, 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4, 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12,
                    7, 2, 14 },
            // S5
            { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8,
                    6, 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14, 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9,
                    10, 4, 5, 3 },
            // S6
            { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3,
                    8, 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6, 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6,
                    0, 8, 13 },
            // S7
            { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8,
                    6, 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2, 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14,
                    2, 3, 12 },
            // S8
            { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9,
                    2, 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8, 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3,
                    5, 6, 11 } };
    
    public String Main_key()//鐢熸垚浜嗗瘑閽�
    {
    		String key="";
    		int element = 0;
    		Random random = new Random();
    		for(int i = 0 ; i < 8 ; i++) {
    			element = Math.abs(random.nextInt())%95+32;
//    			System.out.println(element +"," +(char)element);
    			key += (char)element;
    		}
    		return key;
    }
    
    String encode(String plaintext,String key)
    {

        String plaintext_fake="";
        DES des = new DES();
        String ciphertext="";

//  .substring(i * 8, (i + 1) * 8)
//        StringBuffer plaintextBinary = new StringBuffer();
        StringBuffer mSubPlaintextTemp = new StringBuffer();
        for (int i = 0; i < plaintext.length(); ++i) {
        		StringBuffer temp=new StringBuffer();
        		temp.append(Integer.toBinaryString(plaintext.charAt(i)));
        		while (temp.length() < 8) {
        			temp.insert(0, 0);
            }
        		while (temp.length() ==15) {
        			temp.insert(0, 1);
        		//	temp.replace(0, 1, "1");
            }
            mSubPlaintextTemp.append(temp);
        }
        for (int i = 0; i < mSubPlaintextTemp.length()/8; ++i) {
        		String temp="";
        		temp = mSubPlaintextTemp.substring(i * 8, (i + 1) * 8);
        	plaintext_fake+=(char) Integer.parseInt(temp, 2);
        }
      
        
        int res1=plaintext_fake.length()%8;
        int res2=plaintext_fake.length()/8;
        for(int i=res2*8+res1-1;i<(res2+1)*8-1;++i)//
        {
        		plaintext_fake+=" ";
        }
        for(int i=0;i<=res2;++i)
        {
        		String plaintext_temp="";
        		for(int j=i*8;j<(i+1)*8;++j)
        		{
        			plaintext_temp+=plaintext_fake.charAt(j);
        		}
        		ciphertext+=des.encrypt(plaintext_temp, key, "encrypt");
        }
        //System.out.println("鏄庢枃锛�" + plaintext + "\n瀵嗛挜锛�" + key + "\n瀵嗘枃:" + ciphertext);
        return ciphertext;
    }
    
    String encode_pro(String plaintext,String key)
    {
        DES des = new DES();
        String ciphertext="";
        int res1=plaintext.length()%8;
        int res2 = plaintext.length()/8;
        for(int i=res2*8+res1-1;i<(res2+1)*8-1;++i)//
        {
        		plaintext+=" ";
        }
        for(int i=0;i<=res2;++i)
        {
        		String plaintext_temp="";
        		for(int j=i*8;j<(i+1)*8;++j)
        		{
        			plaintext_temp+=plaintext.charAt(j);
        		}
        		ciphertext+=des.encrypt(plaintext_temp, key, "encrypt");
        }
        //System.out.println("鏄庢枃锛�" + plaintext + "\n瀵嗛挜锛�" + key + "\n瀵嗘枃:" + ciphertext);
        return ciphertext;
    }
    
    public String decode_pro(String ciphertext,String key)
    {
    		DES des = new DES();

        		String plaintext_pro="";
        StringBuffer ciphertext_new = new StringBuffer(); 
        int res1=ciphertext.length()%8;
        int res2=ciphertext.length()/8;
        for(int i=0;i<res2;++i)
        {
        		String ciphertext_temp="";
        		for(int j=i*8;j<(i+1)*8;++j)
        		{
        			ciphertext_temp+=ciphertext.charAt(j);
        		}
        		plaintext_pro+=des.encrypt(ciphertext_temp, key, "decrypt");
        }
        String temp_of_temp="";
        for (int i = 0; i < plaintext_pro.length()/8; ++i) {
            temp_of_temp += plaintext_pro.substring(i * 8, (i + 1) * 8);
          ciphertext_new.append((char) Integer.parseInt(temp_of_temp, 2));
          temp_of_temp="";
        }
        //System.out.println("瑙ｅ瘑锛�" + ciphertext_new);
        String mydecode = ciphertext_new.toString();
        mydecode = mydecode.trim();
        //System.out.println("瑙ｅ瘑锛�" + mydecode);
        return mydecode;
    }
    
    public String decode(String ciphertext,String key)
    {
    		DES des = new DES();

        		String plaintext_pro="";
        StringBuffer ciphertext_new = new StringBuffer(); 
        int res1=ciphertext.length()%8;
        int res2=ciphertext.length()/8;
        for(int i=0;i<res2;++i)
        {
        		String ciphertext_temp="";
        		for(int j=i*8;j<(i+1)*8;++j)
        		{
        			ciphertext_temp+=ciphertext.charAt(j);
        		}
        		plaintext_pro+=des.encrypt(ciphertext_temp, key, "decrypt");
        }
        
        int flag = 0;
        String temp_of_temp="";
        for (int i = 0; i < plaintext_pro.length()/8; ++i) {
            temp_of_temp += plaintext_pro.substring(i * 8, (i + 1) * 8);
          if(temp_of_temp.charAt(0)!='0'&&flag==0)
          {
          		flag=1;
          		temp_of_temp="0"+temp_of_temp.substring(1,8);
          		continue;
          }
          ciphertext_new.append((char) Integer.parseInt(temp_of_temp, 2));
          temp_of_temp="";
          flag=0;
        }
        //System.out.println("瑙ｅ瘑锛�" + ciphertext_new);
        String mydecode = ciphertext_new.toString();
        mydecode = mydecode.trim();
        //System.out.println("瑙ｅ瘑锛�" + mydecode);
        return mydecode;
    }
    
    /*public static void main(String[] args) {
        String plaintext = "鏇�:瀵规垜灏辨槸鐙�";
        String ciphertext="";
        String plaintext_after;
        String key = "";
        
		key=Main_key();//瀵嗛挜鐢熸垚
        ciphertext=encode(plaintext, key);//鍔犲瘑锛堣緭鍏ユ槑鏂囷紝key锛�
        System.out.println("********************************");
        plaintext_after=decode(ciphertext,key);//瑙ｅ瘑锛堣緭鍏ュ瘑鏂囷紝key锛�
		
    }*/

    
    
    // 1 瀛愬瘑閽ョ敓鎴�
    public StringBuffer[] getSubKey(String key) {
        StringBuffer[] subKey = new StringBuffer[16]; // 瀛樺偍瀛愬瘑閽�

        // 1.1 鎶婂瘑閽ヨ浆鎹㈡垚浜岃繘鍒跺瓧绗︿覆
        StringBuffer keyBinary = new StringBuffer(); //瀛樺偍杞崲鍚庣殑浜岃繘鍒跺瘑閽�
        for (int i = 0; i < 8; ++i) {
            StringBuffer mSubKeyTemp = new StringBuffer(Integer.toBinaryString(key.charAt(i)));
            while (mSubKeyTemp.length() < 8) {
                mSubKeyTemp.insert(0, 0);
            }
            keyBinary.append(mSubKeyTemp);
        }

        // 1.2閫氳繃PC1缃崲瀵嗛挜
        StringBuffer substituteKey = new StringBuffer(); // 瀛樺偍PC1缃崲鍚庣殑瀵嗛挜
        for (int i = 0; i < 56; ++i) {
            substituteKey.append(keyBinary.charAt(PC1_Table[i] - 1));
        }

        // 1.3 鍒嗘垚宸﹀彸涓ゅ潡C0鍜孌0
        StringBuffer C0 = new StringBuffer(); //鍌ㄥ瓨瀵嗛挜宸﹁竟
        StringBuffer D0 = new StringBuffer(); //鍌ㄥ瓨绉橀挜鍙宠竟
        C0.append(substituteKey.substring(0, 28));
        D0.append(substituteKey.substring(28));

        // 1.4 寰幆16杞敓鎴愬瓙瀵嗛挜
        for (int i = 0; i < 16; ++i) {
            // 鏍规嵁LOOP_Table寰幆宸︾Щ
            for (int j = 0; j < LOOP_Table[i]; ++j) {
                char mTemp;
                mTemp = C0.charAt(0);
                C0.deleteCharAt(0);
                C0.append(mTemp);
                mTemp = D0.charAt(0);
                D0.deleteCharAt(0);
                D0.append(mTemp);
            }

            // 鎶婂乏鍙充袱鍧楀悎骞�
            StringBuffer C0D0 = new StringBuffer(C0.toString() + D0.toString());

            // 鏍规嵁PC2鍘嬬缉C0D0锛屽緱鍒板瓙瀵嗛挜
            StringBuffer C0D0Temp = new StringBuffer();
            for (int j = 0; j < 48; ++j) {
                C0D0Temp.append(C0D0.charAt(PC2_Table[j] - 1));
            }

            // 鎶婂瓙瀵嗛挜瀛樺偍鍒版暟缁勪腑
            subKey[i] = C0D0Temp;
        }
        return subKey;
    }

    // 2 鍔犲瘑
    public String encrypt(String plaintext, String key, String type) {
        StringBuffer ciphertext = new StringBuffer(); 
        // 2.1 鎶婃槑鏂囪浆鎹㈡垚浜岃繘鍒跺瓧绗︿覆
        StringBuffer plaintextBinary = new StringBuffer(); // 瀛樺偍鏄庢枃浜岃繘鍒�
        for (int i = 0; i < 8; ++i) {
            StringBuffer mSubPlaintextTemp = new StringBuffer(Integer.toBinaryString(plaintext.charAt(i)));
            while (mSubPlaintextTemp.length() < 8) {
                mSubPlaintextTemp.insert(0, 0);
            }
            //System.out.println(mSubPlaintextTemp);
            plaintextBinary.append(mSubPlaintextTemp);
        }
        //System.out.println(plaintextBinary.length());
        // 2.2閫氳繃IP缃崲鏄庢枃
        StringBuffer substitutePlaintext = new StringBuffer(); 
        for (int i = 0; i < 64; ++i) {
            substitutePlaintext.append(plaintextBinary.charAt(IP_Table[i] - 1));
        }

        // 2.3 鎶婄疆鎹㈠悗鐨勬槑鏂囧垎涓哄乏鍙充袱鍧�
        StringBuffer L = new StringBuffer(substitutePlaintext.substring(0, 32));
        StringBuffer R = new StringBuffer(substitutePlaintext.substring(32));

        // 2.4 2.4 寰楀埌瀛愬瘑閽�
        StringBuffer[] subKey = getSubKey(key);
        if (type.equals("decrypt")) {
            StringBuffer[] mTemp = getSubKey(key);
            for (int i = 0; i < 16; ++i) {
                subKey[i] = mTemp[15 - i];
            }
        }

        // 2.5 杩涜16杞凯浠�
        for (int i = 0; i < 16; ++i) {
            StringBuffer mLTemp = new StringBuffer(L); // 锟芥储原锟斤拷锟斤拷锟斤拷锟�

            // 锟斤拷叩牟锟斤拷锟�
            L.replace(0, 32, R.toString());

            // 锟斤拷E位选锟斤拷锟斤拷锟秸癸拷冶锟�
            StringBuffer mRTemp = new StringBuffer(); // 锟芥储锟斤拷展锟斤拷锟斤拷冶锟�
            for (int j = 0; j < 48; ++j) {
                mRTemp.append(R.charAt(E_Table[j] - 1));
            }

            // 锟斤拷展锟斤拷锟斤拷冶吆锟斤拷锟斤拷锟皆匡拷锟斤拷
            for (int j = 0; j < 48; ++j) {
                if (mRTemp.charAt(j) == subKey[i].charAt(j)) {
                    mRTemp.replace(j, j + 1, "0");
                } else {
                    mRTemp.replace(j, j + 1, "1");
                }
            }

            // 锟斤拷锟斤拷S锟斤拷压锟斤拷
            R.setLength(0);
            for (int j = 0; j < 8; ++j) {
                String mSNumber = mRTemp.substring(j * 6, (j + 1) * 6);
                int row = Integer.parseInt(Character.toString(mSNumber.charAt(0)) + mSNumber.charAt(5), 2);
                int column = Integer.parseInt(mSNumber.substring(1, 5), 2);
                int number = S_Box[j][row * 16 + column];
                StringBuffer numberString = new StringBuffer(Integer.toBinaryString(number));
                while (numberString.length() < 4) {
                    numberString.insert(0, 0);
                }
                R.append(numberString);
            }

            // 锟斤拷压锟斤拷锟斤拷锟絉通锟斤拷P_Table锟矫伙拷
            StringBuffer mRTemp1 = new StringBuffer(); // 锟芥储锟矫伙拷锟斤拷锟絉
            for (int j = 0; j < 32; ++j) {
                mRTemp1.append(R.charAt(P_Table[j] - 1));
            }
            R.replace(0, 32, mRTemp1.toString());

            // 锟斤拷锟矫伙拷锟斤拷锟絉锟斤拷锟筋开始锟斤拷L锟斤拷锟�
            for (int j = 0; j < 32; ++j) {
                if (R.charAt(j) == mLTemp.charAt(j)) {
                    R.replace(j, j + 1, "0");
                } else {
                    R.replace(j, j + 1, "1");
                }
            }
        }

        // 2.6 鍚堝苟杩唬瀹岀殑L鍜孯
        StringBuffer LR = new StringBuffer(R.toString() + L.toString());

        // 2.7 鏍规嵁IPR_Table缃崲LR
        StringBuffer mLRTemp = new StringBuffer(); // 瀛樺偍缃崲鍚庣殑LR
        for (int i = 0; i < 64; ++i) {
            mLRTemp.append(LR.charAt(IPR_Table[i] - 1));
        }
        /*****************************************************/
        
        // 2.8 鎶婁簩杩涘埗杞负瀛楃涓�
        String mCharTemp="";

        if(type.equals("encrypt"))
        {
	        for (int i = 0; i < 8; ++i) {
	            mCharTemp = mLRTemp.substring(i * 8, (i + 1) * 8);
	            ciphertext.append((char) Integer.parseInt(mCharTemp, 2));
	        }
        }
        else
        {
        	for (int i = 0; i < 8; ++i) {
	            mCharTemp += mLRTemp.substring(i * 8, (i + 1) * 8);
	        }
//	        System.out.println(ciphertext);
	        ciphertext.append(mCharTemp);
        }
        return ciphertext.toString();
    }
}