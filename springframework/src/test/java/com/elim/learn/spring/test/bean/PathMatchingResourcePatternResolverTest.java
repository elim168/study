package com.elim.learn.spring.test.bean;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class PathMatchingResourcePatternResolverTest {
	
	@Test
	public void test() throws Exception {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		//从classpath下获取单个的资源文件，classpath下没有将尝试把资源当做一个UrlResource
		Resource resource = resolver.getResource("classpath:META-INF/spring/applicationContext.xml");
		Assert.assertNotNull(resource);
		Assert.assertNotNull(resource.getInputStream());
		
		Resource[] resources = resolver.getResources("classpath*:META-INF/spring/applicationContext.xml");
		Assert.assertNotNull(resources);
		Assert.assertTrue(resources.length == 1);
		
		resources = resolver.getResources("classpath*:applicationContext*.xml");
		Assert.assertNotNull(resources);
		//笔者的classpath下一共有三个满足applicationContext*.xml的资源文件
		Assert.assertTrue(resources.length == 3);
		
		
		//在指定资源文件的时候也是可以指定路径的，比如把上面的资源定义到类路径下的com.elim.learn.spring包下
		resources = resolver.getResources("classpath*:com/elim/learn/spring/applicationContext*.xml");
		Assert.assertNotNull(resources);
		//笔者的classpath下一共有三个满足applicationContext*.xml的资源文件
		Assert.assertTrue(resources.length == 3);
		
		
		//假设我们的资源文件是按照模块划分的，放在不同的目录下面，比如com.elim.learn.spring路径下有，com.elim2.learn.spring路径下也有
		resources = resolver.getResources("classpath*:com/*/learn/spring/applicationContext*.xml");
		Assert.assertNotNull(resources);
		//com.elim.learn.spring和com.elim2.learn.spring下各有三个applicationContext*.xml形式的资源文件
		Assert.assertTrue(resources.length == 6);
		
		//也可以用两个*表示任意多层的目录
		resources = resolver.getResources("classpath*:com/**/spring/applicationContext*.xml");
		Assert.assertNotNull(resources);
		//com.elim.learn.spring和com.elim2.learn.spring下各有三个applicationContext*.xml形式的资源文件
		Assert.assertTrue(resources.length == 6);
		
		//当前用户目录下取pom.xml文件。
		resource = resolver.getResource("file:pom.xml");
		Assert.assertNotNull(resource);
		Assert.assertNotNull(resource.getInputStream());
	}
	
}
