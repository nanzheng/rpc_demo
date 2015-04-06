package entity;

import java.io.IOException;

import communication.MyProtocol;

public class TarenaFamily extends Family {
	public void goParty(){
		System.out.println("Let's rock!");
		//传递类名、方法名、参数个数、各个参数给协议交换数据（当参数个数为0时表示无传参）
		try {
			String[] strs = MyProtocol.exchange(this.getClass().toString().substring(6),"goParty");
			for(String str : strs){
				System.out.println(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
