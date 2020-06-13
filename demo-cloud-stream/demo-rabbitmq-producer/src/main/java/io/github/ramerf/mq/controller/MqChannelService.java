package io.github.ramerf.mq.controller;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * The interface Mq channel service.
 *
 * @author ramer
 */
public interface MqChannelService {
  /** The constant OUTPUT. */
  String OUTPUT = "mq-output";

  /** The constant INPUT. */
  String INPUT = "mq-input";

  /**
   * Message channel message channel.
   *
   * @return the message channel
   */
  @Output(OUTPUT)
  MessageChannel messageChannel();
}
