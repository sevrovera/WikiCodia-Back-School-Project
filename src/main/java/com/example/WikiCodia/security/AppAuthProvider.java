package com.example.WikiCodia.security;

import com.example.WikiCodia.service.UtilisateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

// Concerne Spring Security
public class AppAuthProvider extends DaoAuthenticationProvider {

    @Autowired
    UtilisateurService userDetailsService;
    @Autowired
	private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String mail = auth.getName();
        String password = auth.getCredentials()
                .toString();
        
        UserDetails user = userDetailsService.loadUserByUsername(mail);
        Boolean isGoodPassword = passwordEncoder.matches(auth.getCredentials().toString(), user.getPassword());
        System.out.println(user.getPassword());
        System.out.println(password);
        System.out.println(isGoodPassword);
        /*
        if (user == null || !user.getPassword().equals(password)) {
            throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
        }
        */
        if (user == null || !isGoodPassword) {
        	throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
        }
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}