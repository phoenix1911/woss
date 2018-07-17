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
	 * �ɼ����� ���ܣ��������ļ��л�ȡ��һ�������嵥 ʵ��˼·�� 1.׼��һ��List���ϣ��������BIDR���� 2.׼������Map���� 1)��һ��
	 * ��־��һ������ 7 IP,1 ��һ���û� 7 IP,2 7 IP,3 8 IP,3 8 IP,2 8 IP,1 ���һ���û�
	 * 
	 * 2)��Ų�������BIDR���� ���ߵ���Ϣ ���ߵ���Ϣ
	 * 
	 */
	@Override
	public Collection<BIDR> gather() throws Exception {

		List<BIDR> list = new ArrayList<>();
		Map<String, BIDR> map = new HashMap<>();
		Map<String, Integer> onlineNum = new HashMap<>();

//		IP��
		int count = 0;

//		��ȡ�ļ����·���ֽ�������
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/file/radwtmp");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));

		String string;
		while ((string = bufferedReader.readLine()) != null) {
			BIDR bidr = new BIDR();
			String[] split = string.split("[|]");

			// �����û�м�¼����ip �������� ��AAA NAS IP ����ʱ�䱣��Ϊbidr����
			if (!onlineNum.containsKey(split[4]) && Integer.valueOf(split[2]) == 7) {
				// AAA������
				bidr.setAAA_login_name(split[0]);
				// NAS������
				bidr.setNAS_ip(split[1]);
				// ����ip
				bidr.setLogin_ip(split[4]);
				// ��ʼ����ʱ��
				bidr.setLogin_date(new Timestamp(Long.valueOf(split[3]) * 1000));
				// �����߼�������Ϊ1
				onlineNum.put(bidr.getLogin_ip(), 1);
//				list.add(bidr);
				map.put(split[4], bidr);// ��ʼ��ip������ʱ��

			}
			// ����������ڵ�IP��Ҳ������ ���߼�����+1
			else if (onlineNum.containsKey(split[4]) && Integer.valueOf(split[2]) == 7) {
				onlineNum.put(split[4], onlineNum.get(split[4]) + 1);
				System.out.println("������+1");
			}
			// ����������ڵ�IP��Ҳ������ ���߼�����-1
			else if (Integer.valueOf(split[2]) == 8) {
				BIDR bidr2;
				if (onlineNum.get(split[4])==1) {
					bidr2=map.get(split[4]);
					//�û�����ʱ��
					bidr2.setLogout_date(new Timestamp(Long.valueOf(split[3]) * 1000));
					//�û�����ʱ��
					bidr2.setTime_deration((int) (bidr2.getLogout_date().getTime() - bidr2.getLogin_date().getTime()) / 1000);
					list.add(bidr2);
					
					map.remove(split[4]);
					onlineNum.remove(split[4]);
					
					
				}else {
					onlineNum.put(split[4], onlineNum.get(split[4]) - 1);
					System.out.println("������-1");					
				}
			}
			
		}
		for (BIDR bidr : list) {
			System.out.println("AAA������:  " + bidr.getAAA_login_name() + "NAS������IP��ַ: " + bidr.getNAS_ip() + " ����IP��ַ: "
					+ bidr.getLogin_ip() + "����ʱ��: " + bidr.getLogin_date() + "����ʱ��: " + bidr.getLogout_date() + "����ʱ��: "
					+ bidr.getTime_deration() + "��");
		}

		System.out.println("IP��" + list.size());
		System.out.println("û�����ߵ�IP:"+map.size());

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
