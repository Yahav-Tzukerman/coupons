package com.yahav.coupons.security;

import com.yahav.coupons.converters.UserEntityToUserResponseModelConverter;
import com.yahav.coupons.enums.Role;
import com.yahav.coupons.security.jwt.JwtAuthorizationFilter;
import com.yahav.coupons.security.jwt.JwtProvider;
import com.yahav.coupons.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CouponsUserDetails couponsUserDetails;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserEntityToUserResponseModelConverter userEntityToUserResponseModelConverter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(couponsUserDetails).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()
                .antMatchers(HttpMethod.POST, "/customers").permitAll()
                .antMatchers(HttpMethod.GET, "/coupons/**").permitAll()
                .antMatchers(HttpMethod.GET, "/companies/**").permitAll()
                .antMatchers(HttpMethod.GET, "/categories").permitAll()
                .antMatchers(HttpMethod.POST, "/purchases").hasAnyRole(Role.CUSTOMER.name())
                .antMatchers(HttpMethod.GET, "/purchases").hasAnyRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/purchases/byUser/**").hasAnyRole(Role.ADMIN.name(), Role.CUSTOMER.name())
                .antMatchers(HttpMethod.GET, "/purchases/byCoupon/**").hasAnyRole(Role.ADMIN.name(), Role.COMPANY.name())
                .antMatchers(HttpMethod.GET, "/purchases/byCompany/**").hasAnyRole(Role.ADMIN.name(), Role.COMPANY.name())
                .antMatchers(HttpMethod.GET, "/purchases/**").hasAnyRole(Role.ADMIN.name(), Role.CUSTOMER.name())
                .antMatchers(HttpMethod.PUT, "/customers").hasAnyRole(Role.CUSTOMER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/coupons").hasAnyRole(Role.COMPANY.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/customers/**").hasAnyRole(Role.CUSTOMER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/customers").hasAnyRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/customers/**").hasAnyRole(Role.CUSTOMER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/users").hasAnyRole(Role.COMPANY.name(), Role.CUSTOMER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/users/**").hasAnyRole(Role.COMPANY.name(), Role.CUSTOMER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/users/**").hasAnyRole(Role.ADMIN.name(), Role.CUSTOMER.name())
                .antMatchers(HttpMethod.GET, "/users").hasAnyRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/users/byRole").hasAnyRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/users/companyUsers").hasAnyRole(Role.COMPANY.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/users/**").hasAnyRole(Role.COMPANY.name(), Role.CUSTOMER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/coupons").hasAnyRole(Role.ADMIN.name(), Role.COMPANY.name())
                .antMatchers("/coupons/**").hasAnyRole(Role.ADMIN.name(), Role.COMPANY.name())
                .antMatchers("/users/**").hasAnyRole(Role.ADMIN.name())
                .antMatchers("/purchases/**").hasAnyRole(Role.ADMIN.name())
                .antMatchers("/customers/**").hasAnyRole(Role.ADMIN.name())
                .antMatchers("/categories/**").hasAnyRole(Role.ADMIN.name())
                .antMatchers("/companies/**").hasAnyRole(Role.ADMIN.name())
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("*");
            }
        };
    }
}
