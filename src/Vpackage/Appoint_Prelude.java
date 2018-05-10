package Vpackage;

public class Appoint_Prelude {
	static final String C_AS = "000100000000";//C->AS
	static final String AS_C = "010000000000";//AS->C
	static final String C_TGS = "001000000000";//C->TGS
	static final String TGS_C = "100000000000";//TGS->C
	static final String C_V = "001100000000";//C->V
	static final String V_C = "110000000000";//V->C
	static final String C_V_chat = "001101000000";//C->V chat
	static final String V_C_chat = "110001000000";//V->C chat
	static final String C_AS_error = "000110000000";//C->AS error
	static final String AS_C_error = "010010000000";//AS->C error
	static final String C_TGS_error = "001010000000";//C->TGS error
	static final String TGS_C_error = "100010000000";//TGS->C error
	static final String C_V_error = "001110000000";//C->V error
	static final String V_C_error = "110010000000";//V->C error
	static final String C_V_chatcut = "001111000000";//C->V 断开连接
	static final String V_C_chatcut = "110011000000";//V->C 断开连接
	
}
