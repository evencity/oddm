package com.apical.oddm.web.controller.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 注册handler，一个是浏览器内置的websocket、一个是sockjs.min.js
 * @author lgxd
 * 2016-11-17
 */
@Configuration
@EnableWebSocket
//@EnableWebMvc
public class WebSocketConfig implements WebSocketConfigurer {
	
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(myHandler(),"/myHandler").addInterceptors(new HandshakeInterceptor());
        webSocketHandlerRegistry.addHandler(myHandler(),"/sockjs/myHandler").addInterceptors(new HandshakeInterceptor()).withSockJS();
    }
    
    @Bean
    public WebSocketHandler myHandler(){
        return new AnalysisWebSocketHandler();
    }
}
