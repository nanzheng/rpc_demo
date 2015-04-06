package communication;
//序列化类都会实现此接口

public interface Writable {
	public void writeObject();
	public void readObject();
}
