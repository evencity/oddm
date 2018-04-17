package com.apical.oddm.facade.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年10月25日 下午6:01:31
 * @version 1.0
 */

public class StringUtil {

	public static Set<Integer> stringToSet(String str) {
		// TODO Auto-generated method stub
		String[] strings = str.substring(0, str.length()).split(",");
		Set<Integer> set = new HashSet<Integer>();
		for (String string : strings) {
			set.add(Integer.valueOf(string));
		}
		return set;
	}
}
