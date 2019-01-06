package com.elim.learn.spring.test.bean;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.spring.bean.UtilNamespaceBean;

/**
 * 验证util命名空间
 * @author Elim
 * 2018年1月27日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-namespace.xml")
public class UtilNamespaceTest {

    @Autowired
    private UtilNamespaceBean bean;
    
    @Autowired
    private Properties utilProperties;
    @Resource(name="utilList")
    private List<Object> utilList;
    @Resource(name="utilSet")
    private Set<Object> utilSet;
    @Resource(name="utilMap")
    private Map<String, Object> utilMap;
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Test
    public void testProperties() {
        Assert.assertEquals("a", this.utilProperties.getProperty("a"));
        Assert.assertEquals("b", this.utilProperties.getProperty("b"));
        Assert.assertEquals("c", this.utilProperties.getProperty("c"));
        Assert.assertEquals("a", bean.getProperties().getProperty("a"));
    }
    
    @Test
    public void testList() {
        Assert.assertEquals(2, this.utilList.size());
        System.out.println(this.bean.getList());
    }
    
    @Test
    public void testSet() {
        System.out.println(this.utilSet);
        System.out.println(this.utilSet.getClass());
        System.out.println(this.utilSet.iterator().next().getClass());
        System.out.println(this.bean.getSet());
    }
    
    @Test
    public void testMap() {
        System.out.println(this.utilMap);
        System.out.println(this.bean.getMap());
    }
    
    @Test
    public void constant() {
        System.out.println(this.applicationContext.getBean("abc"));
        System.out.println(this.applicationContext.getBean("beanABC"));
    }
    
}
