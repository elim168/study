/**
 * 
 */
package com.elim.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Elim 2018年3月24日
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setAddCommandLineProperties(false);
        app.run(args);
    }

}
