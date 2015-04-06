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
	 * 客户端与服务器交互方法，客户端使用
	 * @param args 类名+方法名+参数列表
	 * @return 服务器调用结果
	 * @throws IOException
	 */
	public static String[] exchange(String... args) throws IOException{
		return MyProtocol.sentRecive(MyProtocol.packData(args));
	
	}
	
	/***
	 * 服务器接收数据方法，服务端使用
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static String[] serverRec(InputStream input) throws IOException{
		byte[] intByte = new byte[4];
		input.read(intByte);	 //读报文正体长度
		byte[] argsByte = new byte[ByteUtility.byte2int(intByte)];//报文正体byte
		intByte = new byte[4];
		input.read(intByte);	//读报文参数个数
		int argsNum = ByteUtility.byte2int(intByte);						 
		input.read(argsByte);	//读报文正体
		String[] argStr =MyProtocol.unpackData(argsByte, argsNum);
//		for (String str:argStr){
//			System.out.println(str);
//		}
		return argStr;
	}
	
	/****
	 * 服务端发送数据方法，服务端使用
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
		input.read(intByte);	 //读报文正体长度
		byte[] argsByte = new byte[ByteUtility.byte2int(intByte)];//报文正体byte
		intByte = new byte[4];
		input.read(intByte);	//读报文参数个数
		int argsNum = ByteUtility.byte2int(intByte);						 
		input.read(argsByte);	//读报文正体
		String[] argStr =MyProtocol.unpackData(argsByte, argsNum);
		input.close();
		dos.close();
		return argStr;
	}
	
//	private  static OutputStream conServer() throws UnknownHostException, IOException{
//
//	}
	
	/***
	 * 按协议要求打包byte数组
	 * 报文格式：定长头（4byte）+参数个数（4byte）+[参数长度(4byte)+实际参数]...
	 */
	private static byte[] packData(String... args){
		
		int argsNum = args.length;        //报文参数个数
		int byteLen = (argsNum+2) * 4;    //报文整体长度，报文定长度+参数个数
		//统计参数报文长度byteLen
		for(int len = 0; len <args.length; len++){
			byteLen += args[len].getBytes().length;
		}
		//定义返回数组
		byte[] outputByte = new byte[byteLen];	//返回的数组
		int outputByteLen = 0;                  //返回的数组坐标
		//写入定长头
		byte[] byteArrayLen = ByteUtility.int2byte(byteLen);//定长头byte[]
		ByteUtility.byteArrayCopy(outputByte,byteArrayLen,outputByteLen);
		outputByteLen += 4;	//更新坐标
		//写入参数个数
		byteArrayLen = ByteUtility.int2byte(argsNum);//参数个数byte[]
		ByteUtility.byteArrayCopy(outputByte,byteArrayLen,outputByteLen);
		outputByteLen += 4;	//更新坐标
		//循环写入参数byte[]
		for(int i=0; i <args.length; i++){
			//计算并写入参数长度
			byteLen = args[i].getBytes().length;
			ByteUtility.byteArrayCopy(outputByte,ByteUtility.int2byte(byteLen),outputByteLen);//写入参数长度
			
			outputByteLen += 4;
			//写入参数
			ByteUtility.byteArrayCopy(outputByte,args[i].getBytes(),outputByteLen);
			outputByteLen += byteLen;
		}
		return outputByte;
		
	}
	
	public static String[] unpackData(byte[] args, int argsNum){
		String[] argString = new String[argsNum];
		int argStringLen=0;
		int byteLen =0;//坐标
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
