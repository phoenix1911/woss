package main;

import woss.client.ClientImpl;
import woss.client.GatherImpl2;

public class clientMain {
	public static void main(String[] args) {
		// �ͻ��˶��� -->����send�������뼯��.
		// �ɼ�ģ�����-->��ȡ����
		GatherImpl2 gatherImpl2 = new GatherImpl2();
		ClientImpl clientImpl = new ClientImpl();

		try {
			// ���ͼ���
			clientImpl.send(gatherImpl2.gather());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
