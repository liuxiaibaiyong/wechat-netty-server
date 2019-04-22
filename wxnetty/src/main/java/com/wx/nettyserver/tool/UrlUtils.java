package com.wx.nettyserver.tool;

import org.springframework.util.StringUtils;

public final class UrlUtils {
	
   public static String deelUrl(String uri){
	  if(StringUtils.isEmpty(uri)){
		  return null;
	  }
	  int firstXIndex = uri.indexOf('/');
	  int lastXIndex = uri.lastIndexOf('/');
	  int index = uri.indexOf("?");
/*	  System.out.println(firstXIndex);
			  System.out.println(lastXIndex);
					  System.out.println(index);
					  System.out.println(uri.length());*/
	  if(index >=0 && lastXIndex == index-1 && firstXIndex == lastXIndex){
	     uri = uri.substring(0, lastXIndex);
	  } else if(index >=0 && lastXIndex == index-1 && firstXIndex != lastXIndex){
		 uri = uri.substring(uri.substring(0,lastXIndex).lastIndexOf('/')+1, index-1);
	  } else if(index >=0 && lastXIndex != index-1 && lastXIndex >=0){
		 uri = uri.substring(lastXIndex+1, index);
	  } else if(index>=0){
		 uri = uri.substring(0, index);
	  } else if(index <0 && lastXIndex>=0 && lastXIndex != uri.length()-1){
		 uri = uri.substring(lastXIndex+1, uri.length()); 
	  } else if(index <0 && lastXIndex>=0 && lastXIndex == uri.length()-1){
		 uri = uri.substring(uri.substring(0,lastXIndex).lastIndexOf('/')+1, lastXIndex); 
	  } 
	  return uri;
   }
  
}
