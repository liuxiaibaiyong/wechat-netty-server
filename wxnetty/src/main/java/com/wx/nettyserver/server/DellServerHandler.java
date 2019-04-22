package com.wx.nettyserver.server;

import com.wx.nettyserver.config.ServiceConfig;
import com.wx.nettyserver.constant.IConstantTrade;
import com.wx.nettyserver.pojo.request.SignatureRequest;
import com.wx.nettyserver.pojo.response.Response;
import com.wx.nettyserver.service.IService;
import com.wx.nettyserver.spring.SpringContext;
import com.wx.nettyserver.tool.CheckUtil;
import com.wx.nettyserver.tool.IRequestParser;
import com.wx.nettyserver.tool.NettyMap;
import com.wx.nettyserver.tool.ObjectUtils;
import com.wx.nettyserver.tool.RequestParser;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.security.Signature;
import java.util.HashMap;
import java.util.Map;
import com.wx.nettyserver.tool.UrlUtils;
import com.wx.nettyserver.tool.XmlUtils;

public class DellServerHandler extends ChannelInboundHandlerAdapter {
	
    public static final Logger log = LoggerFactory.getLogger(DellServerHandler.class);
    
    
    @Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	ServiceConfig serviceConfig = (ServiceConfig)SpringContext.SINGLETON.getBean("serviceConfig");
    	String responseContent = IConstantTrade.ERROR;//默认失败
        Map paramMap = new HashMap();
        Response response = new Response();
        String result="";
        log.info("start dell");
        if(msg instanceof FullHttpRequest){
            String content = ObjectUtils.convertByteBufToString(((HttpContent)msg).content());
            if(!StringUtils.isEmpty(content)){
              paramMap.put(IConstantTrade.XML, IConstantTrade.XMLHEAD+content);
            }
        	IRequestParser  requestParser = (IRequestParser)SpringContext.SINGLETON.getBean("requestParser");
        	FullHttpRequest request = (FullHttpRequest)msg;
            requestParser.parse(request,paramMap);
            log.info(paramMap.toString());
            log.info(request.getUri());
            //String route = UrlUtils.deelUrl(request.getUri());
            if(validate(paramMap)){
            	if(paramMap.containsKey("echostr")){
            		responseContent = String.valueOf(paramMap.get("echostr"));
            	} else if(paramMap.containsKey(IConstantTrade.XML)){
            		String hexCode = XmlUtils.getHexHash(String.valueOf(paramMap.get(IConstantTrade.XML)));
            		if(serviceConfig.isExistsKey(hexCode)){
            		  String route = StringUtils.uncapitalize(serviceConfig.getServiceName(hexCode));
            		  IService service = (IService) SpringContext.SINGLETON.getBean(route);
            		  response = service.complete(ObjectUtils.map2Obj(paramMap, Class.forName(serviceConfig.getValue(hexCode))));
            		  responseContent = response.getText();
            		}
            	}	
            }else {
            	ctx.close();
            	return ;
            }
        } else {
        	result="未知请求!";
            log.info("unknown request");
            send(ctx,result, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        //Discard the received data silently
        //ByteBuf是一个引用计数对象实现ReferenceCounted，他就是在有对象引用的时候计数+1，无的时候计数-1，当为0对象释放内存
        try {
        	if(IConstantTrade.ERROR.equals(responseContent) && IConstantTrade.ERROR.equals(response.getText())){
        		send(ctx,responseContent,HttpResponseStatus.EXPECTATION_FAILED);
        		log.info("error");
        	}else{
        		send(ctx,responseContent,HttpResponseStatus.OK,response.getContentType());
        		log.info("ok");
        	}
        } finally {
        	paramMap.clear();
        	paramMap = null;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 发送的返回值
     * @param ctx     返回
     * @param context 消息
     * @param status 状态
     */
    private void send(ChannelHandlerContext ctx, String context,HttpResponseStatus status) {
    	log.info(context);
    	ByteBuf buf = Unpooled.copiedBuffer(context, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, buf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8").set(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());
        ctx.channel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
    
    private void send(ChannelHandlerContext ctx, String context,HttpResponseStatus status,String contentType) {
    	log.info(context);
    	ByteBuf buf = Unpooled.copiedBuffer(context, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, buf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType).set(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());
        ctx.channel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
    
    private boolean validate(Map map) throws Exception{
    	SignatureRequest request = null;
    	try{
    	 request = (SignatureRequest) ObjectUtils.map2Obj(map,SignatureRequest.class);
    	 return CheckUtil.checkSignature(request.getSignature(), request.getTimestamp(), request.getNonce());
    	} finally {
    		request = null;
    	}
    }
    


}
