package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BasicConfigToolProperties {
	private static Properties properties;
	
	static {
		properties = new Properties();
		try {
			properties.load(new FileInputStream("src/file/basicConfigTool.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	static public String getValue(String key) {
		return properties.getProperty(key);
	}
	
////	test
//	public static void main(String[] args) throws IOException {
//		
//		System.out.println(BasicConfigToolProperties.getValue("port"));
//		System.out.println(ConnectionFactory.getConnection());
//	}
}
