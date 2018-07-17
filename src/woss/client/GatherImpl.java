package woss.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.woss.client.Gather;

public class GatherImpl implements Gather {

	@Override
	public void init(Properties arg0) {
	}

	/*
	 * 采集方法 功能：从数据文件中获取到一共数据清单 实现思路： 1.准备一共List集合，用来存放BIDR对象 2.准备两个Map集合 1)第一个
	 * 标志第一个对象 7 IP,1 第一个用户 7 IP,2 7 IP,3 8 IP,3 8 IP,2 8 IP,1 最后一个用户
	 * 
	 * 2)存放不完整的BIDR对象 上线的信息 下线的信息
	 * 
	 */
	@Override
	public Collection<BIDR> gather() throws Exception {

		List<BIDR> list = new ArrayList<>();
		Map<String, Timestamp> map = new HashMap<>();
		int count = 0;
		int count2 = 0;

//		获取文件相对路径字节流对象
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/file/radwtmp1000");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));

		String string;
		while ((string = bufferedReader.readLine()) != null) {
			BIDR bidr = new BIDR();

//			System.out.println(string);
			String[] split = string.split("[|]");

			// 处理bidr上线的数据 :aaa nas ip 上线时间
			if (Integer.valueOf(split[2]) == 7) {
				// AAA服务器
				bidr.setAAA_login_name(split[0]);
				// NAS服务器
				bidr.setNAS_ip(split[1]);
				// 上线ip
				bidr.setLogin_ip(split[4]);
				// 上线时间

//			System.out.println("上线");				
				bidr.setLogin_date(new Timestamp(Long.valueOf(split[3]) * 1000));

//				System.out.println("" + bidr.getLogin_ip() + "----------" + bidr.getLogin_date());
				// list集合里的是所有上线的ip
				list.add(bidr);
				count2++;

			}

			// 下线 属性集合 ip和下线时间
			else if (Integer.valueOf(split[2]) == 8) {
				map.put(split[4], new Timestamp(Long.valueOf(split[3]) * 1000));
			}
		}

		for (BIDR bidr : list) {
			if (map.get(bidr.getLogin_ip()) != null) {
				// 给bidr对象设置 下线时间和时长
				bidr.setLogout_date(map.get(bidr.getLogin_ip()));

				bidr.setTime_deration((int) (bidr.getLogout_date().getTime() - bidr.getLogin_date().getTime()) / 1000);

			} else {
				// 记录没下线的人

				count++;
			}

			System.out.println("AAA服务名:  " + bidr.getAAA_login_name() + "NAS服务器IP地址: " + bidr.getNAS_ip() + " 上线IP地址: "
					+ bidr.getLogin_ip() + "上线时间: " + bidr.getLogin_date() + "下线时间: " + bidr.getLogout_date() + "上线时长: "
					+ bidr.getTime_deration() + "秒");
		}
//		遍历map集合
//		Set<Entry<String, Timestamp>> entrySet = map.entrySet();
//		for (Entry<String, Timestamp> entry : entrySet) {
//			System.out.println("entry:"+entry.getKey()+" "+entry.getValue());
//		}
		System.out.println("没下线的:" + count);
		System.out.println(count2);
		return list;
	}

	// test
	public static void main(String[] args) {
		GatherImpl gatherImpl = new GatherImpl();
		try {
			gatherImpl.gather();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
