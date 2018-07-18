package main;

import java.util.Collection;

import com.briup.util.BIDR;

import woss.server.DBStoreImpl;
import woss.server.ServerImpl;

public class ServerMain {
	public static void main(String[] args) {
		// ���������� new ServerImpl
		// ������ new DBStroImpl
		ServerImpl serverImpl = new ServerImpl();
		DBStoreImpl dbStoreImpl = new DBStoreImpl();

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
