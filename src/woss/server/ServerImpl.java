package woss.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.briup.util.BIDR;
import com.briup.woss.server.Server;

import util.BasicConfigToolProperties;

public class ServerImpl implements Server {
	private ServerSocket serverSocket;

	@Override
	public void init(Properties arg0) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BIDR> revicer() throws Exception {
		//日志
		Logger logger = Logger.getLogger(ServerImpl.class);
		PropertyConfigurator.configure("src/util/log4j.properties");
		
		logger.info("服务器启动");
		String port = BasicConfigToolProperties.getValue("port");
		serverSocket = new ServerSocket(Integer.valueOf(port));
		Socket socket = serverSocket.accept();
		
		logger.info("socket:"+ socket);
		logger.warn("反序列化开始！");
		
		ObjectInputStream objectinputStream = new ObjectInputStream(socket.getInputStream());
		List<BIDR> list= (List<BIDR>)objectinputStream.readObject();
//		for (BIDR bidr : list) {
//			System.out.println(bidr.getLogin_ip());
//		}
		
		logger.warn("反序列化结束！");

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
