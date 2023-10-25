package io.swagger.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-10-25T07:07:42.427263843Z[GMT]")
@Configuration
public class SwaggerDocumentationConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bank Statement API")
                        .description(
                                "No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)")
                        .termsOfService("")
                        .version("1.0.0")
                        .license(new License()
                                .name("")
                                .url("http://unlicense.org"))
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .email("")));
    }

}
