package backend.resumerryv2.global.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticatedPrincipal;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Server;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Value("${swagger-ui.path}")
    private String swaggerPath;

    @Bean
    public Docket parseApi() {
        return new Docket(DocumentationType.OAS_30)
                .ignoredParameterTypes(AuthenticatedPrincipal.class)
                .servers(serverInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("backend.resumerryv2"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Resumerry Swagger")
                .description("testing resumeery api")
                .version("1.0.0")
                .build();
    }

    private Server serverInfo() {
        return new Server("local",
                swaggerPath,
                "for local usages",
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

}
