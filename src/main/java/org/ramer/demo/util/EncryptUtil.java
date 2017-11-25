/*
 *
 */
package org.ramer.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;

/**
 * 加密类
 * @author ramer
 */
@Slf4j
@Component
public class EncryptUtil{
    private static BCryptPasswordEncoder encoder;

    @PostConstruct
    public void initBCryptPasswordEncoder() {
        encoder = new BCryptPasswordEncoder(8, new SecureRandom("ramer".getBytes()));
    }

    public static String execEncrypt(Object string) {
        String encode = encoder.encode(string.toString());
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + "  encrypt: [{}],[{}]", string, encode);
        return encode;
    }

    public static boolean matches(String plainPassword, String encodedPassword) {
        boolean matches = encoder.matches(plainPassword, encodedPassword);
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + "  matches password: [{}]", matches);
        return matches;
    }
}
