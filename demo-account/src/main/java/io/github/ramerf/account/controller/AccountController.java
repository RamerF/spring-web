package io.github.ramerf.account.controller;

import io.github.ramerf.account.entity.Account;
import io.github.ramerf.account.entity.Account.Gender;
import io.github.ramerf.wind.core.entity.response.Rs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Tang Xiaofeng
 * @since 2020/5/17
 */
@Slf4j
@RequestMapping("/account")
@RestController
public class AccountController {
  @GetMapping("/enumDemo1")
  public ResponseEntity<Rs<Object>> enumDemo1(@RequestParam("gender") Gender gender) {
    log.info("enumDemo1:[{}]", gender);
    return Rs.ok();
  }

  @GetMapping("/enumDemo2")
  public ResponseEntity<Rs<Object>> enumDemo2(Account account) {
    log.info("enumDemo2:[{}]", account.getGender());
    return Rs.ok();
  }
}
