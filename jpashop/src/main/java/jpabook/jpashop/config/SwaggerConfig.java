package jpabook.jpashop.config;


import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //3.0.0 http://localhost:8088/api/swagger-ui/index.html

    RequestParameterBuilder tokenBuilder = new RequestParameterBuilder();
    List<RequestParameter> aParameters = new ArrayList<>();

    private final ApiInfo apiInfo = new ApiInfoBuilder()
        .title("Project/Study 매칭 website")
        .description("프로젝트 메인 API")
        .contact(new Contact("Name", "https://naver.com", "my@email.com"))
        .license("MIT License")
        .version("5.0")
        .build();

    @Bean
    public Docket fileApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//            .globalRequestParameters(aParameters) // 글로벌 파라미터 필요시 추가하기
            .apiInfo(apiInfo)
            .groupName("File")
            .select()
            .apis(RequestHandlerSelectors.basePackage("jpabook.jpashop.controller"))
            // api 필요한 클래스패스 추가하기
            .paths(
                PathSelectors.ant("/**/file/**")
                    .or(PathSelectors.ant("/**/item/**"))
                    .or(PathSelectors.ant("/**/study/**"))
//                PathSelectors.any()
            )
            .build()
            .useDefaultResponseMessages(false);
    }


}
