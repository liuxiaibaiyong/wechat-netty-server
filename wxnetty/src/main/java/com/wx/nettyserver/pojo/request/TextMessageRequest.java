package com.wx.nettyserver.pojo.request;

public class TextMessageRequest extends SignatureRequest{
	

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7147002592468099114L;
	
  private String toUserName;
  private String fromUserName;
  private String msgType;
  private String content;
  private String msgId;
  private String createTime;
  
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
public String getMsgType() {
	return msgType;
}
public void setMsgType(String msgType) {
	this.msgType = msgType;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getMsgId() {
	return msgId;
}
public void setMsgId(String msgId) {
	this.msgId = msgId;
}
public String getCreateTime() {
	return createTime;
}
public void setCreateTime(String createTime) {
	this.createTime = createTime;
}


}
