package main;

import woss.client.ClientImpl;
import woss.client.GatherImpl2;

public class clientMain {
	public static void main(String[] args) {
		// 客户端对象 -->调用send方法传入集合.
		// 采集模块对象-->获取集合
		GatherImpl2 gatherImpl2 = new GatherImpl2();
		ClientImpl clientImpl = new ClientImpl();

		try {
			// 发送集合
			clientImpl.send(gatherImpl2.gather());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
