package io.github.ramerf.feign.service.impl;

import feign.hystrix.FallbackFactory;
import io.github.ramerf.feign.entity.pojo.Account;
import io.github.ramerf.feign.service.AccountService;
import io.github.ramerf.wind.core.entity.response.Rs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Tang Xiaofeng
 * @since 2020/5/24
 */
@Slf4j
@Component
public class AccountServiceFallbackFactoryImpl implements FallbackFactory<AccountService> {
  @Override
  public AccountService create(Throwable throwable) {
    // 这里定义所有
    return new AccountService() {
      @Override
      public ResponseEntity<Rs<Account>> getById(long id) {
        log.info("getById:[{}]", "服务降级");
        return Rs.ok();
      }
    };
  }
}
