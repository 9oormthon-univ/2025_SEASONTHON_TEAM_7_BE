package goormthonuniv.team_7_be.common.auth.config;

import goormthonuniv.team_7_be.common.auth.filter.JwtAuthenticationFilter;
import goormthonuniv.team_7_be.common.auth.resolver.AuthArgumentResolver;
import goormthonuniv.team_7_be.common.config.CustomOAuth2UserService;
import goormthonuniv.team_7_be.common.handler.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    // JWT 인증과 Argument Resolver에 필요한 의존성
    private final AuthArgumentResolver authArgumentResolver;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // OAuth2 로그인에 필요한 의존성
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. 기본적인 비활성화 설정
                .csrf(AbstractHttpConfigurer::disable)
                .cors(auth -> auth.configurationSource(corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 2. JWT 인증 필터를 가장 먼저 배치
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // 3. OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService))
                        .successHandler(oAuth2LoginSuccessHandler)
                )

                // 4. 접근 권한 설정 (가장 마지막에 배치)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login/**", "/oauth2/**", "/auth/**", "/v1/auth",
                                "/swagger-ui/**", "/v3/api-docs/**",
                                "/ws-connect/**", "/chat-test", "/pub/**", "/sub/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/auth/**").permitAll()
                        .anyRequest().authenticated() // 개발 편의를 위해 모든 요청 허용 (추후 변경 필요)
                );

        return http.build();
    }

    // CORS 설정 (모두 허용)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // 사용자 인증 Argument Resolver 추가
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authArgumentResolver);
    }
}