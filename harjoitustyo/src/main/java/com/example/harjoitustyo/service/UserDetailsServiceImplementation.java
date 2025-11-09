package com.example.harjoitustyo.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.harjoitustyo.domain.AppUser;
import com.example.harjoitustyo.domain.AppUserRepository;



@Service
public class UserDetailsServiceImplementation implements UserDetailsService  {
	
	AppUserRepository auRepository;
	
	public UserDetailsServiceImplementation(AppUserRepository auRepository) {
		this.auRepository = auRepository; 
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {   
    	AppUser curruser = auRepository.findByUsername(username);
        UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPasswordHash(), 
        		AuthorityUtils.createAuthorityList(curruser.getUserRole()));
        return user;
    }   
} 
