package io.github.ramerf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 账户模块.
 *
 * @author ramer
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"io.github.ramerf.wind", "io.github.ramerf"})
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
