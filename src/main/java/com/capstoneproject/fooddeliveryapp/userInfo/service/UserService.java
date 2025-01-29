package com.capstoneproject.fooddeliveryapp.userInfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capstoneproject.fooddeliveryapp.userInfo.config.JwtUtils;
import com.capstoneproject.fooddeliveryapp.userInfo.model.Users;
import com.capstoneproject.fooddeliveryapp.userInfo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;

    public Users registerUser(Users user) {
        //user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        return userRepository.save(user);
    }

    public Users findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
    
    public String generateToke(String username) throws Exception {
    	try {
    		//Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, null));
    		return jwtUtils.generateToken(username);
    	} catch(Exception e) {
    		throw new Exception("User is not valid");
    	}
    }

}
