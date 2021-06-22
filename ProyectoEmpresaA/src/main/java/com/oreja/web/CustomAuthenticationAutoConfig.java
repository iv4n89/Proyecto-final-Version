package com.oreja.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomAuthenticationAutoConfig {
	private String uriAutentificacion = "http://localhost:8090";
	private String credencialesAutentificacion = "username:password";

	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider(uriAutentificacion, credencialesAutentificacion);
	}
}
