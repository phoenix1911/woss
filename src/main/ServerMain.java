package main;

import java.util.Collection;

import com.briup.util.BIDR;
import util.ConfigurationImpl;

import woss.server.DBStoreImpl;
import woss.server.ServerImpl;

public class ServerMain {
	public static void main(String[] args) throws Exception {
		// ���������� new ServerImpl
		// ������ new DBStroImpl
		ConfigurationImpl conf = new ConfigurationImpl();
		ServerImpl serverImpl = (ServerImpl) conf.getServer();
		DBStoreImpl dbStoreImpl = (DBStoreImpl) conf.getDBStore();

		try {
			Collection<BIDR> revicer = serverImpl.revicer();
//			���
			dbStoreImpl.saveToDB(revicer);

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (serverImpl != null) {
				serverImpl.shutdown();

			}
		}
	}
}
