package io.github.ramerf.mq.controller;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * The interface Mq channel service.
 *
 * @author ramer
 */
public interface MqChannelService {
  /** The constant INPUT. */
  String INPUT = "mq-input";

  /**
   * Subscribable channel subscribable channel.
   *
   * @return the subscribable channel
   */
  @Input(INPUT)
  SubscribableChannel subscribableChannel();
}
