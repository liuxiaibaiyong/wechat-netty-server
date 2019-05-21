package com.wx.nettyserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    
	@Bean(name = "serviceConfig")
	public ServiceConfig getServiceConfig(){
		return new ServiceConfig();
	}
	

}
