/**
 * 
 */
package com.elim.learn.mybatis.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;

/**
 * @author Elim
 * 2016年12月20日
 */
public class MyCache implements Cache {

	private String id;
	private String name;//Name，故意加这么一个属性，以方便演示给自定义Cache的属性设值
	
	private Map<Object, Object> cache = new HashMap<Object, Object>();
	
	/**
	 * 构造方法。自定义的Cache实现一定要有一个id参数
	 * @param id
	 */
	public MyCache(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void putObject(Object key, Object value) {
		this.cache.put(key, value);
	}

	@Override
	public Object getObject(Object key) {
		return this.cache.get(key);
	}

	@Override
	public Object removeObject(Object key) {
		return this.cache.remove(key);
	}

	@Override
	public void clear() {
		this.cache.clear();
	}

	@Override
	public int getSize() {
		return this.cache.size();
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return null;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
