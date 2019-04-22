package com.wx.nettyserver.tool;

import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.http.MethodNotSupportedException;

import java.io.IOException;
import java.util.Map;

public interface IRequestParser {
    void parse(FullHttpRequest fullReq,Map paramMap) throws IOException, MethodNotSupportedException;
}
