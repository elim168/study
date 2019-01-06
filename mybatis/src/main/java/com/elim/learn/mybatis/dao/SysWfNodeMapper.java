package com.elim.learn.mybatis.dao;

import java.util.List;

import com.elim.learn.mybatis.model.SysWfNode;

public interface SysWfNodeMapper {
    int deleteByPrimaryKey(Integer nodeId);

    int insert(SysWfNode record);

    int insertSelective(SysWfNode record);

    SysWfNode selectByPrimaryKey(Integer nodeId);

    int updateByPrimaryKeySelective(SysWfNode record);

    int updateByPrimaryKey(SysWfNode record);
    
    SysWfNode singleSqlNTo1(Integer id);
    
    List<SysWfNode> fuzzyQuery(String nodeCode);
    
}