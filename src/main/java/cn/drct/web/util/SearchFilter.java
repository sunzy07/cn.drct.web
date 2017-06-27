package cn.drct.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SearchFilter {

	public enum Operator {
		EQ,NEQ, LIKE,NLIKE, GT, LT, GTE, LTE, IN,NIN
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 */
	public static Map<String, SearchFilter> parse(
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof String) {
				if (isBlank((String) value)) {
					continue;
				}
			} else if (value instanceof String[]) {
				String[] tmp = (String[]) value;
				List<String> list = new ArrayList<String>();
				if (tmp != null) {
					for (String val : tmp) {
						if (!isBlank(val)) {
							list.add(val);
						}
					}
				}
				if (list.size() == 0) {
					continue;
				}
				value = list;
			}

			String[] names = key.split("_");
			if (names.length == 2) {
				String filedName = names[1];
				Operator operator = Operator.valueOf(names[0]);
				// 创建searchFilter
				SearchFilter filter = new SearchFilter(filedName, operator,
						value);
				filters.put(key, filter);
			}

		}

		return filters;
	}

	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
}
