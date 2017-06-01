package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.entity.game.PlayerEntity;
import com.pw.quizwhizz.model.account.Role;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.account.UserProfileType;
import com.pw.quizwhizz.model.dto.Ranking;
import com.pw.quizwhizz.model.dto.UserAllStats;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.repository.RoleRepository;
import com.pw.quizwhizz.repository.UserRepository;
import com.pw.quizwhizz.repository.game.PlayerRepository;
import com.pw.quizwhizz.repository.impl.RankingRepository;
import com.pw.quizwhizz.repository.impl.UserAllScoresRepository;
import com.pw.quizwhizz.service.UserService;
import com.pw.quizwhizz.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
	final private UserRepository userRepository;
	final private RoleRepository roleRepository;
	final private PasswordEncoder passwordEncoder;
	final private PlayerRepository playerRepository;
	final private UserAllScoresRepository userAllScoresRepository;
	final private RankingRepository rankingRepository;
	final private ImageUtil imageUtil;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, PlayerRepository playerRepository, UserAllScoresRepository userAllScoresRepository, RankingRepository rankingRepository, ImageUtil imageUtil) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.playerRepository = playerRepository;
		this.userAllScoresRepository = userAllScoresRepository;
		this.rankingRepository = rankingRepository;
		this.imageUtil = imageUtil;
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
		if(user.getUrlImage()!=null)
			updateUser.setUrlImage(user.getUrlImage());
		if(user.getPassword()!=null)
			updateUser.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.save(updateUser);
	}

	@Transactional
	@Override
	public void updateUserWithImage(User user, MultipartFile file, String saveDirectory) throws IOException {
		User updateUser = userRepository.findById(user.getId());
		String fileNameWithExtension = "profile_" + user.getId() + "." + file.getOriginalFilename().split("\\.")[1];
		BufferedImage resizedImage = imageUtil.resizeImage(file.getBytes(), 200, 200);
		updateUser.setUrlImage("/resources/images/" + fileNameWithExtension);
		Path path = Paths.get(saveDirectory + fileNameWithExtension);
		Files.write(path, imageUtil.imageToByte(resizedImage));

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
	 */
	private void addAccountForUserWithRole(User user, UserProfileType... userProfileType) {
		for (int i = 0; i < userProfileType.length; i++) {
			Role role = roleRepository.findByRole(userProfileType[i]);
			user.getRoles().add(role);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public Player findPlayerByUserId(Long userId) {
		PlayerEntity playerEntity = playerRepository.findOne(userId);
		Player player = new Player(playerEntity.getName());
		player.setId(userId);
		if (playerEntity.getGamesPlayed() != null) {
			player.setGamesPlayed(playerEntity.getGamesPlayed());
		}
		if (playerEntity.getXp() != null) {
			player.setXp(playerEntity.getXp());
		}
		return player;
	}

	public List<UserAllStats> findAllScoreForUser(Long userId){
		return userAllScoresRepository.findAllScoreForUser(userId);
	}
	public List<Ranking> findGeneralRank(int limitSearch){
		return rankingRepository.findGeneralRank(limitSearch);
	}
	public List<Ranking> findFiveByCategory(int limitSearch , Long categoryId){
		return rankingRepository.findFiveByCategory(limitSearch, categoryId);
	}

}