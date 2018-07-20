package main;

import util.ConfigurationImpl;
import woss.client.ClientImpl;
import woss.client.GatherImpl2;

public class clientMain {
	public static void main(String[] args) throws Exception {
		// 客户端对象 -->调用send方法传入集合.
		// 采集模块对象-->获取集合

//		需要先创建一个配置文件对象
		ConfigurationImpl conf = new ConfigurationImpl();

		GatherImpl2 gatherImpl2 = (GatherImpl2) conf.getGather();
		ClientImpl clientImpl = (ClientImpl) conf.getClient();

		try {
			// 发送集合
			clientImpl.send(gatherImpl2.gather());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
