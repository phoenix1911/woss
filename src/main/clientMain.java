package main;

import util.ConfigurationImpl;
import woss.client.ClientImpl;
import woss.client.GatherImpl2;

public class clientMain {
	public static void main(String[] args) throws Exception {
		// �ͻ��˶��� -->����send�������뼯��.
		// �ɼ�ģ�����-->��ȡ����

//		��Ҫ�ȴ���һ�������ļ�����
		ConfigurationImpl conf = new ConfigurationImpl();

		GatherImpl2 gatherImpl2 = (GatherImpl2) conf.getGather();
		ClientImpl clientImpl = (ClientImpl) conf.getClient();

		try {
			// ���ͼ���
			clientImpl.send(gatherImpl2.gather());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
