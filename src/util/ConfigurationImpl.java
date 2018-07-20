package util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.briup.util.BackUP;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.WossModule;
import com.briup.woss.client.Client;
import com.briup.woss.client.Gather;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;

import woss.client.GatherImpl2;
import woss.server.DBStoreImpl;

/*
 * map ���ÿ��ģ���Ķ���
 * key �ӱ�ǩ������ 
 * value �������
 * 
 * Properties ���ÿ��ģ���������Ϣ
 * key:������ǩ������: �� data-file
 * value: ������Ϣ
 * 
 * ����xml��һ������ ����map����
 */
public class ConfigurationImpl implements Configuration {

	private Map<String, WossModule> map;
	private Properties properties;

	public ConfigurationImpl() throws Exception {

		map = new HashMap<>();
		// ������
		SAXReader reader = new SAXReader();
		// ָ��xml�ļ�
		Document document = reader.read(new File("src/file/conf.xml"));
		// ��ȡ���ڵ�
		Element rootElement = document.getRootElement();

		List<Element> rootBabyElements = rootElement.elements();
		Properties properties = new Properties();
//		properties.load(new FileInputStream(new File("src/file/basicConfigTool.properties")));
		// �����ڵ�
		for (Element element : rootBabyElements) {
			String name = element.getName();
			String value = element.attribute("class").getValue();
			System.out.println(name);
			System.out.println(value);

			Class<?> class1 = Class.forName(value);
			WossModule newInstance = (WossModule) class1.newInstance();
			map.put(name, newInstance);

			// �����ڵ�
			List<Element> elements = element.elements();
//			OutputStream outputStream = new FileOutputStream("src/file/basicConfigTool.properties");
			for (Element element2 : elements) {
				String name2 = element2.getName();
				String text = element2.getText();
				System.out.println("�����ڵ�:" + name2 + " " + text);
				properties.setProperty(name2, text);
				System.out.println(properties.getProperty(name2));
//				properties.store(outputStream, name2);
			}
		}

		System.out.println(map);

		System.out.println(properties);
		for (String key : map.keySet()) {
			WossModule woss = map.get(key);
			// ��ʼ�� ���ö���
			if (woss instanceof ConfigurationAWare) {
				((ConfigurationAWare) woss).setConfiguration(this);
			}
			// ��ʼ�� ������Ϣ
			if (woss instanceof WossModule) {
				woss.init(properties);
			}
		}

	}

//	@SuppressWarnings("unchecked")
//	public void parseXML()
//			throws Exception {
//		map = new HashMap<>();
//		// ������
//		SAXReader reader = new SAXReader();
//		// ָ��xml�ļ�
//		Document document = reader.read(new File("src/file/conf.xml"));
//		// ��ȡ���ڵ�
//		Element rootElement = document.getRootElement();
//
//		List<Element> rootBabyElements = rootElement.elements();
//		Properties properties = new Properties();
////		properties.load(new FileInputStream(new File("src/file/basicConfigTool.properties")));
//		// �����ڵ�
//		for (Element element : rootBabyElements) {
//			String name = element.getName();
//			String value = element.attribute("class").getValue();
//			System.out.println(name);
//			System.out.println(value);
//
//			Class<?> class1 = Class.forName(value);
//			WossModule newInstance = (WossModule) class1.newInstance();
//			map.put(name, newInstance);
//			
//			// �����ڵ�
//			List<Element> elements = element.elements();
////			OutputStream outputStream = new FileOutputStream("src/file/basicConfigTool.properties");
//			for (Element element2 : elements) {
//				String name2 = element2.getName();
//				String text = element2.getText();
//				System.out.println("�����ڵ�:" + name2+" "+text);
//				properties.setProperty(name2, text);
//				System.out.println(properties.getProperty(name2));
////				properties.store(outputStream, name2);
//			}
//		}
//		
//		System.out.println(map);
//	}

	@Override
	public BackUP getBackup() throws Exception {

		return (BackUP) map.get("backup");
	}

	@Override
	public Client getClient() throws Exception {

		return (Client) map.get("client");
	}

	@Override
	public DBStore getDBStore() throws Exception {

		return (DBStore) map.get("dbstore");
	}

	@Override
	public Gather getGather() throws Exception {

		return (Gather) map.get("gather");

	}

	@Override
	public Logger getLogger() throws Exception {

		return (Logger) map.get("logger");
	}

	@Override
	public Server getServer() throws Exception {

		return (Server) map.get("server");
	}

	public static void main(String[] args) throws Exception {
//		DBStoreImpl dbStore = (DBStoreImpl) new ConfigurationImpl().getDBStore();
//		dbStore.saveToDB(new GatherImpl2().gather());
//		Server server = new ConfigurationImpl().getServer();
//		server.revicer();
		new ConfigurationImpl();
	}
}
