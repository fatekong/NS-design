package Test;

public class Appoint_Prelude {
	//验证阶段
	static final String C_AS = "000100000000";//C->AS
	static final String AS_C = "010000000000";//AS->C
	static final String C_TGS = "001000000000";//C->TGS
	static final String TGS_C = "100000000000";//TGS->C
	static final String C_V = "001100000000";//C->V
	static final String V_C = "110000000000";//V->C
	//验证错误阶段
	static final String C_AS_error = "000110000000";//C->AS error
	static final String AS_C_error = "010010000000";//AS->C error
	static final String C_TGS_error = "001010000000";//C->TGS error
	static final String TGS_C_error = "100010000000";//TGS->C error
	static final String C_V_error = "001110000000";//C->V error
	static final String V_C_error = "110010000000";//V->C error
	//聊天通讯阶段
	static final String C_V_chatcut = "001111000000";//C->V 断开连接
	static final String V_C_chatcut = "110011000000";//V->C 断开连接
	static final String C_V_chat = "001101000000";//C->V chat
	static final String V_C_chat = "110001000000";//V->C chat
	//文件下载阶段
	static final String C_V_ftp_download = "001101010000";//C->V C下载文件，请求V文件夹中的文件名
	static final String V_C_ftp_download = "110001010000";//V->C V发送当前文件夹中的所有内容
	static final String C_V_ftp_name = "001101100000";//C->V C向V发送要获取的文件名称
	static final String V_C_ftp_file = "110001110000";//V->C V向C发送文件内容|V收到C的文件内容
	//文件上传阶段
	//static final String C_V_ftp_name = "001101100000";//C->V C向V发送要获取的文件名称
	static final String V_C_ftp_upload = "110001100000";//V->C V收到C要上传的文件名称
	static final String C_V_ftp_file = "110001110000";//C->V C向V发送文件流|C收到V的文件流
	static final String C_V_ftpcut = "001111000000";//C->V 端来文件连接
	static final String V_C_ftpcut = "110011000000";//V->C 端来文件连接
	//文件错误信息，暂时保留先
	
}
