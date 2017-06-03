package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

/**
 * Kontroler funkcjonalnosci dla uzytkownikow oraz obsluga żądań dotyczacych rejestracji.
 * @author Michał Nowiński
 */
@Controller
public class UserController {

	final private UserService userService;

	@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

	/**
	 * @return zwraca login_form.html strone zawierajaca formularz logowania uzytkownikow
	 */
	@GetMapping("/loginform")
	public String loginForm() {
		return "login_form";
	}

	/**
	 * @param model zawiera bledy logowania
	 * @return zwraca login_form.html strone zawierajaca formularz logowania uzytkownikow
	 */
	@GetMapping("/loginerror")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login_form";
	}

	/**
	 * @param model zwiera zuytkownika
	 * @return zwraca login_form.html strone zawierajaca firmularz rejestracji uzytkownikow
	 */
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register_form";
	}

	/**
	 * Obsluguje motede POST /register, przyjmuje dane z formularza rejestracji uzytkownika
	 * @param user uzytkwnik
	 * @param bindResult bledy poprawnosci danych przy rejestracji
	 * @param request wymagane do auto logowania po rejestracji
	 * @return zwraca strone register_success.html w przypadku prawidlowej rejestracji albo powraca z powrotem na register_form.html w przypadku bledow przy rejestracji
	 * @throws ServletException wymagane przez HttpServletRequest
	 */
	@PostMapping("/register")
	public String addUser(@ModelAttribute @Valid User user, BindingResult bindResult, HttpServletRequest request) throws ServletException {
		if(bindResult.hasErrors())
			return "register_form";
		else {
			userService.addWithDefaultRole(user);
			request.login(user.getEmail(),user.getPassword()); // auto logowanie
			return "register_success";
		}
	}

	/**
	 * Obsluguje metode GET /user/{userId} zawierajaca id uzytkownika. Przekazuje strone profilowa uzytkownika. Sprawdza czy uzytkownik odwiedza swoja strone profilowa.
	 * Uzytkownik odwiedzajacy swoja strone profilowa posiada dodatkowe okno do edycji wlasnych danych osobowych.
	 * @param userId numer id uzytkownika
	 * @param authentication dane uwierzytelniania uzytkownika
	 * @param model zawiera userCheckHimself odpowiedzialny za przekazanie warunku spawdzenia wlasnego profilu, dane uzytkownika, dane playera, wszystkie wyniki dla danego uzytkownika
	 * @return zwraca user_profile.html ze strona profilowa uzytkownika. Strona zawiera statystyki gracza, dane osobowe i formularze edycji
	 */
	@GetMapping("/user/{userId}")
	public String userProfile(
			@PathVariable Long userId,
			Authentication authentication,
			Model model) {
		User user = userService.findById(userId);
		boolean userCheckHimself = false;
		if(user.getEmail().equals(authentication.getName())){
			userCheckHimself = true; // sprawdza czy user odwiedza swoj profil (dodaje opcje edytowania)
		}
		model.addAttribute("userCheckHimself", userCheckHimself);
		model.addAttribute("user", user);
        model.addAttribute("player", userService.findPlayerByUserId(user.getId()));
		model.addAttribute("userAllScores", userService.findAllScoreForUser(user.getId()));
		return "user_profile";
	}

	/**
	 * Obsluguje metode GET /user/my. Przekazuje storne pofrilowa uzytkownika ktory odwoluje sie bezposrednio do wlasnego profilu
	 * @return zwraca user_profile.html ze strona profilowa uzytkownika. Strona zawiera statystyki gracza, dane osobowe i formularze edycji
	 */
	@GetMapping("/user/my")
	public String userProfileMy(
			Authentication authentication,
			Model model) {
		boolean userCheckHimself = true;
		User user = userService.findByEmail(authentication.getName());
		model.addAttribute("userCheckHimself", userCheckHimself);
		model.addAttribute("user", user);
		model.addAttribute("player", userService.findPlayerByUserId(user.getId()));
		model.addAttribute("userAllScores", userService.findAllScoreForUser(user.getId()));
		return "user_profile";
	}

	/**
	 * Obsluguje metode POST /user/editme. Przyjmuje dane z formularza ze strony porfilowej uzytkownika. Pozwala na edycje danych uzytkownkowi
	 * @param user uzytkownik
	 * @param bindResult bledy walidacji formularza
	 * @param file plik zdjecia profilowego
	 * @param request potrzebny do ustalenia sciezki kontekstu aplikacji
	 * @return przekierowuje z powrotem na strone profilowa uzytkownika
	 */
	@PostMapping("/user/editme")
	public String postUserEditMe(
			@ModelAttribute User user,
			BindingResult bindResult,
			@RequestParam MultipartFile file,
			HttpServletRequest request) {
		String saveDirectory = request.getSession().getServletContext().getRealPath("/")+"resources\\images\\";

		if (bindResult.hasErrors()){
			return "redirect:/user/my";
		}
		else {
			if (!file.isEmpty()) {
				try {
					userService.updateUserWithImage(user, file, saveDirectory);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else{
				userService.update(user);
			}
			return "redirect:/user/my";
		}
	}
}