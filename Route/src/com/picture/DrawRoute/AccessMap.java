package com.picture.DrawRoute;

import java.util.HashMap;

public class AccessMap {
	public static final String AK = "4sk2aoy49QutxldPS9G4GjvRCaIWRU1Z";
	public static final String SUGGESTION_URL = "http://api.map.baidu.com/place/v2/suggestion";
	public static HashMap<String, String> parameters = new HashMap<String, String>();
	static {
		parameters.put("query", ""); //���뽨��ؼ��֣�֧��ƴ����
		parameters.put("region", ""); //��������/�������ƻ����
		parameters.put("location", ""); //����location�����󣬷��ؽ�����Ծ����������
		parameters.put("output", "json"); //�������ݸ�ʽ����ѡjson��xml����
		parameters.put("ak", AK); //�����߷�����Կ����ѡ
		parameters.put("sn", ""); //�û���Ȩ��ǩ��
		parameters.put("timestamp", ""); //����sn���ֵ��ѡ
	};
	
}
