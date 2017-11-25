package org.ramer.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class SecurityEntryPoint implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        log.debug(" SecurityRedirect commence : [{}]", request.getRequestURL());
        log.debug(" SecurityRedirect commence : [{}]", request.getRequestURI());
        log.debug(" SecurityRedirect commence : [{}]", request.getHeader("Referer"));
        log.debug(" SecurityRedirect commence : [{}]", e);
        response.sendRedirect(request.getHeader("Referer"));
    }
}
