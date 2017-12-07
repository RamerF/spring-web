package org.ramer.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by RAMER on 11/23/2017.
 */
@Configuration
@EnableSwagger2
public class ApiDocConfig{
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("org.ramer.demo.controller")).paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder().title("Spring-Web API").description("Sample web project use Spring deploy.")
                .version("1.0.0").license("GPL-3.0")
                .licenseUrl("https://github.com/RamerF/spring-web/blob/master/LICENSE")
                .contact(new Contact("Xiaofeng Tang", "https://github.com/RamerF", "feng1390635973@gmail.com")).build();
    }
}
