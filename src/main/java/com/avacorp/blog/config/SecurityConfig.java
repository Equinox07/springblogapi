package com.avacorp.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import security.CustomUserDetailsService;
import security.JwtAuthenticationEntryPoint;
import security.JwtAuthenticationFilter;
import security.JwtTokenProvider;
import security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;

//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
    @Bean
    UserDetailsService userDetailService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService(){
        return new UserDetailsServiceImpl();
    }

//    @Autowired
    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint (){
        return new JwtAuthenticationEntryPoint();
    }
    @Bean
    public JwtTokenProvider jwtTokenProvider(){
        return new JwtTokenProvider();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailService()).passwordEncoder(passwordEncoder());
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // ALTHOUGH THIS SEEMS LIKE USELESS CODE,
        // IT'S REQUIRED TO PREVENT SPRING BOOT AUTO-CONFIGURATION
        return super.authenticationManagerBean();
    }


    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//       UserDetails avalon = User.builder().username("avalon").password(passwordEncoder().encode("password")).roles("USER").build();
//       UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
//       return new InMemoryUserDetailsManager(avalon, admin);
//    }

}
