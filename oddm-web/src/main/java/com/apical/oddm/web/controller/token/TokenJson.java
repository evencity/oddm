package com.apical.oddm.web.controller.token;

/**
 * token权限返回
 * @author lgx
 * 2016-11-14
 */
public class TokenJson implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String status;
	
	private String accessKeyId;

	private String accessKeySecret;

	private String securityToken;

	private String expiration;
	
	private String path;

	private String bucket = "oddm";

	private String region = "oss-cn-hangzhou";

	/**
	 * 200 正常，其他异常
	 * @return 状态
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}

	/**
	 * 文件路径
	 * @return
	 */
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 阿里云oss bucket
	 * @return
	 */
	public String getBucket() {
		return bucket;
	}

	/**
	 * @return token过期时间
	 */
	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public String getRegion() {
		return region;
	}

	@Override
	public String toString() {
		return "TokenJson [status=" + status + ", accessKeyId=" + accessKeyId
				+ ", accessKeySecret=" + accessKeySecret + ", securityToken="
				+ securityToken + ", expiration=" + expiration + ", path="
				+ path + ", bucket=" + bucket + ", region=" + region + "]";
	}
}
