package com.elim.learn.mybatis.dao;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.elim.learn.mybatis.model.SysWfProcess;

public interface SysWfProcessMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysWfProcess record);

    int insertSelective(SysWfProcess record);

    SysWfProcess selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysWfProcess record);

    int updateByPrimaryKey(SysWfProcess record);
    
    SysWfProcess singleSql1ToN(Integer id);
    
    List<SysWfProcess> findByForEach(Set<Entry<Integer, Integer>> set);
    
    List<SysWfProcess> findByForEach2(List<Integer> ids);
    
}