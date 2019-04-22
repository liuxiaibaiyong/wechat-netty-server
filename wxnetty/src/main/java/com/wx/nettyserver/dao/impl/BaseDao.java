package com.wx.nettyserver.dao.impl;

import com.wx.nettyserver.dao.IBaseDao;
import com.wx.nettyserver.service.NettyService;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Repository("baseDao")
public class BaseDao<T> extends SqlSessionDaoSupport implements IBaseDao<T>, Serializable {
	public static final Logger log = LoggerFactory.getLogger(BaseDao.class);
    @Resource(name="sqlSessionTemplate")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate){
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    
    public String getNameSpace(){
    	String spaceName = "";
    	try{
    	  Type params[] = ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments();
    	  Class<T> entityClass = (Class<T>)params[0];
    	  String entityClassName = entityClass.newInstance().getClass().getSimpleName();
    	  spaceName  = entityClass.getName().replace(".request.",".mapper.")+"Mapper.";
    	}catch(InstantiationException|IllegalAccessException e){
    	  log.error(e.getMessage());
    	}
    	return spaceName;
    }
    /**
     * 插入
     * @param sqlId
     * @param obj
     * @return
     */
     public int insert(String sqlId,Object obj){
        return getSqlSession().insert(getNameSpace()+sqlId,obj);
    }

    /**
     * 查询
     * @param sqlId
     * @param obj
     * @return
     */
    public String selectOne(String sqlId,Object obj){
        return getSqlSession().selectOne(getNameSpace()+sqlId,obj);
    }
    
    /**
     * 查询
     * @param sqlId
     * @param obj
     * @return
     */
    public String selectOne(String sqlId){
        return getSqlSession().selectOne(getNameSpace()+sqlId);
    }
    
    /**
     * 查询
     * @param sqlId
     * @param obj
     * @return
     */
    public Long selectLong(String sqlId){
        return getSqlSession().selectOne(sqlId);
    }
    
    /**
     * 删除
     * @param sqlId
     * @param obj
     * @return
     */
    public int delete(String sqlId,Object obj){
    	return getSqlSession().delete(getNameSpace()+sqlId, obj);
    }
    

    /**
     * 查询List
     * @param sqlId
     * @param obj
     * @return
     */
    public List selectList(String sqlId,Object obj){
    	return (List) getSqlSession().selectList(getNameSpace()+sqlId, obj);
    }
}
