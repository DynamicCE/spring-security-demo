package com.erkan.spring_security_demo.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public
class SecurityConfiguration {

    private static final Logger log = LoggerFactory.getLogger ( SecurityConfiguration.class );

    @Bean
    public
    SecurityFilterChain securityFilterChain ( HttpSecurity httpSecurity ) {
        try {
            httpSecurity.authorizeHttpRequests ( ( requests ) -> requests
                    .requestMatchers ( "/rest/public" ).permitAll ( )
                    .requestMatchers ( "/rest/admin" ).hasRole ( "admin" )
                    .requestMatchers ( "/rest/user" ).hasRole ( "user" )
            ).httpBasic ( Customizer.withDefaults ( ) );
            return httpSecurity.build ( );
        } catch (Exception e) {
            log.error ( "Spring Security yapılandırması sırasında bir hata oluştu", e );
            throw new RuntimeException ( "Beklenmeyen bir hata oluştu", e );
        }
    }


    @Bean
    public
    UserDetailsService userDetailsService () {
        UserDetails admin = User.withDefaultPasswordEncoder ( )
                .username ( "admin" )
                .password ( "admin" )
                .roles ( "admin", "user" )
                .build ( );

        UserDetails user = User.withDefaultPasswordEncoder ( )
                .username ( "user" )
                .password ( "user" )
                .roles ( "user" )
                .build ( );

        return new InMemoryUserDetailsManager ( admin, user );

    }
}
