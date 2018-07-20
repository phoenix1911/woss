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
		// 服务器对象 new ServerImpl
		// 入库对象 new DBStroImpl
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

//			// 先将上一回出现异常没有插入到数据库的数据插入到数据库
			
				Object obj = null;
				if ((obj = backUpImpl.load(backfile, true)) != null) {
					Collection<BIDR> coll = (Collection<BIDR>) obj;
					logger.info("开始读取备份文件数据...");
//							System.out.println("开始读取备份文件数据...");
					dbStoreImpl.saveToDB(coll);
					logger.info("备份文件数据入库成功...");
//							System.out.println("备份文件数据入库成功...");
				}
			
			// 接收从客户端的数据
			revicer = serverImpl.revicer();
			logger.info("服务器已接收数据集合 共" + revicer.size() + "条数据");
//			入库
			logger.info("数据开始入库");
			dbStoreImpl.saveToDB(revicer);
			logger.info("数据入库成功");
		} catch (Exception e) {
			logger.warn("数据入库发生异常");
			logger.info("开始备份文件");

			try {
				backUpImpl.store(backfile, revicer, false);
				logger.info("备份完成");
			} catch (Exception e1) {
				logger.warn("备份异常");
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
