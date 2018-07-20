package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.util.BackUP;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.ConfigurationAWare;

import woss.client.GatherImpl2;

public class BackUpImpl implements BackUP,ConfigurationAWare{
//	//�����ļ�·��
//	private String backPath;
	//���ö���
	private Configuration configuration;
	//��־����
	private Logger logger;
	@Override
	public void setConfiguration(Configuration arg0) {
		this.configuration = arg0;
	}
	

	@Override
	public void init(Properties properties) {
//		backPath = (String) properties.get("back-path");
		try {
			logger = configuration.getLogger();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		System.out.println("BackUpImpl init()");
	}

	/**
	 * ͨ��������ȡ�Ѿ����ݵ����� 
	 * key - �������ݵļ� 
	 * flag - ȡ����������ʱ���Ƿ������ݡ�����ʹ�ó���ֵ��
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object load(String key, boolean flag) throws Exception {
		ObjectInputStream objectinputStream= new ObjectInputStream(new FileInputStream(key));
		List<BIDR> list2= (List<BIDR>) objectinputStream.readObject();
		System.out.println(objectinputStream+"   "+list2);
		objectinputStream.close();
		int count=1;
		for (BIDR bidr : list2) {
			logger.debug("��"+(count++)+"������ "+bidr.getLogin_ip());
		}
		if(flag) {
			File file = new File(key);
			file.delete();
		}
		return list2;
	}

	/**
	 * ͨ���������洢���ݡ�
	 *  ������ 
	 *  key - �������ݵļ� 
	 *  data - ��Ҫ���ݵ�����
	 *  flag -�����ֵ�Ѿ��������ݣ�׷�ӻ��Ǹ���֮ǰ�����ݡ�����ʹ�ó���ֵ��
	 */
	@Override
	public void store(String key, Object data, boolean flag) throws Exception {
		System.out.println("backupimpl store()...");
		ObjectOutputStream objectOutputStream =new ObjectOutputStream(new FileOutputStream(key,false));
		objectOutputStream.writeObject(data);		
		objectOutputStream.close();
	}
//	public static void main(String[] args) throws Exception {
//		String string = "src/file/backfile.txt";
//		ConfigurationImpl configurationImpl = new ConfigurationImpl();
//		Collection<BIDR> gather = configurationImpl.getGather().gather();
//		BackUP backup = configurationImpl.getBackup();
////		backup.store(string, gather, false);
//		backup.load(string, false);
//	}
}
