package woss.client;

import java.util.Collection;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.woss.client.Gather;

public class GatherImpl implements Gather {

	@Override
	public void init(Properties arg0) {
	}

	/*
	 * 采集方法
	 * 功能：从数据文件中获取到一共数据清单
	 * 实现思路：
	 * 1.准备一共List集合，用来存放BIDR对象
	 * 2.准备两个Map集合
	 * 		1)第一个 标志第一个对象
	 * 		7	IP,1	第一个用户
	 * 		7	IP,2
	 * 		7	IP,3
	 * 		8	IP,3
	 * 		8	IP,2
	 * 		8	IP,1	最后一个用户
	 * 
	 * 		2)存放不完整的BIDR对象
	 * 			上线的信息
	 * 			下线的信息
	 * 
	 * */
	@Override
	public Collection<BIDR> gather() throws Exception {

		return null;
	}

}
