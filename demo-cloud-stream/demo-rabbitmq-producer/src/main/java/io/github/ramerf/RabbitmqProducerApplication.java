package io.github.ramerf;

import io.github.ramerf.mq.controller.MqChannelService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/** @author ramer */
@EnableBinding({MqChannelService.class})
@SpringBootApplication
public class RabbitmqProducerApplication {
  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(RabbitmqProducerApplication.class, args);
  }
}
