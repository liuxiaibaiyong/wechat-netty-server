package com.wx.nettyserver.service;

import org.springframework.stereotype.Service;

import com.wx.nettyserver.constant.IConstantTrade;
import com.wx.nettyserver.entity.WxUser;
import com.wx.nettyserver.pojo.request.SubscribeMessageRequest;
import com.wx.nettyserver.pojo.response.Response;
import com.wx.nettyserver.tool.ResponseEnum;

/**
 * 文本信息处理
 * @author Administrator
 *
 */
@Service("subscribeMessageService")
public class SubscribeMessageService extends  NettyService<SubscribeMessageRequest>{
    
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5034369448452005116L;

	public Response service(SubscribeMessageRequest obj) throws Exception {
		switch(obj.getEvent()){
		  case IConstantTrade.EVENT_SUBSCRIBE:
			  WxUser user = new WxUser();
			  user.setWxUserName(obj.getFromUserName());
			  super.insert("insertWxUser", user);
		}
		String content = "大家好:\r\n"+"  欢迎来到掌柜掌舵 https://baidu.com";
        return ResponseEnum.MSG_TEXT_TYPE.response(obj,content);
	}

}
