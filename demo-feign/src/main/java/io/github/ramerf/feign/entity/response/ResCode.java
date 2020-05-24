package io.github.ramerf.feign.entity.response;

import io.github.ramerf.wind.core.entity.response.ResultCode;
import lombok.ToString;

/**
 * 响应码,格式: 模块_操作状态_操作类型.
 *
 * @author Tang Xiaofeng
 * @since 2020/5/21
 */
@ToString
public class ResCode extends ResultCode {
  public static final ResultCode ACCOUNT_FAIL_GET_BY_ID = of("E9100", "哇哦 \uD83D\uDE05,系统开小差了,请稍后重试");

  protected ResCode(final String code, final String desc) {
    super(code, desc);
  }
}
