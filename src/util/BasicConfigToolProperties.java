package util;

import java.io.IOException;
import java.util.Properties;

public class BasicConfigToolProperties {
	private static Properties properties;
	
	static {
		properties = new Properties();
		try {
			properties.load(BasicConfigToolProperties.class.getResourceAsStream("basicConfigTool.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	static public String getValue(String key) {
		return properties.getProperty(key);
	}
	
//	test
//	public static void main(String[] args) throws IOException {
//		Properties properties3 = new Properties();
//		properties3.load(BasicConfigToolProperties.class.getResourceAsStream("basicConfigTool.properties"));
//		System.out.println(properties3.getProperty("port"));
//		
//	}
}
