package com.oreja.restApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.oreja.authDao.AuthenticationDao;

@RestController
public class SecurityApi {

    @Autowired
    private AuthenticationDao dao;
    
    @Value("username") private String usernameAuthorized;
    @Value("password") private String passwordAuthorized;
    
    @GetMapping("/") 
    public String[] authenticate( @RequestHeader(name = "authorization") String credentials,
	    						@RequestHeader(name = "username") String user,
	    						@RequestHeader(name = "password") String password) {
	if(checkAuthorizedUser(credentials)) {
	    return dao.validate(user, password);
	}
	throw new SecurityException();
    }
    
    private boolean checkAuthorizedUser(String credentials) {
	String[] userpass = credentials.split(":");
	return userpass[0].equals(usernameAuthorized) && userpass[1].equals(passwordAuthorized);
    }
    
}
