package com.dz.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private String version;
    private String title;
    private final String TITLE_FIX = "DZ_project API";

    @Bean
    public Docket apiV1() {
        version = "V1";
        title = TITLE_FIX + version;

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dz"))
                .paths(PathSelectors.ant("/V1/**"))
                .build()
                .apiInfo(getApiInfo(title, version))
                .securitySchemes(Collections.singletonList(getApiKey()))
                .enable(true);
    }

    @Bean
    public Docket apiV2() {
        version = "V2";
        title = TITLE_FIX + version;

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.DZ_project"))
                .paths(PathSelectors.ant("/V2/**"))
                .build()
                .apiInfo(getApiInfo(title, version))
                .securitySchemes(Collections.singletonList(getApiKey()))
                .enable(true);
    }

    private ApiInfo getApiInfo(String title, String version) {
        return new ApiInfo(
                title,
                "Swagger API Docs",
                version,
                "dz.com",
                new Contact("dz", "dz.com", "an6791@naver.com"),
                "Licenses",
                "dz.com",
                new ArrayList<>()
        );
    }

    private ApiKey getApiKey() {
        return new ApiKey("jwtToken", "X-AUTH-TOKEN", "header");
    }
}
