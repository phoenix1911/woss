package woss.server;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.briup.util.BIDR;
import com.briup.woss.server.DBStore;

import util.ConnectionFactory;
import woss.client.GatherImpl2;

public class DBStoreImpl implements DBStore {

	@Override
	public void init(Properties properties) {
	}

	/**
	 * 	保存方法
	 *	主要功能:将数据清单 保存在数据库
	 *  jdbc
	 * 	先获取今天的日期
	 */
	@Override
	public void saveToDB(Collection<BIDR> collection) throws Exception {
		
		Logger logger = Logger.getLogger(DBStoreImpl.class);
		PropertyConfigurator.configure("src/util/log4j.properties");
		
		Connection connection = ConnectionFactory.getConnection();
		//今天的日期
		String[] split = new java.util.Date().toString().split(" ");
		String today= split[2];
		String sql= "insert into T_DETAIL_"+today+" values(?,?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
//		System.out.println(collection+" "+ps);
		logger.warn("入库操作开始");
		for (BIDR bidr : collection) {
			String AAA = bidr.getAAA_login_name();
			String login_ip = bidr.getLogin_ip();
			Date logindate = new Date(bidr.getLogin_date().getTime());
			Date logoutdate = new Date(bidr.getLogout_date().getTime());
			String nas_ip = bidr.getNAS_ip();
			int time_deration = bidr.getTime_deration();
			
			ps.setString(1, AAA);
			ps.setString(2, login_ip);
			ps.setDate(3, logindate);
			ps.setDate(4, logoutdate);
			ps.setString(5, nas_ip);
			ps.setInt(6, time_deration);
			ps.execute();
		}
		logger.warn("入库操作结束");
		
	}
	
//	public static void main(String[] args) {
//		try {
//			Collection<BIDR> gather = new GatherImpl2().gather();
//			DBStoreImpl dbStoreImpl = new DBStoreImpl();
//			dbStoreImpl.saveToDB(gather);
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//		
//	}


	

}
