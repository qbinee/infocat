package backend.resumerryv2.security.config;


import backend.resumerryv2.security.JwtAuthenticationFilter;
import backend.resumerryv2.security.exception.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final String BaseURL = "http://127.0.0.1:8080";

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**/*")
                .permitAll()
                .antMatchers("/users/login")
                .permitAll()
                .antMatchers("/users/sample")
                .authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                ;
        return http.build();
    }


}
