package com.apical.oddm.web.controller.token;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.apical.oddm.infra.util.OddmDateUtil;
import com.apical.oddm.web.controller.base.BaseController;


@Controller
@RequestMapping("/token")
public class TokenController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(TokenController.class);

	// 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
	private static final String REGION_CN_HANGZHOU = "cn-hangzhou";
	private static final String STS_API_VERSION = "2015-04-01";
	
	// 只有 RAM用户（子账号）才能调用 AssumeRole 接口
	// 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
	// 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
	private static String accessKeyId;
	private static String accessKeySecret;
	
	// RoleArn 需要在 RAM 控制台上获取
	private static String roleArn;
	private static long durationSeconds;
	// RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
	// 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
	// 具体规则请参考API文档中的格式要求
	private static String roleSessionName = "alice-001";
	// 此处必须为 HTTPS
	private static ProtocolType protocolType = ProtocolType.HTTPS;
	//public static FastDateFormat yearFmat = FastDateFormat.getInstance("yyyy");
	public static String LOCAL_IP = null;
	
	static {
		JSONObject jsonObj = JSONObject.parseObject(ReadJson());
		accessKeyId = jsonObj.getString("AccessKeyID");
		accessKeySecret = jsonObj.getString("AccessKeySecret");
		roleArn = jsonObj.getString("RoleArn");
		durationSeconds = jsonObj.getLong("TokenExpireTime");
		
		InetAddress netAddress = null;
		try {
			netAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		LOCAL_IP = netAddress.getHostAddress();
	}

	/**
	 * 增删改目录，不需传文件名
	 * @param tokenCmd
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public TokenJson update(TokenCmd tokenCmd) {
		if (LOCAL_IP.startsWith("192.168")) {
			log.debug("本地禁止操作文档");
			return null;//避免本地操作文档
		}
        log.debug("接收token请求："+tokenCmd);
		TokenJson tokenJson = new TokenJson();
		try {
			String fileType = tokenCmd.getFileType();
			String fileDetail = tokenCmd.getFileDetail();
			String year = tokenCmd.getYear();
			if (StringUtils.isBlank(year) || "nan".equalsIgnoreCase(year)) {
				year = OddmDateUtil.yearFmat.format(System.currentTimeMillis());
			}
			String orderNo = tokenCmd.getOrderNo();
			if (!"test".equals(fileType) && !"document".equals(fileType) && !"picture".equals(fileType)) {
				tokenJson.setStatus("传参解析异常");
				log.error("传参解析异常"+tokenCmd);
				return tokenJson;
			}
			if (!"order".equals(fileDetail) && !"common".equals(fileDetail) && !"prototype".equals(fileDetail) && !"produce".equals(fileDetail)) {
				tokenJson.setStatus("传参解析异常");
				log.error("传参解析异常"+tokenCmd);
				return tokenJson;
			}
			
			String policy = null;
			if (StringUtils.isBlank(orderNo)) {
				policy  = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetObject\",\"oss:PutObject\",\"oss:DeleteObject\",\"oss:ListParts\",\"oss:AbortMultipartUpload\",\"oss:ListObjects\"],\"Resource\":[\"acs:oss:*:*:oddm/"+fileType+"/"+fileDetail+"/"+year+"/*\",\"acs:oss:*:*:oddm\"],\"Effect\":\"Allow\"}]}";
				tokenJson.setPath(fileType+"/"+fileDetail+"/"+year+"/");
			} else {
				policy  = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetObject\",\"oss:PutObject\",\"oss:DeleteObject\",\"oss:ListParts\",\"oss:AbortMultipartUpload\",\"oss:ListObjects\"],\"Resource\":[\"acs:oss:*:*:oddm/"+fileType+"/"+fileDetail+"/"+year+"/"+orderNo+"/*\",\"acs:oss:*:*:oddm\"],\"Effect\":\"Allow\"}]}";
				tokenJson.setPath(fileType+"/"+fileDetail+"/"+year+"/"+orderNo+"/");
			}
			final AssumeRoleResponse stsResponse = assumeRole(accessKeyId, accessKeySecret, roleArn, roleSessionName,
					policy, protocolType, durationSeconds);
			tokenJson.setStatus(200+"");
			tokenJson.setAccessKeyId(stsResponse.getCredentials().getAccessKeyId());
			tokenJson.setAccessKeySecret(stsResponse.getCredentials().getAccessKeySecret());
			tokenJson.setSecurityToken(stsResponse.getCredentials().getSecurityToken());
			tokenJson.setExpiration(stsResponse.getCredentials().getExpiration());
			
            log.debug("成功返回："+tokenJson);
			return tokenJson;
		} catch (ClientException e) {
			tokenJson.setStatus(e.getErrCode());
			log.error("向阿里云请求token错误", e);
			return tokenJson;
		} catch (Exception e) {
			tokenJson.setStatus(e.getMessage());
			log.error("传参解析异常", e);
			return tokenJson;
		}
	}
	
	/**
	 * 增删改单个文件
	 * @param tokenCmd 只需传文件名全路径即可
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/download")
	public TokenJson download(@RequestBody TokenCmd tokenCmd) {
		if (LOCAL_IP.startsWith("192.168")) {
	        log.debug("本地禁止操作文档");
			return null;//避免本地操作文档
		}
		TokenJson tokenJson = new TokenJson();
        log.debug("接收token请求："+tokenCmd.getFilePath());
		try {
			String filePath = tokenCmd.getFilePath();
			String[] split = filePath.split("\\/");
			//System.out.println("filePath :"+split[0]);
			if (!"test".equals(split[0]) && !"document".equals(split[0]) && !"picture".equals(split[0])) {
				tokenJson.setStatus("传参解析异常");
				log.error("传参解析异常"+tokenCmd);
				return tokenJson;
			}
			if (!"order".equals(split[1]) && !"common".equals(split[1]) && !"prototype".equals(split[1]) && !"produce".equals(split[1])) {
				tokenJson.setStatus("传参解析异常");
				log.error("传参解析异常"+tokenCmd);
				return tokenJson;
			}
			String policy  = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetObject\",\"oss:PutObject\",\"oss:DeleteObject\",\"oss:ListObjects\"],\"Resource\":[\"acs:oss:*:*:oddm/"+filePath+"\",\"acs:oss:*:*:oddm\"],\"Effect\":\"Allow\"}]}";
			System.err.println("policy    "+policy);
			tokenJson.setPath(filePath);
			final AssumeRoleResponse stsResponse = assumeRole(accessKeyId, accessKeySecret, roleArn, roleSessionName,
				policy, protocolType, durationSeconds);
			tokenJson.setStatus(200+"");
			tokenJson.setAccessKeyId(stsResponse.getCredentials().getAccessKeyId());
			tokenJson.setAccessKeySecret(stsResponse.getCredentials().getAccessKeySecret());
			tokenJson.setSecurityToken(stsResponse.getCredentials().getSecurityToken());
			tokenJson.setExpiration(stsResponse.getCredentials().getExpiration());
			
            log.debug("成功返回："+tokenJson);
			return tokenJson;
		} catch (ClientException e) {
			tokenJson.setStatus(e.getErrCode());
			log.error("向阿里云请求token错误 "+tokenCmd, e);
			return tokenJson;
		} catch (Exception e) {
			tokenJson.setStatus(e.getMessage());
			log.error("传参解析异常 "+tokenCmd, e);
			return tokenJson;
		}
	}
	
	/**
	 * 读取配置文件
	 * 最小不能小于900秒
	 */
	private static String ReadJson(){
		return "{'AccessKeyID' : 'IXuS2AjWeq1yjY3f','AccessKeySecret' : 'JvllchslJkhZ35oxpSrwcI0YiBc8GG','RoleArn' : 'acs:ram::1514751286037597:role/aliyunosstokengeneratorrole','TokenExpireTime' : '901','PolicyFile': 'policy/bucket_read_write_policy.txt'}";
    }
	
	private AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret, String roleArn,
			String roleSessionName, String policy, ProtocolType protocolType, long durationSeconds) throws ClientException {
		try {
			// 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
			IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			// 创建一个 AssumeRoleRequest 并设置请求参数
			final AssumeRoleRequest request = new AssumeRoleRequest();
			request.setVersion(STS_API_VERSION);
			request.setMethod(MethodType.POST);
			request.setProtocol(protocolType);
			request.setRoleArn(roleArn);
			request.setRoleSessionName(roleSessionName);
			request.setPolicy(policy);
			request.setDurationSeconds(durationSeconds);

			// 发起请求，并得到response
			final AssumeRoleResponse response = client.getAcsResponse(request);
			return response;
		} catch (ClientException e) {
			throw e;
		}
	}
	
	/*switch(fileType) {
	case "document":
		switch (fileDetail) {
		case "order"://普通订单文档
			break;
		case "common"://通用文档
			orderNo = null;
			break;
		default:
			tokenJson.setStatus("页面传参错误");
			log.error("页面传参错误 "+tokenCmd);
			return tokenJson;
		}
		break;
	case "picture":
		switch (fileDetail) {
		case "produce": //生产任务书图片
			
			break;
		case "prototype"://首件图片
			
			break;
		default:
			tokenJson.setStatus("页面传参错误");
			log.error("页面传参错误 "+tokenCmd);
			return tokenJson;
		}
		break;
	default:
		tokenJson.setStatus("页面传参错误");
		log.error("页面传参错误 "+tokenCmd);
		return tokenJson;
}*/
}