package com.apical.oddm.infra.encrypt;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密相关
 * @author lgx
 * 2016-11-16
 */
public class Encrypt {

	/**
	 * 用apache的sha256加密
	 * @param body
	 * @return
	 */
	public static String sha256Hex(String body) {
		return DigestUtils.sha256Hex(body);
	}
}
