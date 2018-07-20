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
//	//备份文件路径
//	private String backPath;
	//配置对象
	private Configuration configuration;
	//日志对象
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
	 * 通过键名获取已经备份的数据 
	 * key - 备份数据的键 
	 * flag - 取出备份数据时，是否保留备份。建议使用常量值。
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
			logger.debug("第"+(count++)+"条数据 "+bidr.getLogin_ip());
		}
		if(flag) {
			File file = new File(key);
			file.delete();
		}
		return list2;
	}

	/**
	 * 通过键名来存储数据。
	 *  参数： 
	 *  key - 备份数据的键 
	 *  data - 需要备份的数据
	 *  flag -如果键值已经存在数据，追加还是覆盖之前的数据。建议使用常量值。
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
