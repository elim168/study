package com.elim.learn.guava.networking;

import com.google.common.net.InternetDomainName;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Elim
 * 19-3-16
 */
public class InternetDomainNameTest {

  @Test
  public void test() {
    Assert.assertTrue(InternetDomainName.isValid("www.baidu.com.a"));//输入一个不存在的域名，其返回结果也可能是true
    InternetDomainName domainName = InternetDomainName.from("www.baidu.com");
    System.out.println(domainName.hasPublicSuffix());//true
    System.out.println(domainName.publicSuffix());//com
    System.out.println(domainName.registrySuffix());//com
    System.out.println(domainName.hasRegistrySuffix());//true
    System.out.println(domainName.isPublicSuffix());//false
    System.out.println(domainName.hasParent());//true
    System.out.println(domainName.parent());//baidu.com
    System.out.println(domainName.parent().parent());//com

  }

}
