package io.github.ramerf.feign.controller;

import io.github.ramerf.feign.entity.pojo.Account;
import io.github.ramerf.feign.entity.response.ResCode;
import io.github.ramerf.feign.service.AccountService;
import io.github.ramerf.wind.core.entity.response.Rs;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author Tang Xiaofeng
 * @since 2020/5/17
 */
@Slf4j
@RestController
@RequestMapping("/account")
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class AccountController {
  @Resource private AccountService service;
  @Autowired @LoadBalanced private RestTemplate restTemplate;

  @GetMapping("/get-by-id")
  @ApiOperation("通过id获取账户详情")
  public ResponseEntity<Rs<String>> getById() {
    final ResponseEntity<Rs<Account>> response = service.getById(1L);
    final Account account = Rs.requireNonNull(response, ResCode.ACCOUNT_FAIL_GET_BY_ID);
    return Rs.ok(account);
  }

  @GetMapping("/get-by-id2")
  @ApiOperation("通过id获取账户详情")
  public ResponseEntity<Rs<String>> getById2() {
    final ResponseEntity<Rs<Account>> response =
        restTemplate.exchange(
            "http://DEMO-ACCOUNT/demo-account/account/1",
            HttpMethod.GET,
            new HttpEntity<>(null),
            new ParameterizedTypeReference<Rs<Account>>() {});
    final Account account = Rs.requireNonNull(response, ResCode.ACCOUNT_FAIL_GET_BY_ID);
    return Rs.ok(account);
  }
}
