package com.homework.app.swagger;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket apiDocket(){
        return new Docket(SWAGGER_2).apiInfo(apiInfo()).forCodeGeneration(true)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                    .paths(PathSelectors.regex("/*/.*"))
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfo("Homework-App API Documentation", "This the API documentation of Homework app", "1.0.0", "", contact(), "", "", Collections.emptyList());
    }

    private Contact contact(){
        return new Contact("Shehan Bartholomeusz", "", "shehan@gmail.com");
    }

    private ApiKey apiKey(){
        return new ApiKey("Token Access", "Authorization", SecurityScheme.In.HEADER.name());
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(securityReference()).build();
    }

    private List<SecurityReference> securityReference(){
        AuthorizationScope[] authorizationScope = { new AuthorizationScope("Unlimited", "Full API Permission") };
        return Collections.singletonList(new SecurityReference("Token Access", authorizationScope));
    }
}
