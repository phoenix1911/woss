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

public class GatherImpl2 implements Gather {

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
		Map<String, BIDR> map = new HashMap<>();
		Map<String, Integer> onlineNum = new HashMap<>();

//		IP数
		int count = 0;

//		获取文件相对路径字节流对象
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/file/radwtmp");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));

		String string;
		while ((string = bufferedReader.readLine()) != null) {
			BIDR bidr = new BIDR();
			String[] split = string.split("[|]");

			// 如果是没有记录过的ip 且是上线 将AAA NAS IP 上线时间保存为bidr对象
			if (!onlineNum.containsKey(split[4]) && Integer.valueOf(split[2]) == 7) {
				// AAA服务器
				bidr.setAAA_login_name(split[0]);
				// NAS服务器
				bidr.setNAS_ip(split[1]);
				// 上线ip
				bidr.setLogin_ip(split[4]);
				// 起始上线时间
				bidr.setLogin_date(new Timestamp(Long.valueOf(split[3]) * 1000));
				// 将在线计数器置为1
				onlineNum.put(bidr.getLogin_ip(), 1);
//				list.add(bidr);
				map.put(split[4], bidr);// 初始化ip的下线时间

			}
			// 如果遇见存在的IP且也是上线 在线计数器+1
			else if (onlineNum.containsKey(split[4]) && Integer.valueOf(split[2]) == 7) {
				onlineNum.put(split[4], onlineNum.get(split[4]) + 1);
				System.out.println("计数器+1");
			}
			// 如果遇见存在的IP且也是上线 在线计数器-1
			else if (Integer.valueOf(split[2]) == 8) {
				BIDR bidr2;
				if (onlineNum.get(split[4])==1) {
					bidr2=map.get(split[4]);
					//用户下线时间
					bidr2.setLogout_date(new Timestamp(Long.valueOf(split[3]) * 1000));
					//用户在线时长
					bidr2.setTime_deration((int) (bidr2.getLogout_date().getTime() - bidr2.getLogin_date().getTime()) / 1000);
					list.add(bidr2);
					
					map.remove(split[4]);
					onlineNum.remove(split[4]);
					
					
				}else {
					onlineNum.put(split[4], onlineNum.get(split[4]) - 1);
					System.out.println("计数器-1");					
				}
			}
			
		}
		for (BIDR bidr : list) {
			System.out.println("AAA服务名:  " + bidr.getAAA_login_name() + "NAS服务器IP地址: " + bidr.getNAS_ip() + " 上线IP地址: "
					+ bidr.getLogin_ip() + "上线时间: " + bidr.getLogin_date() + "下线时间: " + bidr.getLogout_date() + "上线时长: "
					+ bidr.getTime_deration() + "秒");
		}

		System.out.println("IP数" + list.size());
		System.out.println("没有下线的IP:"+map.size());

		return list;
	}

	// test
	public static void main(String[] args) {
		GatherImpl2 gatherImpl = new GatherImpl2();
		System.out.println(new Timestamp(0).getTime());
		try {
			gatherImpl.gather();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
