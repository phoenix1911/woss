package main;

import java.util.Collection;

import com.briup.util.BIDR;
import util.ConfigurationImpl;

import woss.server.DBStoreImpl;
import woss.server.ServerImpl;

public class ServerMain {
	public static void main(String[] args) throws Exception {
		// 服务器对象 new ServerImpl
		// 入库对象 new DBStroImpl
		ConfigurationImpl conf = new ConfigurationImpl();
		ServerImpl serverImpl = (ServerImpl) conf.getServer();
		DBStoreImpl dbStoreImpl = (DBStoreImpl) conf.getDBStore();

		try {
			Collection<BIDR> revicer = serverImpl.revicer();
//			入库
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
