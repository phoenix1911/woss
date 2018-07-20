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
 * map 存放每个模块快的对象
 * key 子标签的名字 
 * value 反射对象
 * 
 * Properties 存放每个模块的配置信息
 * key:三级标签的名字: 如 data-file
 * value: 配置信息
 * 
 * 解析xml的一个方法 充满map集合
 */
public class ConfigurationImpl implements Configuration {

	private Map<String, WossModule> map;
	private Properties properties;

	public ConfigurationImpl() throws Exception {

		map = new HashMap<>();
		// 解析器
		SAXReader reader = new SAXReader();
		// 指定xml文件
		Document document = reader.read(new File("src/file/conf.xml"));
		// 获取根节点
		Element rootElement = document.getRootElement();

		List<Element> rootBabyElements = rootElement.elements();
		Properties properties = new Properties();
//		properties.load(new FileInputStream(new File("src/file/basicConfigTool.properties")));
		// 二级节点
		for (Element element : rootBabyElements) {
			String name = element.getName();
			String value = element.attribute("class").getValue();
			System.out.println(name);
			System.out.println(value);

			Class<?> class1 = Class.forName(value);
			WossModule newInstance = (WossModule) class1.newInstance();
			map.put(name, newInstance);

			// 三级节点
			List<Element> elements = element.elements();
//			OutputStream outputStream = new FileOutputStream("src/file/basicConfigTool.properties");
			for (Element element2 : elements) {
				String name2 = element2.getName();
				String text = element2.getText();
				System.out.println("三级节点:" + name2 + " " + text);
				properties.setProperty(name2, text);
				System.out.println(properties.getProperty(name2));
//				properties.store(outputStream, name2);
			}
		}

		System.out.println(map);

		System.out.println(properties);
		for (String key : map.keySet()) {
			WossModule woss = map.get(key);
			// 初始化 配置对象
			if (woss instanceof ConfigurationAWare) {
				((ConfigurationAWare) woss).setConfiguration(this);
			}
			// 初始化 配置信息
			if (woss instanceof WossModule) {
				woss.init(properties);
			}
		}

	}

//	@SuppressWarnings("unchecked")
//	public void parseXML()
//			throws Exception {
//		map = new HashMap<>();
//		// 解析器
//		SAXReader reader = new SAXReader();
//		// 指定xml文件
//		Document document = reader.read(new File("src/file/conf.xml"));
//		// 获取根节点
//		Element rootElement = document.getRootElement();
//
//		List<Element> rootBabyElements = rootElement.elements();
//		Properties properties = new Properties();
////		properties.load(new FileInputStream(new File("src/file/basicConfigTool.properties")));
//		// 二级节点
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
//			// 三级节点
//			List<Element> elements = element.elements();
////			OutputStream outputStream = new FileOutputStream("src/file/basicConfigTool.properties");
//			for (Element element2 : elements) {
//				String name2 = element2.getName();
//				String text = element2.getText();
//				System.out.println("三级节点:" + name2+" "+text);
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
