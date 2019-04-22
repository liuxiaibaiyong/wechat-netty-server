package com.wx.nettyserver.server;

import ch.qos.logback.core.net.ssl.SSLContextFactoryBean;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.xml.XmlDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.logging.InternalLogLevel;
import org.springframework.beans.factory.support.SimpleSecurityContextProvider;

import javax.net.ssl.SSLEngine;


public class ChannelProcess extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 空闲状态检查  间隔无消息,断开连接
       //pipeline.addLast("prepender", new LengthFieldPrepender(4, false));
        pipeline.addLast("idleStateCheck", new IdleStateHandler(0, 0, 600));
        //pipeline.addLast("xml-decoder", new MessageDecoder());
        //pipeline.addLast("handlerOuts",new XmlServerHandler());
        //pipeline.addLast("xml-decoder", new MessageEncoder());
        pipeline.addLast("decoder",new HttpRequestDecoder());
        pipeline.addLast("encoder",new HttpResponseEncoder());
        pipeline.addLast("deflater", new HttpContentCompressor());
        pipeline.addLast("aggregator", new HttpObjectAggregator(10*1024*1024));
        pipeline.addLast("handlerOut",new DellServerHandler());
        
       /* pipeline.addLast("decoder", new HttpRequestDecoder());*/
        /**
         * http-response解码器
         * http服务器端对response编码
         */
       /* pipeline.addLast("encoder", new HttpResponseEncoder());*/
 
        /**
         * 压缩
         * Compresses an HttpMessage and an HttpContent in gzip or deflate encoding
         * while respecting the "Accept-Encoding" header.
         * If there is no matching encoding, no compression is done.
         */
 
        //pipeline.addLast("handler", new HttpDemoHandler());

    }
}
