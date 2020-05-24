package io.github.ramerf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Feign模块.
 *
 * @author ramer
 */
@EnableFeignClients(basePackages = "io.github.ramerf.feign.service")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"io.github.ramerf.wind", "io.github.ramerf.feign"})
public class FeignApplication {
  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(FeignApplication.class, args);
  }
}
