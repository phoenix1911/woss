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
		System.out.println("-------�ͻ���---------------");

//		String serverIP = BasicConfigToolProperties.getValue("ServerIP");
//		int port = (BasicConfigToolProperties.getValue("port"));
		System.out.println("�ͻ��� serverIp,port:"+serverIp+" "+port);
		logger.debug("�ͻ��� serverIp,port:"+serverIp+" "+port);
		socket = new Socket(serverIp, Integer.valueOf(port));
		
		

		// �������л�
		// ���л�����������������ļ���д�����
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		System.out.println("��ʼ�������л���");
		oos.writeObject(collection);
		System.out.println("���л��ѽ�����");

		// ˢ�»���
		oos.flush();
		// �رո���
		oos.close();
		socket.close();
		System.out.println("END OF ClientIMPL send()");

	}

}
