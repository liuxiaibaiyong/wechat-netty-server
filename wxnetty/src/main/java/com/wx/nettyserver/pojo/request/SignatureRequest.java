package com.wx.nettyserver.pojo.request;

import java.io.Serializable;

public class SignatureRequest implements Serializable{
  private String signature;
  private String timestamp;
  private String nonce;
  private String echostr;
  private String openid;
  
  public String getOpenid() {
	return openid;
  }
  public void setOpenid(String openid) {
	this.openid = openid;
  }
public String getSignature() {
	return signature;
}
public void setSignature(String signature) {
	this.signature = signature;
}
public String getTimestamp() {
	return timestamp;
}
public void setTimestamp(String timestamp) {
	this.timestamp = timestamp;
}
public String getNonce() {
	return nonce;
}
public void setNonce(String nonce) {
	this.nonce = nonce;
}
public String getEchostr() {
	return echostr;
}
public void setEchostr(String echostr) {
	this.echostr = echostr;
}
@Override
public String toString() {
	return "SignatureRequest [signature=" + signature + ", timestamp=" + timestamp + ", nonce=" + nonce + ", echostr="
			+ echostr + "]";
}
 
}
