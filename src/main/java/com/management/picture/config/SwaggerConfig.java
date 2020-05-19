package com.management.picture.config;

import io.swagger.models.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created on 2020/4/23.
 *
 * Swagger文档生成工具配置文件类
 *
 * @author Yue Wu
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.management.picture.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        ApiInfo build = new ApiInfoBuilder()
                // 设置页面标题
                .title("图片管理系统后端api接口文档")
                // 描述
                .description("欢迎访问图片管理系统接口文档，这里是用于前端开发时浏览的后端接口文档。")
                // 定义版本号
                .version("0.0.1").build();
        return build;
    }

}
