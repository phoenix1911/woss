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
		System.out.println("-------�ͻ���---------------");

		String serverIP = BasicConfigToolProperties.getValue("ServerIP");
		int port = Integer.valueOf(BasicConfigToolProperties.getValue("port"));
//		System.out.println(serverIP + " " + port);
		socket = new Socket(serverIP, port);

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
