package com.wx.nettyserver.service;

import org.springframework.stereotype.Service;
import com.wx.nettyserver.constant.IConstantTrade;
import com.wx.nettyserver.pojo.request.TextMessageRequest;
import com.wx.nettyserver.pojo.response.Response;
import com.wx.nettyserver.pojo.response.TextMessageResponse;
import com.wx.nettyserver.tool.XmlUtils;

/**
 * 文本信息处理
 * @author Administrator
 *
 */
@Service("textMessageService")
public class TextMessageService extends  NettyService<TextMessageRequest>{
    
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5034369448452005116L;

	public Response service(TextMessageRequest obj) throws Exception {
		TextMessageResponse response = new TextMessageResponse();
		response.setToUserName(obj.getFromUserName());
		response.setFromUserName(obj.getToUserName());
		response.setCreateTime(String.valueOf(System.currentTimeMillis()/1000L));
		response.setMsgType(IConstantTrade.MSG_TEXT_TYPE);
        response.setContent("宝宝，你真漂亮");
        return new Response(XmlUtils.object2Xml(response));
	}

}
