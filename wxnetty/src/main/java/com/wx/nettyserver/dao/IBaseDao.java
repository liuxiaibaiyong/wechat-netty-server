package com.wx.nettyserver.dao;

public interface IBaseDao<T> {
    int insert(String sqlId,Object obj);
    String selectOne(String sqlId,Object obj);
}
