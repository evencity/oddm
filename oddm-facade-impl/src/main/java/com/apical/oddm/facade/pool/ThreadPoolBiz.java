package com.apical.oddm.facade.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 设置一个线程池为 处理业务线程，根据实际硬件配置，设置参数
 * @author lgx
 * 2016-6-2
 */
public class ThreadPoolBiz {
	private static final Logger log = LoggerFactory.getLogger(ThreadPoolBiz.class);

	private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
   		private final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = defaultFactory.newThread(r);
            if (!thread.isDaemon()) {
                thread.setDaemon(true);
            }
            thread.setName("changeRecord-thread-pool-" + threadNumber.getAndIncrement());
            return thread;
        }
	};
	private static ExecutorService nPool;
	//static ThreadPoolExecutor t;
 	static {
 		//无界队列LinkedBlockingQueue(其实相当于newFixedThreadPool())，最大线程池和时间是无效的，超过corePoolSize，就会等待了
 		//遗留 线程数的设置依据是？网上建议：CPU密集型 cpu+1;IO密集型任务，参考值可以设置为2*NCPU。如果不能理论计算出，请以实际测试的为准
 		int poolSize = Runtime.getRuntime().availableProcessors() * 20;
		nPool = new ThreadPoolExecutor(poolSize, poolSize ,0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), THREAD_FACTORY);//先初始化，不必要懒加载
		log.info("thread pool init success "+poolSize);
 	}
	private ThreadPoolBiz () {}
	
	public static ExecutorService getInstance() {
		//System.out.println("返回池中的当前线程数end:"+t.getPoolSize());;
		return nPool;  
	}
}
