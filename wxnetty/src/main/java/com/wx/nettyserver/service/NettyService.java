package com.wx.nettyserver.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wx.nettyserver.constant.IConstantTrade;
import com.wx.nettyserver.dao.impl.BaseDao;
import com.wx.nettyserver.pojo.response.Response;

public abstract class NettyService<T> extends BaseDao<T>  implements IService<T>{
	
	 public static final Logger log = LoggerFactory.getLogger(NettyService.class);
     
	 public abstract Response service(T t) throws Exception;
	 
	 public boolean validte(Object obj){
		 log.info(obj.getClass().toGenericString());
		 if(obj.getClass().equals(fetchType())){
			 return true;
		 } else {
			 return false;
		 }
	 }
	 
	 public Response complete(Object obj) throws Exception{
		if(!validte(obj)){
			return new Response(IConstantTrade.ERROR);
		} else {
			T t = (T)obj;
			return service(t);
		}
	 }
	 
	 
	 private Class fetchType(){
		 Type type = this.getClass().getGenericSuperclass();
			if(!(type instanceof ParameterizedType)) {
				return Object.class;
			}
			Type[] params = ((ParameterizedType)type).getActualTypeArguments();
			if(!(params[0] instanceof Class)) {
				return Object.class;
			}
			return (Class) params[0];
 
	 }

}
