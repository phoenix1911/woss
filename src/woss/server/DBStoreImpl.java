package woss.server;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.server.DBStore;

import util.ConfigurationImpl;
import util.ConnectionFactory;
import util.LoggerImpl;

public class DBStoreImpl implements DBStore, ConfigurationAWare {

	// 日志对象
	private Logger logger;// = new LoggerImpl();
	// 配置对象
	private Configuration conf;

	@Override
	public void setConfiguration(Configuration arg0) {
		this.conf = arg0;
	}


	@Override
	public void init(Properties properties) {
		try {
			logger = conf.getLogger();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * 保存方法 主要功能:将数据清单 保存在数据库 jdbc 先获取今天的日期
	 */
	@Override
	public void saveToDB(Collection<BIDR> collection) throws Exception {

		Connection connection = ConnectionFactory.getConnection();
		// 今天的日期
		String[] split = new java.util.Date().toString().split(" ");
		String today = split[2];
		String sql = "insert into T_DETAIL_" + today + " values(?,?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
//		System.out.println(collection+" "+ps);
		logger.info("入库操作开始");
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
		logger.info("入库操作结束");

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
