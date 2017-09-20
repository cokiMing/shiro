package com.shiro.common.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by wuyiming on 2017/9/7.
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(),"/webSocketIMServer").setAllowedOrigins("*") .addInterceptors(handshakeInterceptor());
        registry.addHandler(webSocketHandler(), "/sockjs/webSocketIMServer").addInterceptors(handshakeInterceptor()).withSockJS();
    }

    @Bean
    public WebSocketHandler webSocketHandler(){
        return new WebSocketHandler();
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor(){
        return new HandshakeInterceptor();
    }
}
