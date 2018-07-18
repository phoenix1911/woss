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
		System.out.println("服务器");
		String port = BasicConfigToolProperties.getValue("port");
		serverSocket = new ServerSocket(Integer.valueOf(port));
		Socket socket = serverSocket.accept();
		System.out.println("socket" + socket);
		// 新建一个集合用于接收从备份文件中读取的BIDR对象
		ArrayList<BIDR> arrayList = new ArrayList<>();
		// 对象反序列化
		// 反序列化流，利用输入流从文件中读取对象
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//		System.out.println("开始进行反序列化！");
		BIDR bidr = null;
		try {
			// 注意:当读取到末尾时 会报出EOFException的异常 将此异常捕获 而不是抛出 程序则可以正常运行
			while ((bidr = (BIDR) ois.readObject()) != null) {
				arrayList.add(bidr);
			}
		}catch (Exception e) {
			new BackUpImpl().store("src/file/backup.txt", new GatherImpl2().gather(), false);
			e.printStackTrace();
		}
		System.out.println("反序列化结束！");

		// 关闭资源
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

		// 返回一个集合
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
