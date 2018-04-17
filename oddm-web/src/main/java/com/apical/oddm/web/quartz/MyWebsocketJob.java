package com.apical.oddm.web.quartz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.apical.oddm.facade.websocket.MsgCache;

/**
 * 执行定时删除轨迹缓存文件的任务
 * @author lgx
 * 2016-11-17
 */
@Component("myWebsocketBean")
public class MyWebsocketJob {  
	private static final Logger log = LoggerFactory.getLogger(MyWebsocketJob.class);

	public void execute(){
		MsgCache.excuteCheckCache();
	}  
	
	/**
	 * @param cmd 执行shell命令
	 */
	@SuppressWarnings("unused")
	private void excuteCmd(String cmd) {
		InputStreamReader isr = null;
		BufferedReader br = null;
        //String line = null;
		try {
			//注意命令的空格
			Process p = Runtime.getRuntime().exec(cmd);
	        // 用一个读输出流类去读
			isr = new InputStreamReader(p.getInputStream());//正常情况下没有日志
			//isr = new InputStreamReader(p.getErrorStream());//这个可以看到日志
	        // 用缓冲器读行   
	        br = new BufferedReader(isr);
	        // 直到读完为止   
			while ((br.readLine()) != null) {//打印日志，这个起到等待转换完缩略图的作用
			    //System.out.println(line);
				log.info("删除结果，" + br.readLine());
			}
			log.info("定时删除轨迹文件任务，命令："+cmd);
		} catch (Exception e) {
			log.error("定时删除轨迹文件任务异常", e);
		} finally {
			try {
				if(isr != null)
					isr.close();
				if(br != null)
					br.close();
			} catch (IOException e) {
				log.error("关闭流错误", e);
			}
		}
	}
}  
