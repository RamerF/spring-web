package io.github.ramerf.feign.config;

import com.fasterxml.jackson.databind.*;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import io.github.ramerf.wind.annotation.InjectDefault;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Tang Xiaofeng
 * @since 2020/5/17
 */
@Configuration
@EnableSwagger2
public class CommonBean {
  @Value("${spring.swagger.enable:false}")
  private boolean enableSwagger;

  /** 指定ribbon负载均衡策略. */
  @Bean
  public IRule randomRule() {
    // 包含重试机制
    return new RetryRule(new RibbonRule());
  }

  @Bean
  @LoadBalanced
  public RestTemplate lbRestTemplate(ObjectMapper objectMapper) {
    RestTemplate restTemplate = new RestTemplate();
    final List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
    // 使用UTF-8字符集,1的由来,看RestTemplate构造器就明白了
    converters.set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    // 保持自定义的objectMapper生效
    converters.stream()
        .filter(MappingJackson2HttpMessageConverter.class::isInstance)
        .forEach(
            converter ->
                ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(objectMapper));
    return restTemplate;
  }

  @Bean
  @InjectDefault
  public RestTemplate defaultRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    final ObjectMapper mapper = new ObjectMapper();
    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    restTemplate.getMessageConverters().stream()
        .filter(MappingJackson2HttpMessageConverter.class::isInstance)
        .forEach(
            converter -> ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(mapper));
    restTemplate
        .getMessageConverters()
        .set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    return restTemplate;
  }

  /**
   * Api docket.
   *
   * @return the docket
   */
  @Bean
  public Docket api() {
    ParameterBuilder parameterBuilder = new ParameterBuilder();
    List<Parameter> parameters = new ArrayList<>();
    parameterBuilder
        .name("Authorization")
        .description("Authorization")
        .modelRef(new ModelRef("string"))
        .parameterType("header")
        .required(false)
        .build();
    parameters.add(parameterBuilder.build());
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .enable(enableSwagger)
        .select()
        .apis(RequestHandlerSelectors.basePackage("io.github.ramerf"))
        .paths(PathSelectors.any())
        .build()
        .globalOperationParameters(parameters);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("项目接口文档")
        .description("项目描述")
        .contact(
            new Contact(
                "Tang Xiaofeng", "https://github.com/ramerf/spring-web.git", "1390635973@qq.com"))
        .version("1.0.0")
        .build();
  }
}
