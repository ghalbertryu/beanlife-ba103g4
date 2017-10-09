package com.prod.query;

import java.util.*;

public class ProdQuery {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;
		
		if ("bean_contry".equals(columnName) || "proc".equals(columnName) || "roast".equals(columnName) || "prod_no".equals(columnName) || "store_no".equals(columnName)) // 用於其他
			aCondition = columnName + "=" + " '" + value + "' ";
		else if ("prod_name".equals(columnName) || "bean_type".equals(columnName)|| "bean_grade".equals(columnName)|| "bean_region".equals(columnName)
				|| "bean_farm".equals(columnName)|| "bean_farmer".equals(columnName)|| "prod_cont".equals(columnName)|| "prod_stat".equals(columnName)|| "bean_aroma".equals(columnName)) // 用於varchar
			aCondition = "lower("+columnName + ") like lower('%" + value + "%')";
		else if ("".equals(columnName))                          // 用於Oracle的date
			aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";

		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if (count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" and " + aCondition);

				System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		
		return whereCondition.toString();
	}
	
	public static String get_ElseCondition(Map<String, String[]> map){
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());
				
				if (count == 1)
					whereCondition.append(aCondition);
				else
				whereCondition.append( " or " + aCondition);

			}
		}
		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("bean_contry", new String[] { "" });
		map.put("proc", new String[] { "日曬" });
		map.put("roast", new String[] { "淺焙" });

		map.put("action", new String[] { "searchProds" }); // 注意Map裡面會含有action的key
		
		String ser = "%%";
		Map<String, String[]> map2 = new TreeMap<String, String[]>();
		map2.put("prod_no", new String[] {ser });
		map2.put("store_no", new String[] {ser });
		map2.put("prod_name", new String[] {ser });
		map2.put("bean_type", new String[] {ser });
		map2.put("bean_grade", new String[] {ser });
		map2.put("bean_region", new String[] {ser });
		map2.put("bean_farm", new String[] {ser });
		map2.put("bean_farmer", new String[] {ser });
		map2.put("bean_aroma", new String[] {ser });
		map2.put("prod_stat", new String[] {ser });
		

		String finalSQL = "select * from prod "
				          + get_WhereCondition(map)
				          + "and ("
				          + get_ElseCondition(map2)
				          + ")"
				          + "order by prod_no desc";
		System.out.println(finalSQL);

	}
}
