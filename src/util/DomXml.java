package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.briup.woss.WossModule;

public class DomXml {
	public Map<String, WossModule> getXmlObject() throws Exception {
		Map<String, WossModule> map = new HashMap<>();
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
				System.out.println("***2:" + name2);
				String text = element2.getText();
				System.out.println(text);

				properties.setProperty(name2, text);
//				properties.store(outputStream, name2);
			}
		}

		System.out.println(map);
		return map;
	}
//	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, DocumentException, FileNotFoundException, IOException {
//		DomXml domXml = new DomXml();
//		domXml.getXmlObject();
//	}
}
