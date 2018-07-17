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
	 * �ɼ����� ���ܣ��������ļ��л�ȡ��һ�������嵥 ʵ��˼·�� 1.׼��һ��List���ϣ��������BIDR���� 2.׼������Map���� 1)��һ��
	 * ��־��һ������ 7 IP,1 ��һ���û� 7 IP,2 7 IP,3 8 IP,3 8 IP,2 8 IP,1 ���һ���û�
	 * 
	 * 2)��Ų�������BIDR���� ���ߵ���Ϣ ���ߵ���Ϣ
	 * 
	 */
	@Override
	public Collection<BIDR> gather() throws Exception {

		List<BIDR> list = new ArrayList<>();
		Map<String, Timestamp> map = new HashMap<>();
		int count = 0;
		int count2 = 0;

//		��ȡ�ļ����·���ֽ�������
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/file/radwtmp1000");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));

		String string;
		while ((string = bufferedReader.readLine()) != null) {
			BIDR bidr = new BIDR();

//			System.out.println(string);
			String[] split = string.split("[|]");

			// ����bidr���ߵ����� :aaa nas ip ����ʱ��
			if (Integer.valueOf(split[2]) == 7) {
				// AAA������
				bidr.setAAA_login_name(split[0]);
				// NAS������
				bidr.setNAS_ip(split[1]);
				// ����ip
				bidr.setLogin_ip(split[4]);
				// ����ʱ��

//			System.out.println("����");				
				bidr.setLogin_date(new Timestamp(Long.valueOf(split[3]) * 1000));

//				System.out.println("" + bidr.getLogin_ip() + "----------" + bidr.getLogin_date());
				// list����������������ߵ�ip
				list.add(bidr);
				count2++;

			}

			// ���� ���Լ��� ip������ʱ��
			else if (Integer.valueOf(split[2]) == 8) {
				map.put(split[4], new Timestamp(Long.valueOf(split[3]) * 1000));
			}
		}

		for (BIDR bidr : list) {
			if (map.get(bidr.getLogin_ip()) != null) {
				// ��bidr�������� ����ʱ���ʱ��
				bidr.setLogout_date(map.get(bidr.getLogin_ip()));

				bidr.setTime_deration((int) (bidr.getLogout_date().getTime() - bidr.getLogin_date().getTime()) / 1000);

			} else {
				// ��¼û���ߵ���

				count++;
			}

			System.out.println("AAA������:  " + bidr.getAAA_login_name() + "NAS������IP��ַ: " + bidr.getNAS_ip() + " ����IP��ַ: "
					+ bidr.getLogin_ip() + "����ʱ��: " + bidr.getLogin_date() + "����ʱ��: " + bidr.getLogout_date() + "����ʱ��: "
					+ bidr.getTime_deration() + "��");
		}
//		����map����
//		Set<Entry<String, Timestamp>> entrySet = map.entrySet();
//		for (Entry<String, Timestamp> entry : entrySet) {
//			System.out.println("entry:"+entry.getKey()+" "+entry.getValue());
//		}
		System.out.println("û���ߵ�:" + count);
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
