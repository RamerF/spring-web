package io.github.ramerf.mq.controller;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  /** The constant FOO_INPUT_CONDITION. */
  public static final String FOO_INPUT_CONDITION =
      "headers['" + MqChannelService.INPUT + "'] eq '1'";

  /**
   * 获取消息,input.
   *
   * @param foo the foo
   * @param headers the headers
   * @param inputVal the header name
   */
  @StreamListener(MqChannelService.INPUT)
  public void getMessage(
      @Payload Foo foo,
      @Headers Map<String, Object> headers,
      @Header(name = MqChannelService.INPUT) String inputVal) {
    log.info("getMessage:[foo:{},headers:{},inputVal:{}]", foo, headers, inputVal);
  }

  /**
   * 指定条件获取消息,input.
   *
   * @param foo the foo
   */
  @StreamListener(value = MqChannelService.INPUT, condition = FOO_INPUT_CONDITION)
  public void getMessageWithCondition(Foo foo) {
    log.info("getMessageWithCondition:[{}]", foo);
  }
}
