package entity;

import java.io.IOException;

import communication.MyProtocol;

public class TarenaFamily extends Family {
	public void goParty(){
		System.out.println("Let's rock!");
		//��������������������������������������Э�齻�����ݣ�����������Ϊ0ʱ��ʾ�޴��Σ�
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
