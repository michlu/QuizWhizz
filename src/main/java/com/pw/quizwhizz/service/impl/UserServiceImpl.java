package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.exception.EmailExistsException;
import com.pw.quizwhizz.model.account.Role;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.account.UserProfileType;
import com.pw.quizwhizz.repository.RoleRepository;
import com.pw.quizwhizz.repository.UserRepository;
import com.pw.quizwhizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder){
		this.passwordEncoder = passwordEncoder;
	}

	public List<User> findAll(){
		return userRepository.findAll();
	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id);
	}
	@Override
	public void addWithDefaultRole(User user) {
		addAccountForUserWithRole(user, UserProfileType.ROLE_USER);
	}

	@Override
	public void addWithAdminRole(User user) {
		addAccountForUserWithRole(user, UserProfileType.ROLE_USER, UserProfileType.ROLE_ADMIN);
	}

	@Transactional
	@Override
	public void addRoleToUser(String userId, String[] roles){
		User user = userRepository.findOne(Long.parseLong(userId));
		for (String roleId : roles) {
			Role role = roleRepository.findOne(Long.parseLong(roleId));
			user.addRole(role);
		}
		userRepository.save(user);
	}

	@Transactional
	@Override
	public void removeRoleUser(String userId, String[] roles) {
		User user = userRepository.findOne(Long.parseLong(userId));
		for (String roleId : roles) {
			Role role = roleRepository.findOne(Long.parseLong(roleId));
			user.removeRole(role);
		}
		userRepository.save(user);
	}

	@Transactional
	@Override
	public void update(User user) {
		User updateUser = userRepository.findById(user.getId());
		if(user.getFirstName()!=null)
		updateUser.setFirstName(user.getFirstName());
		if(user.getEmail()!=null)
			updateUser.setEmail(user.getEmail());
		if(user.getPassword()!=null)
			updateUser.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.save(updateUser);
	}

	/**
	 * Dodaje nowe konto dla podanego uzytkownika. Hashuje haslo przed zapisaniem do bazy danych.
	 * @param user
	 * @param userProfileType przyjmuje Enumy odpowiadajace rolą
	 * @throws EmailExistsException
	 */
	private void addAccountForUserWithRole(User user, UserProfileType... userProfileType) {
		for (int i = 0; i < userProfileType.length; i++) {
			Role role = roleRepository.findByRole(userProfileType[i]);
			user.getRoles().add(role);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

}