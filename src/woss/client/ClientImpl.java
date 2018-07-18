package woss.client;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.woss.client.Client;

import util.BasicConfigToolProperties;

public class ClientImpl implements Client {
	private Socket socket;

	@Override
	public void init(Properties properties) {
	}

	@Override
	public void send(Collection<BIDR> collection) throws Exception {
		System.out.println("-------客户端---------------");

		String serverIP = BasicConfigToolProperties.getValue("ServerIP");
		int port = Integer.valueOf(BasicConfigToolProperties.getValue("port"));
//		System.out.println(serverIP + " " + port);
		socket = new Socket(serverIP, port);

		// 对象序列化
		// 序列化流，利用输出流向文件中写入对象
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		System.out.println("开始进行序列化！");
		oos.writeObject(collection);
		System.out.println("序列化已结束！");

		// 刷新缓冲
		oos.flush();
		// 关闭该流
		oos.close();
		socket.close();
		System.out.println("END OF ClientIMPL send()");

	}

}
