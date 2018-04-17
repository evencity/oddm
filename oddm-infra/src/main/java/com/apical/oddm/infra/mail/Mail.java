package com.apical.oddm.infra.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 发送邮件的测试程序
 * @author lgx
 * 2016-05-12
 */
public class Mail {
	private static final Logger log = LoggerFactory.getLogger(Mail.class);

    static final Properties propsMail = new Properties();
    static Authenticator authenticator;
	/**
	 * 初始化参数
	 * @throws GeneralSecurityException 
	 */
	static {
		try {
			// 配置发送邮件的环境属性
	        /*
	         * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
	         * mail.user / mail.from
	         */
	        // 表示SMTP发送邮件，需要进行身份验证
	        propsMail.put("mail.smtp.auth", "true");
	        propsMail.put("mail.smtp.host", "smtp.apical.com.cn"); // smtp.qq.com smtp.apical.com.cn
	        // 发件人的账号
	        propsMail.put("mail.user", "oddm@apical.com.cn");// 2851721115@qq.com   security@apical.com.cn
	        // 访问SMTP服务时需要提供的密码
	        propsMail.put("mail.password", "A666888a!@#");//wofvcrpholbddfej A666888a
	        
	        //支持ssl和no ssl
	        MailSSLSocketFactory sf = new MailSSLSocketFactory(); 
	        sf.setTrustAllHosts(true);
	        // or
	        // sf.setTrustedHosts(new String[] { "my-server" }); 
	        propsMail.put("mail.smtp.ssl.enable", "true"); 
	        // also use following for additional safety
	        //props.put("mail.smtp.ssl.checkserveridentity", "true"); 
	        propsMail.put("mail.smtp.ssl.socketFactory", sf); 
	        
	        // 构建授权信息，用于进行SMTP进行身份验证
	        authenticator = new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                // 用户名、密码
	                String userName = propsMail.getProperty("mail.user");
	                String password = propsMail.getProperty("mail.password");
	                return new PasswordAuthentication(userName, password);
	            }
	        };
	        log.info("mail init success ");
		} catch (Exception e) {
	        log.error("mail init failed ", e);
		}
	}
    /**
     * @param receivers  逗号分隔：tiwsonchen@163.com,tiwson@163.com
     * @throws MessagingException
     */
    public static void sendMail(String receivers, String title, String msgContents) {
    	MimeMessage message = null;
    	try {
	        // 使用环境属性和授权信息，创建邮件会话
	    	//Session mailSession = Session.getInstance(propsMail, authenticator);//多例
	    	Session mailSession = Session.getDefaultInstance(propsMail, authenticator);//单例
	        // 创建邮件消息
	        message = new MimeMessage(mailSession);
	        // 设置发件人
	        InternetAddress form = new InternetAddress(propsMail.getProperty("mail.user"));
	        message.setFrom(form);
	        // 设置收件人(单发)
	        /*InternetAddress to = new InternetAddress("2851721115@qq.com");
	        message.setRecipient(RecipientType.TO, to);*/
	        // 设置收件人(多发)
	        //InternetAddress[] to = InternetAddress.parse(toAddress); 
	        InternetAddress[] to = InternetAddress.parse(receivers); 
	        message.setRecipients(RecipientType.TO, to);
	
	        // 设置抄送
	        InternetAddress cc = new InternetAddress("linggx@apical.com.cn");
	        message.setRecipient(RecipientType.CC, cc);
	        /*
	        // 设置密送，其他的收件人不能看到密送的邮件地址
	        InternetAddress bcc = new InternetAddress("aaaaa@163.com");
	        message.setRecipient(RecipientType.CC, bcc);
	*/
	        // 设置邮件标题
	        message.setSubject(title);
	        // 设置邮件的内容体
	        //message.setContent("<a href='http://www.fkjava.org'>测试的HTML邮件</a>", "text/html;charset=UTF-8");
	        //message.setContent(msgContents, "text/html;charset=UTF-8");
	        
	        String body = msgContents + "</br></br>" 
	                + "<hr align=\"left\" width=\"500\"><img src = \"cid:logo_jpg\">"
	                + "</br>爱培科订单管理系统"
	                + "</br>网址：<a href=\"http://oddm.apical.com.cn/\" target=\"_blank\" title=\"转到ODDM主页\">http://oddm.apical.com.cn</a>";
	        // 创建邮件的各个 MimeBodyPart 部分
	        String logPath = Mail.class.getResource("/logo.jpg").getPath();
	        MimeBodyPart content = createContent(body, logPath);  
	        // 将邮件中各个部分组合到一个"mixed"型的 MimeMultipart 对象  
	        MimeMultipart allPart = new MimeMultipart("mixed");  
	        allPart.addBodyPart(content);  
	        // 将上面混合型的 MimeMultipart 对象作为邮件内容并保存  
	        message.setContent(allPart);  
	        message.saveChanges();
	        // 发送邮件
	        Transport.send(message);
	        log.debug("Send Mail to "+receivers+" successfully "+msgContents);
		} catch (SendFailedException e) {
			/*Address[] addrs = e.getValidUnsentAddresses();
			//System.out.println(Arrays.toString(addrs));
			try {
		        message.setRecipients(RecipientType.TO, addrs);
				Transport.send(message, addrs);
		        log.debug("ReSend Mail to "+Arrays.toString(addrs)+" successfully ");
		        return;
			} catch (MessagingException e1) {
				log.error("ReSend Mail failed addr:"+Arrays.toString(addrs));
			}*/
	        log.error("Send Mail failed "+receivers+" "+title+" "+msgContents, e);
		} catch (Exception e) {
	        log.error("Send Mail failed "+receivers+" "+title+" "+msgContents, e);
		}
    }
    
    /**  
     * 根据传入的邮件正文body和文件路径创建图文并茂的正文部分  
     */ 
    public static MimeBodyPart createContent(String body, String fileName)  
            throws Exception {  
        // 用于保存最终正文部分  
        MimeBodyPart contentBody = new MimeBodyPart();  
        // 用于组合文本和图片，"related"型的MimeMultipart对象  
        MimeMultipart contentMulti = new MimeMultipart("related");  
 
        // 正文的文本部分  
        MimeBodyPart textBody = new MimeBodyPart();  
        textBody.setContent(body, "text/html;charset=UTF-8");  
        contentMulti.addBodyPart(textBody);  
 
        // 正文的图片部分  
        MimeBodyPart jpgBody = new MimeBodyPart();  
        FileDataSource fds = new FileDataSource(fileName);  
        jpgBody.setDataHandler(new DataHandler(fds));  
        jpgBody.setContentID("logo_jpg");  
        contentMulti.addBodyPart(jpgBody);  
 
        // 将上面"related"型的 MimeMultipart 对象作为邮件的正文  
        contentBody.setContent(contentMulti);  
        return contentBody;  
    }
    
    public static void main(String[] args) {
    	//init();
    	//sendMail("2851721115@qq.com,linggx@apical.com.cn,tedddst2s2@apical.com.cn","test","test");
    	sendMail("security@apical.com.cn","title","contents");
	}
}
