package com.wx.nettyserver.tool;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostMultipartRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import org.apache.http.MethodNotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wx.nettyserver.server.DellServerHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("requestParser")
public class RequestParser implements IRequestParser{
	 public static final Logger log = LoggerFactory.getLogger(RequestParser.class);
        /**
         * 解析请求参数
         * @return 包含所有请求参数的键值对, 如果没有参数, 则返回空Map
         *
         * @throws IOException
         */
        public void parse(FullHttpRequest fullReq,Map parmMap) throws IOException, MethodNotSupportedException {
            HttpMethod method = fullReq.method();
            
            if (HttpMethod.GET == method) {
            	log.info("request way:GET");
                // 是GET请求
            	getWay(parmMap, fullReq);
            } else if (HttpMethod.POST == method) {
            	log.info("request way:POST");
                // 是POST请求
            	postWay(parmMap, fullReq);
            	getWay(parmMap, fullReq);
            } else {
                // 不支持其它方法
                throw new MethodNotSupportedException(""); // 这是个自定义的异常, 可删掉这一行
            }
        }
        
        public void getWay(Map paramMap,FullHttpRequest fullReq){
        	QueryStringDecoder decoder = new QueryStringDecoder(fullReq.uri());
            decoder.parameters().entrySet().forEach( entry -> {
                // entry.getValue()是一个List, 只取第一个元素
                paramMap.put(entry.getKey(), entry.getValue().get(0));
            });
        }
        
        public void postWay(Map parmMap,FullHttpRequest  fullReq) throws IOException{
        	HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(fullReq);
            decoder.offer(fullReq);
            List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();

            for (InterfaceHttpData parm : parmList) {

                Attribute data = (Attribute) parm;
                parmMap.put(data.getName(), data.getValue());
            }
        }
    }

