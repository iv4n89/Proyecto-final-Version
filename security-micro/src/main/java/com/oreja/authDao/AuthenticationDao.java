package com.oreja.authDao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationDao {

    private static final String QUERY_USER = "SELECT PASSWORD FROM USERS WHERE USERNAME=?";
    private static final String QUERY_ROLES = "SELECT AUTHORITY FROM AUTHORITIES WHERE USERNAME=?"; //
    
    private JdbcTemplate template;
    
    @Bean
    public BCryptPasswordEncoder encoder() {
	return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void setDataSource(DataSource dataSource) { 
	template = new JdbcTemplate(dataSource);
    }
    
    public String[] validate (String user, String password) {
	String passEnc = template.queryForObject(QUERY_USER, String.class, user);
	if(passEnc != null && encoder().matches(password, passEnc)) {
	    
	    return template.queryForList(QUERY_ROLES, String.class, user).toArray(String[]::new);
	}
	return null;
    }
    
}
