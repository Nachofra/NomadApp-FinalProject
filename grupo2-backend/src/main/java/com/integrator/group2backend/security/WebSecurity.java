package com.integrator.group2backend.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private AuthenticationConfig authenticationConfig;
//    private RestAuthEntryPoint restAuthEntryPoint;

    @Value("${frontendUrl}")
    private String frontendUrl;

    @Autowired
    public WebSecurity(AuthenticationConfig authenticationConfig) {
        this.authenticationConfig = authenticationConfig;
        //this.restAuthEntryPoint = restAuthEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        // This bean is created if needed
        return new ObjectMapper();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(this.frontendUrl));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH",
                "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type",
                "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        JWTAuthenticationFilter jwtFilter = new JWTAuthenticationFilter(authenticationManager());
        jwtFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());

//        httpSecurity.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class); //permito que pasen las peticiones OPTIONS
        httpSecurity.cors().and()
                .csrf().disable().authorizeRequests()

                .antMatchers("/reservation/**").authenticated().and()                    //Solo usamos JWT en Reservation endpoint
                .addFilter(jwtFilter)
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .exceptionHandling().and()
//                .authenticationEntryPoint(this.restAuthEntryPoint)   //si no pasa sale con error 401
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        HeadersConfigurer<HttpSecurity> headers = httpSecurity.headers();
        headers.cacheControl();
        headers.frameOptions();
        headers.xssProtection();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Se define la clase que recupera los usuarios y el algoritmo para procesar las passwords
        auth.userDetailsService(this.authenticationConfig).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }
}
