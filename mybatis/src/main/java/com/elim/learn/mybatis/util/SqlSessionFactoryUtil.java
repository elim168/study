/**
 * 
 */
package com.elim.learn.mybatis.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author Elim 2016年10月13日
 *
 */
public class SqlSessionFactoryUtil {

	private static SqlSessionFactory sqlSessionFactory = null;

	static {
		try {
			InputStream is = Resources.getResourceAsStream("mybatis-config-single.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

}
