package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.util.BackUP;

import woss.client.GatherImpl2;

public class BackUpImpl implements BackUP {

	@Override
	public void init(Properties properties) {
	}

	/**
	 * ͨ��������ȡ�Ѿ����ݵ����� 
	 * key - �������ݵļ� 
	 * flag - ȡ����������ʱ���Ƿ������ݡ�����ʹ�ó���ֵ��
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object load(String key, boolean flag) throws Exception {
		ObjectInputStream objectinputStream= new ObjectInputStream(new FileInputStream(key));
		List<BIDR> list2= (List<BIDR>) objectinputStream.readObject();
		objectinputStream.close();
		int count=0;
		for (BIDR bidr : list2) {
			System.out.println((count++)+bidr.getLogin_ip());
		}
		if(flag) {
			File file = new File(key);
			file.delete();
		}
		return null;
	}

	/**
	 * ͨ���������洢���ݡ�
	 *  ������ 
	 *  key - �������ݵļ� 
	 *  data - ��Ҫ���ݵ�����
	 *  flag -�����ֵ�Ѿ��������ݣ�׷�ӻ��Ǹ���֮ǰ�����ݡ�����ʹ�ó���ֵ��
	 */
	@Override
	public void store(String key, Object data, boolean flag) throws Exception {
		ObjectOutputStream objectOutputStream =new ObjectOutputStream(new FileOutputStream(key,false));
		objectOutputStream.writeObject(data);		
		objectOutputStream.close();
	}
	/*public static void main(String[] args) throws Exception {
//		String string = "src/file/test.txt";
//		Collection<BIDR> gather = new GatherImpl2().gather();
//		BackUpImpl backUpImpl = new BackUpImpl();
//		backUpImpl.store(string, gather, true);
//		backUpImpl.load(string,false);
		
		�������·��
		 * System.out.println(BackUpImpl.class.getResource("/woss"));
		File file = new File("src/file/test.txt");
		System.out.println(file.exists());
		
	}*/
}
