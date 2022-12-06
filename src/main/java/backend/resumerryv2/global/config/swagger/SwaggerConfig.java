/* Licensed under InfoCat */
package backend.resumerryv2.global.config.swagger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.AuthenticatedPrincipal;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

  @Value("${swagger-ui.path}")
  private String swaggerPath;

  @Bean
  public Docket parseApi() {

    return new Docket(DocumentationType.OAS_30)
        .ignoredParameterTypes(AuthenticatedPrincipal.class)
        .globalResponses(HttpMethod.GET, globalResponse())
        .securitySchemes(apiKeys())
        .servers(serverInfo())
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors.basePackage("backend.resumerryv2"))
        .paths(PathSelectors.ant("/**"))
        .paths(Predicate.not(PathSelectors.regex("/admin.*")))
        .build()
        .apiInfo(apiInfo());
  }

  private List<Response> globalResponse() {
    return Arrays.asList(
        new ResponseBuilder().code("200").description("OK").build(),
        new ResponseBuilder().code("400").description("Bad Request").build(),
        new ResponseBuilder().code("500").description("Internal Error").build());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Resumerry Swagger")
        .description("testing resummery api")
        .version("1.0.0")
        .build();
  }

  private Server serverInfo() {
    return new Server("", swaggerPath, "", Collections.emptyList(), Collections.emptyList());
  }

  private List<SecurityScheme> apiKeys() {

    return Arrays.asList(
        new ApiKey("Authorization", "Bearer", "cookies"),
        new ApiKey("Validation", "Validation", "header"));
  }
}
