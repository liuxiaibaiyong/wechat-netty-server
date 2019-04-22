package com.wx.nettyserver.tool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wx.nettyserver.server.DellServerHandler;

public class NettyMap<K,V> extends HashMap<K,V>{

  public static final Logger log = LoggerFactory.getLogger(NettyMap.class);
  
   public String getHexCode(){
	   long hashcode = 0L;
	   Iterator it = this.entrySet().iterator();
	   while(it.hasNext()){
		   Map.Entry entry = (Map.Entry)it.next();
		   log.info(entry.getKey()+":"+entry.getKey().hashCode());
		   hashcode+= entry.getKey().hashCode();
	   }
	   return Long.toHexString(hashcode);
   }
   

}
