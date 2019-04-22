package com.wx.nettyserver.main;

import com.wx.nettyserver.server.ChannelProcess;
import com.wx.nettyserver.spring.SpringContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * 启动netty
 */
public final class Netty {
    public static final Logger log = LoggerFactory.getLogger(Netty.class);

    private static int SERVER_PORT;
    static{
        loadConfig();
    }
    private Netty(){

     }

    /**
     * 加载配置参数
     */
    public static void loadConfig(){
         try(InputStream in = Netty.class.getClassLoader().getResourceAsStream("config/system.properties")){
             Properties pro = new Properties();
             pro.load(in);
             SERVER_PORT = Integer.parseInt(pro.getProperty("sys.port"));
         } catch(Exception e){
             log.error(e.getMessage());
         }
     }

     public static void main(String[] args){
         SpringContext.SINGLETON.getParentBeanFactory();//加载spring环境
         log.info("start netty");
         EventLoopGroup boosGroup = new NioEventLoopGroup(1);
         EventLoopGroup workGroup = new NioEventLoopGroup(16);
         ServerBootstrap b = new ServerBootstrap();
         b.group(boosGroup,workGroup).channel(NioServerSocketChannel.class)
                 .handler(new LoggingHandler(LogLevel.INFO))
                 .childHandler(new ChannelProcess()).option(ChannelOption.SO_BACKLOG,128)
                 .childOption(ChannelOption.SO_KEEPALIVE,true);
         try {
             ChannelFuture f = b.bind(SERVER_PORT).sync();
             f.channel().closeFuture().sync();
         }catch(InterruptedException e){
            log.error(e.getMessage());
         }finally {
             //资源优雅释放
             boosGroup.shutdownGracefully();
             workGroup.shutdownGracefully();
         }

    }

}
