package woss.server;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.server.DBStore;

public class DBStoreImpl implements DBStore, ConfigurationAWare {

	// ��־����
	private Logger logger;
	// ���ö���
	private Configuration conf;

	private String driver;
	private String url;
	private String user;
	private String password;

	@Override
	public void setConfiguration(Configuration arg0) {
		this.conf = arg0;
	}

	@Override
	public void init(Properties properties) {
		driver = properties.getProperty("driver");
		System.out.println(driver);
		url = properties.getProperty("url");
		user = properties.getProperty("username");
		password = properties.getProperty("password");

		try {
			logger = conf.getLogger();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * ���淽�� ��Ҫ����:�������嵥 ���������ݿ� jdbc �Ȼ�ȡ���������
	 */
	@Override
	public void saveToDB(Collection<BIDR> collection) throws Exception {

		Connection connection = null;
		PreparedStatement ps = null;
		try {

			// 1��ע���������Զ�ע��
			Class.forName(driver);
			
			logger.debug(driver + " " + url + " " + user + " " + password);
			// 2����ȡ����
			connection = DriverManager.getConnection(url, user, password);
			
			
			// ���������
			String[] split = new java.util.Date().toString().split(" ");
			String today = split[2];
			String sql = "insert into T_DETAIL_" + today + " values(?,?,?,?,?,?)";
			ps = connection.prepareStatement(sql);
//		System.out.println(collection+" "+ps);
			logger.info("��������ʼ");
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

			logger.info("����������");
		} finally {
			if (ps!=null) {
				ps.close();
			}
			if (connection!=null) {
				connection.close();
				
			}
		} 

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
