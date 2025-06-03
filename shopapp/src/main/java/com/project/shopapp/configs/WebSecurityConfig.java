package com.project.shopapp.configs;

import com.project.shopapp.filters.JwtTokenFilter;
import com.project.shopapp.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.net.Authenticator;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Value("${api.prefix}")
    private String apiPrefix;

//    @Bean // filter các http hop le
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf(AbstractHttpConfigurer::disable)  // disable csrf
//                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests(request ->
//                {
//                    request
//                            .requestMatchers(
//                                    String.format("%s/users/register", apiPrefix),
//                                    String.format("%s/users/login", apiPrefix),
//                                    String.format("%s/products/images/*", apiPrefix),
//                                    String.format("%s/products/uploads/*", apiPrefix),
//                                    String.format("%s/products/*", apiPrefix)
//                            ).permitAll()
//                            .requestMatchers(HttpMethod.PUT,
//                                    String.format("%s/orders/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.POST,
//                                    String.format("%s/orders/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.DELETE,
//                                    String.format("%s/orders/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.GET,
//                                    String.format("%s/orders/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.GET,
//                                    String.format("%s/categories**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.POST,
//                                    String.format("%s/categories**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.PUT,
//                                    String.format("%s/categories/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.DELETE,
//                                    String.format("%s/categories/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.GET,
//                                    String.format("%s/products**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.POST,
//                                    String.format("%s/products/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.PUT,
//                                    String.format("%s/products/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.DELETE,
//                                    String.format("%s/products/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.PUT,
//                                    String.format("%s/order-details/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.POST,
//                                    String.format("%s/order-details/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.DELETE,
//                                    String.format("%s/order-details/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.GET,
//                                    String.format("%s/order-details/**", apiPrefix)).permitAll()
//                            .anyRequest()

    /// /                            .authenticated()
//                            .permitAll();
//                });
//        httpSecurity.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
//            // config http request trusted or not from client
//            @Override
//            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
//                CorsConfiguration corsConfiguration = new CorsConfiguration();
//                corsConfiguration.setAllowedOrigins(List.of("*"));
//                corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                corsConfiguration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//                corsConfiguration.setExposedHeaders(Arrays.asList("x-auth-token"));
//                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//                source.registerCorsConfiguration("/**", corsConfiguration);
//                httpSecurityCorsConfigurer.configurationSource(source);
//            }
//        });
//        return httpSecurity.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // allow all requests
                );
        return http.build();
    }
}


