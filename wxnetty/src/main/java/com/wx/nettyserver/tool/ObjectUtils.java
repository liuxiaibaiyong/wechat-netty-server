package com.wx.nettyserver.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.wx.nettyserver.constant.IConstantTrade;

import io.netty.buffer.ByteBuf;

public class ObjectUtils {
	
	public static Map<String,Object> Obj2Map(Object obj) throws Exception{
        Map<String,Object> map=new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }
	
	
    public static Object map2Obj(Map<String,Object> map,Class<?> clz) throws Exception{
        Object obj = clz.newInstance();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        Field[] superField = obj.getClass().getSuperclass().getDeclaredFields();
        Field[] fields = (Field[]) merge(declaredFields, superField);
        if(map.containsKey(IConstantTrade.XML)){
        	map.putAll(XmlUtils.Dom2Map(String.valueOf(map.get(IConstantTrade.XML))));
        }
        for(Field field:fields){
            int mod = field.getModifiers(); 
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }
 
    public static String convertByteBufToString(ByteBuf buf) {
        String str;
        if (buf.hasArray()) { // 处理堆缓冲区
            str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
        } else { // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            str = new String(bytes, 0, buf.readableBytes());
        }
        return str;
    }
    
    public static Field[] merge(Field[] a1, Field[] a2) {
    	  Field[] a3 = new Field[a1.length + a2.length];
    	  System.arraycopy(a1, 0, a3, 0, a1.length);
    	  System.arraycopy(a2, 0, a3, a1.length, a2.length);
    	  return a3;
    }
}
