package com.elim.springboot.https;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Elim
 * 2019/4/22
 */
@Configuration
@Profile("https")
public class TomcatConfiguration {

  @Bean
  public TomcatServletWebServerFactory servletContainer(){
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    tomcat.addContextCustomizers(context -> {
      SecurityConstraint securityConstraint = new SecurityConstraint();
      securityConstraint.setUserConstraint("CONFIDENTIAL");
      SecurityCollection collection = new SecurityCollection();
      collection.addPattern("/*");
      securityConstraint.addCollection(collection);
      context.addConstraint(securityConstraint);
    });
    tomcat.addAdditionalTomcatConnectors(redirectConnector());
    return tomcat;
  }

  private Connector redirectConnector(){
    Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
    connector.setScheme("http");
    connector.setPort(8081);
    connector.setSecure(false);
    connector.setRedirectPort(8888);
    return connector;
  }

}
