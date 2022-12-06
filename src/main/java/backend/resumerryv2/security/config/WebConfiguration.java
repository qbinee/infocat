/* Licensed under InfoCat */
package backend.resumerryv2.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOriginPatterns("*")
        .allowedMethods("*")
        .allowCredentials(true);
  }

  @Bean
  public MethodValidationPostProcessor methodValidationPostProcessor() {
    return new MethodValidationPostProcessor();
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    SortHandlerMethodArgumentResolver sortArgumentResolver =
        new SortHandlerMethodArgumentResolver();
    sortArgumentResolver.setFallbackSort(Sort.by("createdDate"));

    PageableHandlerMethodArgumentResolver pageableArgumentResolver =
        new PageableHandlerMethodArgumentResolver(sortArgumentResolver);
    pageableArgumentResolver.setOneIndexedParameters(true);
    pageableArgumentResolver.setMaxPageSize(500);
    pageableArgumentResolver.setFallbackPageable(PageRequest.of(0, 18));

    argumentResolvers.add(pageableArgumentResolver);
    argumentResolvers.add(sortArgumentResolver);
  }
}
