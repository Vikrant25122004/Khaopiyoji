package com.Khaopiyoji.Khaopiyoji.Config;

import com.Khaopiyoji.Khaopiyoji.Service.Customeruserdetailimpls;
import com.Khaopiyoji.Khaopiyoji.Service.VendorDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SpringSecurity {
    @Autowired
    private Customeruserdetailimpls customeruserdetailimpls;
    @Autowired
    private VendorDetailService vendorDetailService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(request -> request
                .requestMatchers("/customer/**").hasRole("CUSTOMER")
                        .requestMatchers("/Vendor/**").hasRole("VENDORS")
                .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(httpBasic -> httpBasic.realmName("Khaopiyoji") )
                .authenticationProvider(customerAuthenticationProvider())
                .authenticationProvider(vendorAuthenticationProvider())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider customerAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customeruserdetailimpls);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public DaoAuthenticationProvider vendorAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(vendorDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
