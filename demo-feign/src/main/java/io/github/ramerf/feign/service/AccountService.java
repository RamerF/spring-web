package io.github.ramerf.feign.service;

import io.github.ramerf.feign.entity.pojo.Account;
import io.github.ramerf.feign.service.impl.AccountServiceFallbackFactoryImpl;
import io.github.ramerf.wind.core.entity.response.Rs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Tang Xiaofeng
 * @since 2020/5/17
 */
@FeignClient(name = "DEMO-ACCOUNT", fallbackFactory = AccountServiceFallbackFactoryImpl.class)
public interface AccountService {
  /**
   * Gets by id.
   *
   * @param id the id
   * @return the by id
   */
  @GetMapping("/demo-account/account/{id}")
  ResponseEntity<Rs<Account>> getById(@PathVariable("id") final long id);
}
