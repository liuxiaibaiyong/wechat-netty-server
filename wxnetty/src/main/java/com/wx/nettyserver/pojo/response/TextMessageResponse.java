package com.wx.nettyserver.pojo.response;

public class TextMessageResponse {
  private String toUserName;
  private String fromUserName;
  private String createTime;
  private String msgType;
  private String Content;
public String getToUserName() {
	return toUserName;
}
public void setToUserName(String toUserName) {
	this.toUserName = toUserName;
}
public String getFromUserName() {
	return fromUserName;
}
public void setFromUserName(String fromUserName) {
	this.fromUserName = fromUserName;
}
public String getCreateTime() {
	return createTime;
}
public void setCreateTime(String createTime) {
	this.createTime = createTime;
}
public String getMsgType() {
	return msgType;
}
public void setMsgType(String msgType) {
	this.msgType = msgType;
}
public String getContent() {
	return Content;
}
public void setContent(String content) {
	Content = content;
}
  
	
}
