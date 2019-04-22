package com.wx.nettyserver.tool;


import java.lang.reflect.Field;
import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.Iterator; 
import java.util.List; 
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wx.nettyserver.constant.IConstantTrade; 
public final class XmlUtils { 
	 public static final Logger log = LoggerFactory.getLogger(XmlUtils.class);
    @SuppressWarnings("unchecked")  
    public static NettyMap<String, Object> Dom2Map(String text){ 
        NettyMap<String, Object> map = new NettyMap(); 
	    Document doc;
		try {
			doc = DocumentHelper.parseText(text);
            if(doc == null) 
              return map; 
            Element root = doc.getRootElement(); 
            for (Iterator iterator = root.elementIterator(); iterator.hasNext();) { 
            Element e = (Element) iterator.next(); 
            List list = e.elements(); 
            if(list.size() > 0){ 
                map.put(StringUtils.uncapitalize(e.getName()), Dom2Map(e)); 
            }else 
                map.put(StringUtils.uncapitalize(e.getName()), e.getText()); 
            }
        } catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			doc = null;
		}
        return map; 
    }
    
    public static String getHexHash(String text){ 
    	 return Dom2Map(text).getHexCode();
    }
    
    
    @SuppressWarnings("unchecked")
    public static NettyMap Dom2Map(Element e){ 
        NettyMap map = new NettyMap(); 
        List list = e.elements(); 
        if(list.size() > 0){ 
            for (int i = 0;i < list.size(); i++) { 
                Element iter = (Element) list.get(i); 
                List mapList = new ArrayList(); 
                 
                if(iter.elements().size() > 0){ 
                    Map m = Dom2Map(iter); 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList(); 
                            mapList.add(obj); 
                            mapList.add(m); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(m); 
                        } 
                        map.put(StringUtils.uncapitalize(iter.getName()), mapList); 
                    }else 
                        map.put(StringUtils.uncapitalize(iter.getName()), m); 
                } 
                else{ 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList(); 
                            mapList.add(obj); 
                            mapList.add(iter.getText()); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(iter.getText()); 
                        } 
                        map.put(StringUtils.uncapitalize(iter.getName()), mapList); 
                    }else 
                        map.put(StringUtils.uncapitalize(iter.getName()), iter.getText()); 
                } 
            } 
        }else 
            map.put(StringUtils.uncapitalize(e.getName()), e.getText()); 
        return map; 
    } 
    
    /**
     * object转成wechat想要的xml
     * @param obj
     * @return
     */
    public static String object2Xml(Object obj){
    	StringBuffer strBuff = new StringBuffer();
    	strBuff.append(IConstantTrade.XMLHEAD);
    	strBuff.append("<xml>\r\n");
    	try{
    	 Field[] fields = obj.getClass().getDeclaredFields();
    	 for(Field field:fields){
             field.setAccessible(true);
             String fieldName=StringUtils.capitalize(field.getName());
             strBuff.append("<");
             strBuff.append(fieldName);
             strBuff.append(">");
             strBuff.append(IConstantTrade.CDATAF);
             strBuff.append(field.get(obj));
             strBuff.append(IConstantTrade.CDATAL);
             strBuff.append("</");
             strBuff.append(fieldName);
             strBuff.append(">");
             strBuff.append('\r').append('\n');
         }
    	 strBuff.append("</xml>");
    	}catch(Exception e){
    	  log.error(e.getMessage());
    	}
    	log.info(strBuff.toString());
    	return strBuff.toString();
    }
} 