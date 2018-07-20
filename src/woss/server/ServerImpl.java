package woss.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.server.Server;

public class ServerImpl implements Server,ConfigurationAWare{
	//���ö���
	private Configuration conf;
	//��־����
	private Logger logger;
	private ServerSocket serverSocket;
	
	private String port;

	@Override
	public void setConfiguration(Configuration arg0) {
		System.out.println(" serverimpl   setConfiguration()");
		this.conf=arg0;
	}

	@Override
	public void init(Properties arg0) {
		port = arg0.getProperty("port");
		System.out.println("serverImpl init() port "+port);
		try {
			logger = conf.getLogger();
			System.out.println(logger);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BIDR> revicer() throws Exception {
		//��־
//		Logger logger = Logger.getLogger(ServerImpl.class);
//		PropertyConfigurator.configure("src/util/log4j.properties");
		
		logger.info("����������");
		serverSocket = new ServerSocket(Integer.valueOf(port));
		Socket socket = serverSocket.accept();
		
		logger.info("socket:"+ socket);
		logger.info("�����л���ʼ��");
		
		ObjectInputStream objectinputStream = new ObjectInputStream(socket.getInputStream());
		List<BIDR> list= (List<BIDR>)objectinputStream.readObject();
//		for (BIDR bidr : list) {
//			System.out.println(bidr.getLogin_ip());
//		}
		
		logger.info("�����л�������");

		// �ر���Դ
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

		// ����һ������
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
