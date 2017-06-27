package cn.gcks.web.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletRequest;





/**
 * Http与Servlet工具类.
 * 
 * @author calvin
 */
public class RequestUtil {

	/**
	 * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
	 * 
	 * 返回的结果的Parameter名已去除前缀.
	 */
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Map<String, Object> params = new TreeMap<String, Object>();
		if(request!=null){
			Enumeration<String> paramNames = request.getParameterNames();
			if (prefix == null) {
				prefix = "";
			}
			while ((paramNames != null) && paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();
				if ("".equals(prefix) || paramName.startsWith(prefix)) {
					String unprefixed = paramName.substring(prefix.length());
					String[] values = request.getParameterValues(paramName);
					if ((values == null) || (values.length == 0)) {
						// Do nothing, no values found at all.
					} else if (values.length > 1) {
						params.put(unprefixed, values);
					} else {
						params.put(unprefixed, values[0]);
					}
				}
			}
		}
		return params;
	}

	/**
	 * 组合Parameters生成Query String的Parameter部分, 并在paramter name上加上prefix.
	 * 
	 * @see #getParametersStartingWith
	 */
	public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
		if (params == null || params.isEmpty()) {
			return "";
		}

		if (prefix == null) {
			prefix = "";
		}

		StringBuilder queryStringBuilder = new StringBuilder();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append('&');
			}
		}
		return queryStringBuilder.toString();
	}

}
