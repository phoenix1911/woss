package woss.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
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

	@Override
	public Collection<BIDR> revicer() throws Exception {
		System.out.println("������");
		String port = BasicConfigToolProperties.getValue("port");
		serverSocket = new ServerSocket(Integer.valueOf(port));
		Socket socket = serverSocket.accept();
		System.out.println("socket" + socket);
		// �½�һ���������ڽ��մӱ����ļ��ж�ȡ��BIDR����
		ArrayList<BIDR> arrayList = new ArrayList<>();
		// �������л�
		// �����л������������������ļ��ж�ȡ����
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//		System.out.println("��ʼ���з����л���");
		BIDR bidr = null;
		try {
			// ע��:����ȡ��ĩβʱ �ᱨ��EOFException���쳣 �����쳣���� �������׳� �����������������
			while ((bidr = (BIDR) ois.readObject()) != null) {
				arrayList.add(bidr);
			}
		}catch (Exception e) {
			new BackUpImpl().store("src/file/backup.txt", new GatherImpl2().gather(), false);
			e.printStackTrace();
		}
		System.out.println("�����л�������");

		// �ر���Դ
		try {
			if (ois != null)
				ois.close();
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
		return arrayList;
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
