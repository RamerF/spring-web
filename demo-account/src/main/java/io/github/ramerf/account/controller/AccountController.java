package io.github.ramerf.account.controller;

import io.github.ramerf.wind.core.entity.response.Rs;
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
  @GetMapping("/halo")
  public ResponseEntity<Rs<String>> halo() {
    return Rs.ok("halo");
  }
}
