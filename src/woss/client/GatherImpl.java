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
	 * �ɼ�����
	 * ���ܣ��������ļ��л�ȡ��һ�������嵥
	 * ʵ��˼·��
	 * 1.׼��һ��List���ϣ��������BIDR����
	 * 2.׼������Map����
	 * 		1)��һ�� ��־��һ������
	 * 		7	IP,1	��һ���û�
	 * 		7	IP,2
	 * 		7	IP,3
	 * 		8	IP,3
	 * 		8	IP,2
	 * 		8	IP,1	���һ���û�
	 * 
	 * 		2)��Ų�������BIDR����
	 * 			���ߵ���Ϣ
	 * 			���ߵ���Ϣ
	 * 
	 * */
	@Override
	public Collection<BIDR> gather() throws Exception {

		return null;
	}

}
