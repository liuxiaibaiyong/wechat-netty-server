package com.wx.nettyserver;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class TalkClient4Byte {



    public static void main(String[] args) throws IOException {
        try {
                        //创建一个客户端socket
                      Socket socket = new Socket("localhost",80);
                        //向服务器端传递信息
                      OutputStream ots = socket.getOutputStream();
                       PrintWriter pw = new PrintWriter(ots);
                     pw.write("用户名：admin;密码：123");
                        pw.flush();
                   //关闭输出流
                       socket.shutdownOutput();
                        //获取服务器端传递的数据
                       InputStream is = socket.getInputStream();
                      InputStreamReader isr = new InputStreamReader(is);
                      BufferedReader br = new BufferedReader(isr);
                       String info = null;
                        while((info=br.readLine())!=null){
                           System.out.println("我是客户端，服务器说："+info);
                        }
                       //关闭资源
                     br.close();
                      isr.close();
                        is.close();
                        pw.close();
                      ots.close();
                         socket.close();
                     } catch (UnknownHostException e) {
                      e.printStackTrace();
                  } catch (IOException e) {
                       e.printStackTrace();
                  }
           }
}