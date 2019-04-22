package com.wx.nettyserver.pojo.response;

import com.wx.nettyserver.constant.IConstantTrade;

public class Response {
  private String text = IConstantTrade.ERROR;
  private String contentType = "text/xml;charset=UTF-8";
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public String getContentType() {
	return contentType;
}
public void setContentType(String contentType) {
	this.contentType = contentType;
}

public Response(String text,String contentType){
	this.text = text;
	this.contentType = contentType;
}
public Response(String text){
	this.text = text;
}

public Response(){
}
}