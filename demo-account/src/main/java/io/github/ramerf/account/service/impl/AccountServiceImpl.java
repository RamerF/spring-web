package io.github.ramerf.account.service.impl;

import io.github.ramerf.account.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * @author Tang Xiaofeng
 * @since 2020/5/17
 */
@Service
public class AccountServiceImpl implements AccountService {
  @Override
  public <U> U getRepository() throws RuntimeException {
    return null;
  }
}
