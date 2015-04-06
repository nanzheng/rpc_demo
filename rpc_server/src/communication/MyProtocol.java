package communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import utility.ByteUtility;

public final class MyProtocol {
	
	public static String serverIp;
	public static int port;
	
	
	/***
	 * �ͻ���������������������ͻ���ʹ��
	 * @param args ����+������+�����б�
	 * @return ���������ý��
	 * @throws IOException
	 */
	public static String[] exchange(String... args) throws IOException{
		return MyProtocol.sentRecive(MyProtocol.packData(args));
	
	}
	
	/***
	 * �������������ݷ����������ʹ��
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static String[] serverRec(InputStream input) throws IOException{
		byte[] intByte = new byte[4];
		input.read(intByte);	 //���������峤��
		byte[] argsByte = new byte[ByteUtility.byte2int(intByte)];//��������byte
		intByte = new byte[4];
		input.read(intByte);	//�����Ĳ�������
		int argsNum = ByteUtility.byte2int(intByte);						 
		input.read(argsByte);	//����������
		String[] argStr =MyProtocol.unpackData(argsByte, argsNum);
//		for (String str:argStr){
//			System.out.println(str);
//		}
		return argStr;
	}
	
	/****
	 * ����˷������ݷ����������ʹ��
	 * @param output
	 * @param args
	 * @throws IOException
	 */
	public static void serverSent(OutputStream output, String... args) throws IOException{
		output.write(MyProtocol.packData(args));
	}
	
	
	private static String[] sentRecive(byte[] args) throws IOException{
		Socket socket = new Socket(serverIp, port);
		OutputStream dos = socket.getOutputStream();
		dos.write(args);
		dos.flush();
		
		InputStream input= socket.getInputStream();
		byte[] intByte = new byte[4];
		input.read(intByte);	 //���������峤��
		byte[] argsByte = new byte[ByteUtility.byte2int(intByte)];//��������byte
		intByte = new byte[4];
		input.read(intByte);	//�����Ĳ�������
		int argsNum = ByteUtility.byte2int(intByte);						 
		input.read(argsByte);	//����������
		String[] argStr =MyProtocol.unpackData(argsByte, argsNum);
		input.close();
		dos.close();
		return argStr;
	}
	
//	private  static OutputStream conServer() throws UnknownHostException, IOException{
//
//	}
	
	/***
	 * ��Э��Ҫ����byte����
	 * ���ĸ�ʽ������ͷ��4byte��+����������4byte��+[��������(4byte)+ʵ�ʲ���]...
	 */
	private static byte[] packData(String... args){
		
		int argsNum = args.length;        //���Ĳ�������
		int byteLen = (argsNum+2) * 4;    //�������峤�ȣ����Ķ�����+��������
		//ͳ�Ʋ������ĳ���byteLen
		for(int len = 0; len <args.length; len++){
			byteLen += args[len].getBytes().length;
		}
		//���巵������
		byte[] outputByte = new byte[byteLen];	//���ص�����
		int outputByteLen = 0;                  //���ص���������
		//д�붨��ͷ
		byte[] byteArrayLen = ByteUtility.int2byte(byteLen);//����ͷbyte[]
		ByteUtility.byteArrayCopy(outputByte,byteArrayLen,outputByteLen);
		outputByteLen += 4;	//��������
		//д���������
		byteArrayLen = ByteUtility.int2byte(argsNum);//��������byte[]
		ByteUtility.byteArrayCopy(outputByte,byteArrayLen,outputByteLen);
		outputByteLen += 4;	//��������
		//ѭ��д�����byte[]
		for(int i=0; i <args.length; i++){
			//���㲢д���������
			byteLen = args[i].getBytes().length;
			ByteUtility.byteArrayCopy(outputByte,ByteUtility.int2byte(byteLen),outputByteLen);//д���������
			
			outputByteLen += 4;
			//д�����
			ByteUtility.byteArrayCopy(outputByte,args[i].getBytes(),outputByteLen);
			outputByteLen += byteLen;
		}
		return outputByte;
		
	}
	
	public static String[] unpackData(byte[] args, int argsNum){
		String[] argString = new String[argsNum];
		int argStringLen=0;
		int byteLen =0;//����
		int len;
		for (int i =0; i< argsNum; i++){
			
			len = ByteUtility.byte2int(ByteUtility.getByteArray(args,byteLen,4));
			byteLen +=4;
			String str = new String(ByteUtility.getByteArray(args,byteLen,len));
			byteLen +=len;
			argString[argStringLen++] = str;
		}
		
		return argString;
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		String host="localhost";
		int port=8000;

		Socket socket =  new Socket(host, port);
		OutputStream outStream = socket.getOutputStream();
		
		String[] arg = new String[]{"123","23456","345678","44567889"};
		
		outStream.write(packData(arg));
		outStream.flush();
		outStream.close();
	}
}
