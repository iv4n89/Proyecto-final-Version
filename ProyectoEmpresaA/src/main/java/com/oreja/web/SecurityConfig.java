package com.oreja.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends  WebSecurityConfigurerAdapter{

    @Bean
    public PasswordEncoder passwordEncoder() {
	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable()
		.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/material/add","/material/update")
				.hasAuthority("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/material/delete")
				.hasAuthority("ADMIN")
			.antMatchers(HttpMethod.GET, "/pruebas/deletePrueba")
				.hasAnyAuthority("ADMIN")
		.and()
			.formLogin()
			.loginPage("/login")
		.and()
			.httpBasic()
			.realmName("basico")
		.and()
			.exceptionHandling()
			.accessDeniedPage("/error/403")
		.and()
			.logout()
				.permitAll()
				.logoutSuccessUrl("/material")
		;
    }

}
