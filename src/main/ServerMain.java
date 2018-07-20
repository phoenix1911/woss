package main;

import java.util.Collection;

import com.briup.util.BIDR;
import com.briup.util.Logger;

import util.BackUpImpl;
import util.ConfigurationImpl;
import woss.server.DBStoreImpl;
import woss.server.ServerImpl;

public class ServerMain {
	public static void main(String[] args) throws Exception {
		// ���������� new ServerImpl
		// ������ new DBStroImpl
		ConfigurationImpl conf = new ConfigurationImpl();
		ServerImpl serverImpl = null;
		DBStoreImpl dbStoreImpl = null;
		Logger logger = null;
		BackUpImpl backUpImpl = null;
		Collection<BIDR> revicer = null;

		String backfile = "src/file/backfile.txt";
		try {
			serverImpl = (ServerImpl) conf.getServer();
			dbStoreImpl = (DBStoreImpl) conf.getDBStore();
			logger = conf.getLogger();
			backUpImpl = (BackUpImpl) conf.getBackup();

//			// �Ƚ���һ�س����쳣û�в��뵽���ݿ�����ݲ��뵽���ݿ�
			
				Object obj = null;
				if ((obj = backUpImpl.load(backfile, true)) != null) {
					Collection<BIDR> coll = (Collection<BIDR>) obj;
					logger.info("��ʼ��ȡ�����ļ�����...");
//							System.out.println("��ʼ��ȡ�����ļ�����...");
					dbStoreImpl.saveToDB(coll);
					logger.info("�����ļ��������ɹ�...");
//							System.out.println("�����ļ��������ɹ�...");
				}
			
			// ���մӿͻ��˵�����
			revicer = serverImpl.revicer();
			logger.info("�������ѽ������ݼ��� ��" + revicer.size() + "������");
//			���
			logger.info("���ݿ�ʼ���");
			dbStoreImpl.saveToDB(revicer);
			logger.info("�������ɹ�");
		} catch (Exception e) {
			logger.warn("������ⷢ���쳣");
			logger.info("��ʼ�����ļ�");

			try {
				backUpImpl.store(backfile, revicer, false);
				logger.info("�������");
			} catch (Exception e1) {
				logger.warn("�����쳣");
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (serverImpl != null) {
				serverImpl.shutdown();

			}
		}
	}
}
