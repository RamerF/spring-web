package io.github.ramerf.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 账户模块.
 *
 * @author ramer
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AccountApplication {
  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(AccountApplication.class, args);
  }
}
