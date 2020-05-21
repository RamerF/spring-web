package io.github.ramerf.account.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

  /**
   * Api docket.
   *
   * @return the docket
   */
  @Bean
  public Docket api() {
    ParameterBuilder parameterBuilder = new ParameterBuilder();
    List<Parameter> parameters = new ArrayList<>();
    parameterBuilder.name("Authorization").description("Authorization").modelRef(new ModelRef("string"))
        .parameterType("header").required(false).build();
    parameters.add(parameterBuilder.build());
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).enable(enableSwagger).select()
        .apis(RequestHandlerSelectors.basePackage("io.github.ramerf")).paths(PathSelectors.any()).build()
        .globalOperationParameters(parameters);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("项目接口文档").description("项目描述")
        .contact(new Contact("Tang Xiaofeng", "https://github.com/ramerf/spring-web.git", "1390635973@qq.com"))
        .version("1.0.0").build();
  }
}
