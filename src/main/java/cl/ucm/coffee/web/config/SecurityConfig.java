package cl.ucm.coffee.web.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    // @Autowired
    // public SecurityConfig(JwtFilter jwtFilter) {
    // this.jwtFilter = jwtFilter;
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/coffee/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/user/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/coffee/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/coffee/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/coffee/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/testimonials/**").hasAnyRole("ADMIN", "CUSTOMER")
                .requestMatchers(HttpMethod.POST, "/api/testimonials/**").hasAnyRole("ADMIN","CUSTOMER")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(
                        (request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            Map<String, String> errorResponse = new HashMap<>();
                            errorResponse.put("error", "Accesso Denegado");
                            errorResponse.put("message", "No tienes permiso para realizar esta accion");
                            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                        })
                .authenticationEntryPoint(
                        (request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            Map<String, String> errorResponse = new HashMap<>();
                            errorResponse.put("error", "Unauthorized");
                            errorResponse.put("message", "Por favor, proporcione credenciales validas");
                            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                        })
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
