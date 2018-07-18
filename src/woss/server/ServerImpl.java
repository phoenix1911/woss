package woss.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.woss.server.Server;

import util.BackUpImpl;
import util.BasicConfigToolProperties;
import woss.client.GatherImpl2;

public class ServerImpl implements Server {
	private ServerSocket serverSocket;

	@Override
	public void init(Properties arg0) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BIDR> revicer() throws Exception {
		System.out.println("服务器");
		String port = BasicConfigToolProperties.getValue("port");
		serverSocket = new ServerSocket(Integer.valueOf(port));
		Socket socket = serverSocket.accept();
		System.out.println("socket" + socket);
		
		
		System.out.println("反序列化开始！");
		
		ObjectInputStream objectinputStream = new ObjectInputStream(socket.getInputStream());
		List<BIDR> list= (List<BIDR>)objectinputStream.readObject();
//		for (BIDR bidr : list) {
//			System.out.println(bidr.getLogin_ip());
//		}
		
		System.out.println("反序列化结束！");

		// 关闭资源
		try {
			if (objectinputStream != null)
				objectinputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 返回一个集合
		return list;
	}

	@Override
	public void shutdown() {
		try {
			if (serverSocket != null)
				serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
