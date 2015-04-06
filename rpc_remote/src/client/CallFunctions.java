package client;

import communication.MyProtocol;

import entity.IFamily;
import entity.TarenaFamily;

public class CallFunctions {

	public static void main(String[] args) {
		IFamily	family	= new TarenaFamily();
		MyProtocol.serverIp="127.0.0.1";
		MyProtocol.port= 8000;
		
		family.goParty();
	}
}
