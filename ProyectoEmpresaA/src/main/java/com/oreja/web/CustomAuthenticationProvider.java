package com.oreja.web;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	private String uriAutentificacion;
	private String credencialesAutentificacion;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public CustomAuthenticationProvider(String uri, String credential) {
		this.uriAutentificacion = uri;
		this.credencialesAutentificacion = credential;
	}
	
    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
 
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        System.out.println(password);
        
        String[] roles = autenticarRemoto(username, password);
        if (roles==null)
        	return null;
        return new UsernamePasswordAuthenticationToken(
              username, 
              password, 
              Stream.of(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList())
              );
    }

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	private String[] autenticarRemoto(String username, String password) {	    
		return WebClient.builder().baseUrl(uriAutentificacion)
		.build()
		.get()
		.uri("/")
		.header("username", username)
		.header("password", password)
		.header("authorization", credencialesAutentificacion)
		.retrieve()
		.bodyToMono(String[].class)
		.block();
	}
}