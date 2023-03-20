package com.exam.examserver.config;

import com.exam.examserver.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    //This is a method in Java that returns an instance of the AuthenticationManager interface. The AuthenticationManager interface is a part of Spring Security framework and is responsible for authenticating users based on their credentials, such as username and password.
    //
    //The method takes an instance of the AuthenticationConfiguration class as a parameter. The AuthenticationConfiguration class is used to configure the authentication process, such as setting up user details service, authentication provider, and other authentication-related settings.
    //
    //In the provided code, the method simply returns the AuthenticationManager instance obtained from the AuthenticationConfiguration object by calling the getAuthenticationManager() method. This means that the authentication manager used in the application is obtained from the AuthenticationConfiguration object.
    //
    //The AuthenticationManager interface has a single method, authenticate(), which is responsible for authenticating users based on their credentials. When a user attempts to log in, their credentials are passed to the authenticate() method, which uses the configured UserDetailsService to retrieve the user's details, and then uses the configured PasswordEncoder to compare the provided password with the stored hash.
    //
    //If the provided credentials match the stored credentials, the authenticate() method returns an instance of the Authentication interface, which represents a successful authentication. If the provided credentials do not match the stored credentials, the authenticate() method throws an instance of the AuthenticationException interface, which represents a failed authentication.
    //
    //The AuthenticationManager interface is typically used in conjunction with other Spring Security components, such as AuthenticationProvider and UserDetailsService, to provide a complete authentication and authorization solution for a Spring Security-enabled application.
    //
    //In summary, the authenticationManager() method is responsible for returning an instance of the AuthenticationManager interface that is used to authenticate users based on their credentials in a Spring Security-enabled application. This method obtains the AuthenticationManager instance from the AuthenticationConfiguration object.ā
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeHttpRequests() .requestMatchers("/generate-token", "/user/").permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //This is a method in Java that returns an instance of the WebMvcConfigurer interface. The WebMvcConfigurer interface is a part of the Spring Web MVC framework and provides configuration options for web application context, such as configuring CORS (Cross-Origin Resource Sharing) settings.
    //
    //The method overrides the addCorsMappings() method of the WebMvcConfigurer interface to configure the CORS settings for the application. The addCorsMappings() method takes a CorsRegistry object as a parameter, which is used to configure the CORS settings.
    //
    //In the provided code, the addCorsMappings() method configures the CORS settings to allow requests from any origin and with any HTTP method by calling the addMapping() method on the CorsRegistry object with a mapping of "/**" and the allowedMethods() method with "*" as its parameter.
    //
    //The "/**" mapping allows the CORS configuration to be applied to all endpoints in the application, while the "*" parameter for allowedMethods() indicates that any HTTP method is allowed.
    //
    //CORS is a security feature implemented in web browsers that restricts web pages from making requests to a different domain than the one that served the original page. CORS allows the web page to specify which other domains are allowed to access its resources. By default, web browsers enforce CORS to prevent malicious websites from accessing sensitive data from other websites.
    //
    //In summary, the corsConfigurer() method is responsible for returning an instance of the WebMvcConfigurer interface that is used to configure the CORS settings for the application. The addCorsMappings() method is overridden to configure the CORS settings to allow requests from any origin and with any HTTP method. This method is typically used in a Spring Web MVC-enabled application to configure CORS settings for the endpoints.
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*");
            }
        };
    }
}
