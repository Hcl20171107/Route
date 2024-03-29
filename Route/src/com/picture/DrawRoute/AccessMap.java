package com.picture.DrawRoute;

import java.util.HashMap;
import java.util.Iterator;

import com.github.heqiao2010.BusinessException;
import com.github.heqiao2010.StringUtils;

public class AccessMap {
	public static final String AK = "4sk2aoy49QutxldPS9G4GjvRCaIWRU1Z";
	public static final String SUGGESTION_URL = "http://api.map.baidu.com/place/v2/suggestion";
	public static HashMap<String, String> parameters = new HashMap<String, String>();
	static {
		parameters.put("query", ""); //输入建议关键字（支持拼音）
		parameters.put("region", ""); //所属城市/区域名称或代号
		parameters.put("location", ""); //传入location参数后，返回结果将以距离进行排序
		parameters.put("output", "json"); //返回数据格式，可选json、xml两种
		parameters.put("ak", AK); //开发者访问密钥，必选
		parameters.put("sn", ""); //用户的权限签名
		parameters.put("timestamp", ""); //设置sn后该值必选
	};
	private static void checkParameter(HashMap<String, String> aparameters)
			throws BusinessException {
		String errorMsg = "";
		if (null == aparameters) {
			errorMsg = "Parameter is empty!";
		} else if (StringUtils.isBlank(parameters.get("query"))) {
			errorMsg = "Parameter: query is blank!";
		} else if (StringUtils.isBlank(parameters.get("region"))) {
			errorMsg = "Parameter: region is blank!";
		} else if (StringUtils.isBlank(parameters.get("ak"))) {
			errorMsg = "Parameter: ak is blank!";
		}
		if (StringUtils.isNotEmpty(errorMsg)) {
			throw new BusinessException(errorMsg);
		}
	}
	public static String getParameterStr(HashMap<String, String> aparameters) 
			throws BusinessException{
		checkParameter(aparameters);
		StringBuilder retStr = new StringBuilder();
		Iterator<String> it = aparameters.keySet().iterator();
		while(it.hasNext()){
			String name = it.next();
			String value = aparameters.get(name);
			if(StringUtils.isNotEmpty(value)){
				retStr.append(name + "=" + aparameters.get(name));
				if(it.hasNext()){
					retStr.append('&');
				}
			}
		}
		return retStr.toString();
	}
}
