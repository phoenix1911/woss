package woss.client;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.client.Client;

public class ClientImpl implements Client,ConfigurationAWare{
	private Configuration conf;
	private Logger logger;
	private Socket socket;
	private String serverIp;
	private String port;
	@Override
	public void setConfiguration(Configuration arg0) {
		this.conf = arg0;
	}

	@Override
	public void init(Properties properties) {
		serverIp = properties.getProperty("server-ip");
		port = properties.getProperty("port");		
		try {
			logger = conf.getLogger();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void send(Collection<BIDR> collection) throws Exception {
		System.out.println("-------客户端---------------");

//		String serverIP = BasicConfigToolProperties.getValue("ServerIP");
//		int port = (BasicConfigToolProperties.getValue("port"));
		System.out.println("客户端 serverIp,port:"+serverIp+" "+port);
		logger.debug("客户端 serverIp,port:"+serverIp+" "+port);
		socket = new Socket(serverIp, Integer.valueOf(port));
		
		

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
