package com.wx.nettyserver.service;

import java.util.Map;

import com.wx.nettyserver.pojo.response.Response;

public interface IService<T>{
   public Response complete(Object obj) throws Exception;
}
