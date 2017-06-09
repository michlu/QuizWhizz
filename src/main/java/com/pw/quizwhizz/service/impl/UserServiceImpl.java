package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.entity.game.PlayerEntity;
import com.pw.quizwhizz.model.account.Role;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.account.UserProfileType;
import com.pw.quizwhizz.model.dto.UserAllStats;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.repository.RoleRepository;
import com.pw.quizwhizz.repository.UserRepository;
import com.pw.quizwhizz.repository.game.PlayerRepository;
import com.pw.quizwhizz.repository.impl.UserAllScoresRepository;
import com.pw.quizwhizz.service.RandomProfileImageService;
import com.pw.quizwhizz.service.UserService;
import com.pw.quizwhizz.util.FileTool;
import com.pw.quizwhizz.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Serwis domenowy udostepniajacy funkcjonalnosci dla domeny User
 * @author Michał Nowiński
 * @see UserService
 */
@Service
public class UserServiceImpl implements UserService {

	/** Zabezpieczenie minimalnej dlugosci hasla przy edycji danych uzytkownika */
	final private static int ACCEPTABLE_MINIMUM_PASSWORD_LENGTH = 4;

	final private UserRepository userRepository;
	final private RoleRepository roleRepository;
	final private PasswordEncoder passwordEncoder;
	final private PlayerRepository playerRepository;
	final private UserAllScoresRepository userAllScoresRepository;
	final private ImageUtil imageUtil;
	final private RandomProfileImageService randomProfileImageService;
	final private FileTool fileTool;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, PlayerRepository playerRepository, UserAllScoresRepository userAllScoresRepository, ImageUtil imageUtil, RandomProfileImageServiceImpl randomProfileImageService, FileTool fileTool) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.playerRepository = playerRepository;
		this.userAllScoresRepository = userAllScoresRepository;
		this.imageUtil = imageUtil;
		this.randomProfileImageService = randomProfileImageService;
		this.fileTool = fileTool;
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
	public void addWithDefaultRole(User user, String saveDirectory) throws IOException {
		addAccountForUserWithRole(user, saveDirectory, UserProfileType.ROLE_USER);
	}

	@Override
	public void addWithAdminRole(User user, String saveDirectory) throws IOException {
		addAccountForUserWithRole(user, saveDirectory, UserProfileType.ROLE_USER, UserProfileType.ROLE_ADMIN);
	}

	/**
	 * Dodaje role Uzytkownikowi
	 * @param userId numer id uzytkownika
	 * @param roles tabela z rolami do nadania uzytkownikowi
	 */
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

	/**
	 * Usowa role Uzytkownikowi
	 * @param userId numer id uzytkownika
	 * @param roles tabela z rolami do usuniecia uzytkownikowi
	 */
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

	/**
	 * Aktualizuje dane uzytkownika. Sprawdza jakie dane zostaly przeslane poprzez formularz i aktualizuje jedynie zmienione.
	 * @param user obiekt Uzytkownika przeslany z formularza
	 */
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
		if(user.getPassword()!=null && user.getPassword().length()>= ACCEPTABLE_MINIMUM_PASSWORD_LENGTH)
			updateUser.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.save(updateUser);
	}

	/**
	 * Aktualizuje dane Uzytkownika łacznie ze zdjeciem proflowym. Sprawdza jakie dane zostaly przeslane poprzez formularz i aktualizuje jedynie zmienione.
	 * @param user obiekt Uzytkownika przeslany z formularza
	 * @param file obraz proflu uzytkownika
	 * @param saveDirectory sciezka do zapisu pliku
	 * @throws IOException wymagana przez Files
	 */
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
		if(user.getPassword()!=null && user.getPassword().length()>= ACCEPTABLE_MINIMUM_PASSWORD_LENGTH)
			updateUser.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.save(updateUser);
	}

	/**
	 * Metoda dodaje nowe konto dla podanego uzytkownika. Hashuje haslo przed zapisaniem do bazy danych.
	 * Przypisuje losowy obraz dla zdjecia profilowego.
	 * @param user obiekt Uzytkownika przeslany z formularza
	 * @param saveDirectory   sciezka do zapisu pliku
	 * @param userProfileType przyjmuje Enumy odpowiadajace roli
	 */
	private void addAccountForUserWithRole(User user, String saveDirectory, UserProfileType... userProfileType) throws IOException {
		for (int i = 0; i < userProfileType.length; i++) {
			Role role = roleRepository.findByRole(userProfileType[i]);
			user.getRoles().add(role);
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		user = userRepository.findById(user.getId()); // wyciaganie id zapisywanego user'a

		File file = randomProfileImageService.getRandomProfileImage();
		String extension = fileTool.getFileExtension(file);
		String fileNameWithExtension = "profile_" + user.getId() + "." + extension;

		user.setUrlImage("/resources/images/" + fileNameWithExtension);
		Path path = Paths.get(saveDirectory + fileNameWithExtension);
		Files.write(path, fileTool.getFileBytes(file));

		userRepository.save(user);
	}

	/**
	 * @param userId numer id uzytkownika
	 * @return zwraca Player'a po numerze ID Uzytkownika
	 */
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

	/**
	 * @param userId numer id Uzytkownika
	 * @return zwraca wsyzstkie wyniki gier dla danego Uzytkownika
	 */
	public List<UserAllStats> findAllScoreForUser(Long userId){
		return userAllScoresRepository.findAllScoreForUser(userId);
	}
}