package utility;

public class ByteUtility {
	//intתbyte[]
    public static byte[] int2byte(int res) {  
	    byte[] targets = new byte[4];  
	      
	    targets[0] = (byte) (res & 0xff);// ���λ   
	    targets[1] = (byte) ((res >> 8) & 0xff);// �ε�λ   
	    targets[2] = (byte) ((res >> 16) & 0xff);// �θ�λ   
	    targets[3] = (byte) (res >>> 24);// ���λ,�޷������ơ�   
	    return targets;   
    }   
	
	//byte[]תint
	public static int byte2int(byte[] res) {
		// һ��byte��������24λ���0x??000000��������8λ���0x00??0000
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | ��ʾ��λ��
				| ((res[2] << 24) >>> 8) | (res[3] << 24);
		return targets;
	}
	
	/***
	 * ����fromByte��toByte����lenλ�ú�
	 * @param toByte
	 * @param fromByte
	 * @param len
	 * @return
	 */
	public static byte[] byteArrayCopy(byte[] toByte,byte[] fromByte, int len){//LenΪtoByte����
		for(int i = 0; i < fromByte.length; i++){
			toByte[len+i] = fromByte[i];
		}
		return toByte;
	}
	
	public static byte[] getByteArray(byte[] fromByte,int star, int len){//LenΪtoByte����
		byte[] byteArray = new byte[len];
		for(int i = 0; i < len; i++){
			byteArray[i] = fromByte[star + i];
		}
		return byteArray;
	}
}
