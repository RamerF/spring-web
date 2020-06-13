package io.github.ramerf.mq.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

/**
 * cloud-stream-rabbitmq方法示例.
 *
 * @author Tang Xiaofeng
 * @since 2020 /5/17
 */
@Slf4j
@RestController
@RequestMapping("/foo")
public class FooController {
  @Resource private MqChannelService mqService;
  /**
   * 发布消息,output.
   *
   * @return the response entity
   */
  @GetMapping("/send")
  public ResponseEntity<Boolean> sendMessage(
      @RequestParam(name = "inputVal") final String inputVal) {
    final Message<Foo> message =
        MessageBuilder.withPayload(new Foo(1, "ramer"))
            .setHeader(MqChannelService.INPUT, inputVal)
            // 指定分区
            .setHeader("partition", inputVal)
            .build();
    return ResponseEntity.ok(
        mqService
            .messageChannel()
            .send(
                message
                // 设置超时 ,30 * 1000L
                ));
  }
}
