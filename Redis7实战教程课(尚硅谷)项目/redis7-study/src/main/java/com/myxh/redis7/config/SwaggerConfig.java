package com.myxh.redis7.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author MYXH
 * @date 2023/11/28
 */
@Configuration
public class SwaggerConfig
{
    @Bean
    public GroupedOpenApi webApiConfig()
    {
        return GroupedOpenApi.builder()
                .group("com.myxh.redis7")
                .pathsToMatch("/order/**")
                .addOpenApiMethodFilter(method -> true)
                .build();
    }

    private OpenAPI webApiInfo()
    {
        return new OpenAPI()
                .info(new Info().title("SpringBoot 利用 Swagger 构建 API 接口文档 " + "\t" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()))
                        .description("SpringBoot + Redis 整合")
                        .version("1.0")
                        .license(new License().name("GNU GENERAL PUBLIC LICENSE Version 3")
                                .url("https://www.gnu.org/licenses/gpl-3.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot + Redis 整合")
                        .url("https://www.myxh.com"));
    }
}
