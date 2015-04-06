package utility;

public class ByteUtility {
	//int转byte[]
    public static byte[] int2byte(int res) {  
	    byte[] targets = new byte[4];  
	      
	    targets[0] = (byte) (res & 0xff);// 最低位   
	    targets[1] = (byte) ((res >> 8) & 0xff);// 次低位   
	    targets[2] = (byte) ((res >> 16) & 0xff);// 次高位   
	    targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。   
	    return targets;   
    }   
	
	//byte[]转int
	public static int byte2int(byte[] res) {
		// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
				| ((res[2] << 24) >>> 8) | (res[3] << 24);
		return targets;
	}
	
	/***
	 * 拷贝fromByte到toByte，在len位置后
	 * @param toByte
	 * @param fromByte
	 * @param len
	 * @return
	 */
	public static byte[] byteArrayCopy(byte[] toByte,byte[] fromByte, int len){//Len为toByte坐标
		for(int i = 0; i < fromByte.length; i++){
			toByte[len+i] = fromByte[i];
		}
		return toByte;
	}
	
	public static byte[] getByteArray(byte[] fromByte,int star, int len){//Len为toByte坐标
		byte[] byteArray = new byte[len];
		for(int i = 0; i < len; i++){
			byteArray[i] = fromByte[star + i];
		}
		return byteArray;
	}
}
