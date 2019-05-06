package com.elim.learn.spring.cloud.config.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Elim
 * 2019/4/24
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class TextEncryptorTest {

  @Autowired
  private TextEncryptor textEncryptor;

  @Test
  public void test() {
    for (int i=0; i<10; i++) {
      String plainText = "ABCDEFG";
      String encryptedText = this.textEncryptor.encrypt(plainText);
      String decryptedText = this.textEncryptor.decrypt(encryptedText);
      System.out.println(plainText + "----------" + encryptedText + "-----------" + decryptedText);
    }
  }

}
