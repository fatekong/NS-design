package Test;

public class Appoint_Prelude {
	//��֤�׶�
	static final String C_AS = "000100000000";//C->AS
	static final String AS_C = "010000000000";//AS->C
	static final String C_TGS = "001000000000";//C->TGS
	static final String TGS_C = "100000000000";//TGS->C
	static final String C_V = "001100000000";//C->V
	static final String V_C = "110000000000";//V->C
	//��֤����׶�
	static final String C_AS_error = "000110000000";//C->AS error
	static final String AS_C_error = "010010000000";//AS->C error
	static final String C_TGS_error = "001010000000";//C->TGS error
	static final String TGS_C_error = "100010000000";//TGS->C error
	static final String C_V_error = "001110000000";//C->V error
	static final String V_C_error = "110010000000";//V->C error
	//����ͨѶ�׶�
	static final String C_V_chatcut = "001111000000";//C->V �Ͽ�����
	static final String V_C_chatcut = "110011000000";//V->C �Ͽ�����
	static final String C_V_chat = "001101000000";//C->V chat
	static final String V_C_chat = "110001000000";//V->C chat
	//�ļ����ؽ׶�
	static final String C_V_ftp_download = "001101010000";//C->V C�����ļ�������V�ļ����е��ļ���
	static final String V_C_ftp_download = "110001010000";//V->C V���͵�ǰ�ļ����е���������
	static final String C_V_ftp_name = "001101100000";//C->V C��V����Ҫ��ȡ���ļ�����
	static final String V_C_ftp_file = "110001110000";//V->C V��C�����ļ�����|V�յ�C���ļ�����
	//�ļ��ϴ��׶�
	//static final String C_V_ftp_name = "001101100000";//C->V C��V����Ҫ��ȡ���ļ�����
	static final String V_C_ftp_upload = "110001100000";//V->C V�յ�CҪ�ϴ����ļ�����
	static final String C_V_ftp_file = "110001110000";//C->V C��V�����ļ���|C�յ�V���ļ���
	static final String C_V_ftpcut = "001111000000";//C->V �����ļ�����
	static final String V_C_ftpcut = "110011000000";//V->C �����ļ�����
	//�ļ�������Ϣ����ʱ������
	
}
