package com.pw.quizwhizz.security;

import com.pw.quizwhizz.model.account.Role;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * Serwis konwertujacy klase User na klase UserDetails wykorzystywana przez Authentication w SpringSecurity
 * @author Michał Nowiński
 * @see UserDetailsService
 * @see org.springframework.security.core.Authentication
 */
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private UserRepository userRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if(user == null)
			throw new UsernameNotFoundException("SpringSecurity: Nie ma takiego użytkownika - " + user.getEmail());
		org.springframework.security.core.userdetails.User userDetails = 
				new org.springframework.security.core.userdetails.User(
						user.getEmail(), 
						user.getPassword(), 
						convertAuthorities(user.getRoles()));
		return userDetails;
	}
	
	private Set<GrantedAuthority> convertAuthorities(Set<Role> userRoles) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		for(Role ur: userRoles) {
			authorities.add(new SimpleGrantedAuthority(ur.getRole().getRoleName()));
		}
		return authorities;
	}
}