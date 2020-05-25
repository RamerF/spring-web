package io.github.ramerf.account.controller;

import io.github.ramerf.account.entity.pojo.Account;
import io.github.ramerf.account.service.AccountService;
import io.github.ramerf.wind.core.entity.response.Rs;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Tang Xiaofeng
 * @since 2020/5/17
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
  @Resource private AccountService service;

  @GetMapping("/halo")
  public ResponseEntity<Rs<String>> halo() {
    return Rs.ok("halo");
  }

  @GetMapping("/{id}")
  public ResponseEntity<Rs<String>> getById(@PathVariable("id") final long id) {
    final Account account = service.getById(id);
    log.info("getById:[{}]", account);
    return Rs.ok(account);
  }


}
