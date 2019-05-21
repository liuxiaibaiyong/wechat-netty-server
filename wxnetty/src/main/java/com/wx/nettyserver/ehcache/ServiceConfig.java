package com.wx.nettyserver.config;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class ServiceConfig implements InitializingBean,ApplicationContextAware{
	
	public static final Logger log = LoggerFactory.getLogger(ServiceConfig.class);
	 
    public static final Map serviceMap = new ConcurrentHashMap();
    
    @Autowired
    private CacheManager cacheManager;
    
    private Cache cache;
    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		cache = (Cache) cacheManager.getCache("serviceCache");
		if(cache == null){
			return;
		}
		// TODO Auto-generated method stub
		try(InputStream in = ServiceConfig.class.getClassLoader().getResourceAsStream("config/service.properties")){
            Properties pro = new Properties();
            pro.load(in);
            String path = (String) pro.remove("path")+".";
            pro.entrySet().forEach(action -> {
            	cache.put(new Element(getHashCode(String.valueOf(action.getValue()).split(",")),path+String.valueOf(action.getKey())));
            });
         } catch(Exception e){
            log.error(e.getMessage());
        }
		System.out.println(cache.getKeys());
	}

	private String getHashCode(String... s){
		long hexHash = 0L;
		for(String value:s){
			hexHash+=value.hashCode();
			log.info(value+":"+value.hashCode());
		}
		return Long.toHexString(hexHash);
	}
	
	/**
	 * 获取值
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		Element ele = cache.get(key);
		if(ele == null) return null;
		return (String) ele.getValue();
	}
	
	/**
	 * 检验是否存在参数
	 * @param key
	 * @return
	 */
	public boolean isExistsKey(String key){
		return cache.isElementInMemory(key);
	}
	
	/**
	 * 获取值
	 * @param key
	 * @return
	 */
	public String getServiceName(String key){
		Element ele = cache.get(key);
		if(ele == null) return null;
		String value = (String) ele.getValue();
		return value.substring(value.lastIndexOf('.')+1).replace("Request", "Service");
	}
}
